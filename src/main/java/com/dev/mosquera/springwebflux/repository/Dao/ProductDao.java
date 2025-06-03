package com.dev.mosquera.springwebflux.repository.Dao;

import com.dev.mosquera.springwebflux.configuration.MongoDBConfig;
import com.dev.mosquera.springwebflux.models.Product;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;

public interface ProductDao {
    Product save(Product product);
    List<Product> findAll();
    Product findById(String id);
}
