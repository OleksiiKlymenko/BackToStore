package com.example.model;

import com.example.ProductCategory;

public class Product {

    private final String id;
    private final ProductCategory category;

    public Product(String id, ProductCategory category) {
        this.id = id;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public ProductCategory getCategory() {
        return category;
    }

}
