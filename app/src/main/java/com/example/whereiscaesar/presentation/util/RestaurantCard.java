package com.example.whereiscaesar.presentation.util;

public class RestaurantCard {
    public String restaurantName;
    public Integer resEstimation;
    public Integer estimation;
    public Integer counter;
    public Integer allSum;
    public  Integer allCount;

    public RestaurantCard(String restaurantName, Integer resEstimation, Integer estimation, Integer counter, Integer allSum, Integer allCount) {
        this.restaurantName = restaurantName;
        this.resEstimation = resEstimation;
        this.estimation = estimation;
        this.counter = counter;
        this.allSum = allSum;
        this.allCount = allCount;
    }
}
