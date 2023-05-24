package com.example.whereiscaesar.presentation.util;


import com.yandex.mapkit.geometry.Point;

public class Restaurant {

    public String name;
    public com.yandex.mapkit.geometry.Point point;
    public Integer allCount;
    public Integer allSum;

    public Restaurant(String name, Point point, Integer allSum, Integer allCount) {
        this.name = name;
        this.point = point;
        this.allSum = allSum;
        this.allCount = allCount;
    }
}
