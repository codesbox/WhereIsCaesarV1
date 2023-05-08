package com.example.data.storage.models;

public class CategoryOrDishModel {


    public String title, imageUrl;
    public Boolean IsCategory;

    public CategoryOrDishModel(String title, String imageUrl, Boolean isCategory) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.IsCategory = isCategory;
    }
}
