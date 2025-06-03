package com.dev.mosquera.springwebflux.controller;

import com.dev.mosquera.springwebflux.models.Product;
import com.dev.mosquera.springwebflux.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("create")
    public Product createProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @GetMapping("all")
    public List<Product> findAll() {
        return productService.findAll();
    }
}
