package com.example.whereiscaesar.presentation.util;


import com.yandex.mapkit.geometry.Point;

public class Restaurant {

    public String name;
    public com.yandex.mapkit.geometry.Point point;

    public Restaurant(String name, Point point) {
        this.name = name;
        this.point = point;
    }
}
