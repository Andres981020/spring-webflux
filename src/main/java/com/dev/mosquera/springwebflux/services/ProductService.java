package com.dev.mosquera.springwebflux.services;

import com.dev.mosquera.springwebflux.models.Product;
import com.dev.mosquera.springwebflux.repository.Dao.ProductDao;
import com.dev.mosquera.springwebflux.repository.ProductRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements ProductDao {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product findById(String id) {
        return productRepository.findById(id).get();
    }
}
