package com.dev.mosquera.springwebflux.repository.Dao;

import com.dev.mosquera.springwebflux.models.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ProductDao {
    Mono<Product> save(Product product);
    Mono<Product> update(Product product);
    Flux<Product> findAll();
    Mono<Product> findById(String id);
}
