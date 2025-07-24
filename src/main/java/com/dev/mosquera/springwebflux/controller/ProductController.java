package com.dev.mosquera.springwebflux.controller;

import com.dev.mosquera.springwebflux.models.Product;
import com.dev.mosquera.springwebflux.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;


@RestController
@RequestMapping("api/products")
@Tag(name = "Producto service", description = "Services to manage all the transactions from the Product table")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/find/all")
    @Operation(description = "Endpoint that allows the searching of all products.")
    public Flux<Product> findAll() {
        return productService.findAll();
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
}
