package com.dev.mosquera.springwebflux.services;

import com.dev.mosquera.springwebflux.models.Product;
import com.dev.mosquera.springwebflux.repository.Dao.ProductDao;
import com.dev.mosquera.springwebflux.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService implements ProductDao {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public Mono<Product> save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Mono<Product> update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Flux<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Flux<Product> findAllByNameUpperCase() {
        return productRepository
                .findAll()
                .map(product -> {
                    product.setName(product.getName().toUpperCase());
                    return product;
                });
    }

    @Override
    public Flux<Product> findAllByNameUpperCaseRepeat() {
        return findAllByNameUpperCase().repeat(5);
    }

    @Override
    public Mono<Product> findById(String id) {
        return productRepository.findById(id);
    }

    @Override
    public Mono<Void> delete(Product p) {
        return productRepository.delete(p);
    }
}
