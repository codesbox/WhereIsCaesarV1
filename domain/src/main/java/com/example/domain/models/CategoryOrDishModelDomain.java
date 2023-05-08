package com.example.domain.models;

public class CategoryOrDishModelDomain {

    public String title, imageUrl;
    public Boolean isCategory;

    public CategoryOrDishModelDomain(String title, String imageUrl, Boolean isCategory) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.isCategory = isCategory;
    }
}
