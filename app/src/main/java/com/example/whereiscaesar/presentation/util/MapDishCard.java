package com.example.whereiscaesar.presentation.util;

public class MapDishCard {
    public String dishName;
    public String imageUrl;
    public Integer counter;
    public Integer estimation;
    Integer sum;

    public MapDishCard(String dishName, String imageUrl, Integer counter, Integer sum) {
        this.dishName = dishName;
        this.imageUrl = imageUrl;
        this.counter = counter;
        this.sum = sum;
        this.estimation = this.sum / this.counter;
    }
}
