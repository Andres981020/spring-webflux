package com.dev.mosquera.springwebflux.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.codec.multipart.FilePart;

import java.util.Date;

@Document
@Data
public class Product {
    @Id
    @Schema(description = "Product's identification code", example = "684f83e5b85141120f8bd2cf")
    private String id;

    @Schema(description = "Product's name", example = "Televisor")
    @NotEmpty
    private String name;
    @Schema(description = "Product's price", example = "1600000")
    @NotNull
    private double price;
    @Schema(description = "Product's creation date", example = "2025-06-15T00:00:00")
    private Date createAt;
    @Schema(description = "Photo of the product")
    private String photo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", createAt=" + createAt +
                '}';
    }
}
