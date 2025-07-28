package com.dev.mosquera.springwebflux.controller;

import com.dev.mosquera.springwebflux.models.Product;
import com.dev.mosquera.springwebflux.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("api/products")
@Tag(name = "Producto service", description = "Services to manage all the transactions from the Product table")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Value("${config.uploads.path}")
    private String path;

    @GetMapping("/find/all")
    @Operation(description = "Endpoint that allows the searching of all products.")
    public Flux<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping("/find/all/mono")
    @Operation(description = "Some way to get all the products with webflux mono as return.")
    public Mono<ResponseEntity<Flux<Product>>> findAllMono() {
        return Mono.just(ResponseEntity.ok(productService.findAll()));
    }

    @GetMapping("find/all/uppercase")
    @Operation(description = "Endpoint that allows the searching of all products with the name as uppercase")
    public Flux<Product> findAllByNameUppercase() {
        return productService.findAllByNameUpperCase();
    }

    @GetMapping("find/all/repeat")
    @Operation(description = "Endpoint that allos the searching of all products repating")
    public Flux<Product> findAllByNameRepeat() {
        return findAllByNameRepeat();
    }

    @GetMapping("/find/{id}")
    public Mono<ResponseEntity<Product>> findById(
            @Parameter(description = "Product's identification which is needed to get the right object")
            @PathVariable String id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    @Operation(description = "Endpoint that allows the product's creation.")
    public Mono<Product> create(@RequestBody Product product) {
        Date getDateNow = new Date();
        product.setCreateAt(getDateNow);
        return productService.save(product);
    }

    @PostMapping("/create/mono")
    public Mono<ResponseEntity<Map<String, Object>>> createMono(@Valid @RequestBody Mono<Product> product) {
        Map<String, Object> response = new HashMap<>();
        Date getDateNow = new Date();
        return product.flatMap(p -> {
            p.setCreateAt(getDateNow);

            return productService.save(p).map(item -> {
                response.put("product", item);
                response.put("message", "Product created succesfully");
                return ResponseEntity.created(URI.create("/api/products/find/".concat(item.getId())))
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(response);
                });
        }).onErrorResume(t -> {
            return Mono.just(t).cast(WebExchangeBindException.class)
                    .flatMap(e -> Mono.just(e.getFieldErrors()))
                    .flatMapMany(Flux::fromIterable)
                    .map(error -> "El campo " + error.getField() + " " + error.getDefaultMessage())
                    .collectList()
                    .flatMap(list -> {
                        response.put("errors", list);
                        return Mono.just(ResponseEntity.badRequest().body(response));
                    });
        });
    }

    @PutMapping("/update")
    @Operation(description = "Enpoint that allow the product's update.")
    public Mono<ResponseEntity<Product>> update(@RequestBody Product product) {
        return productService.findById(product.getId()).flatMap(productFound -> {
            productFound.setName(product.getName());
            productFound.setPrice(product.getPrice());
            productFound.setCreateAt(new Date());
            return productService.save(productFound)
                    .map(ResponseEntity::ok)
                    .defaultIfEmpty(ResponseEntity.notFound().build());
        });
    }

    @PutMapping("/update/mono/{id}")
    public Mono<ResponseEntity<Product>> updateMono(@RequestBody Product product, @PathVariable String id) {
        return productService.findById(id).flatMap(p -> {
            p.setName(product.getName());
            p.setPrice(product.getPrice());
            return productService.save(p);
        }).map(p -> ResponseEntity.created(URI.create("/api/products/find/".concat(p.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(p)).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return productService.findById(id).flatMap(p -> {
            return productService.delete(p).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
        }).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value ="/upload/{id}", consumes = "multipart/form-data")
    public Mono<ResponseEntity<Product>> upload(@PathVariable String id, @RequestPart FilePart file) {
        return productService.findById(id).flatMap(p -> {
            p.setPhoto(UUID.randomUUID().toString() + "-" + file.filename()
                    .replace(" ", "")
                    .replace(":", "")
                    .replace("\\", ""));
                    System.out.println(path);
            return file.transferTo(new File(path + p.getPhoto())).then(productService.save(p));
        }).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
