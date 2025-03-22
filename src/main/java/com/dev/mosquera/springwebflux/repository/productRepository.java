package com.dev.mosquera.springwebflux.repository;

import com.dev.mosquera.springwebflux.models.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface productRepository extends ReactiveMongoRepository<Product, String> {
}
