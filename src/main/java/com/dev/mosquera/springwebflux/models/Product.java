package com.dev.mosquera.springwebflux.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "products")
public class Product {
    @Id
    private String id;

    private String name;
    private double price;
    private Date createAt;
}
