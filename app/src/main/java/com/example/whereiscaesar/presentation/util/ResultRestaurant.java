package com.example.whereiscaesar.presentation.util;

import com.yandex.mapkit.geometry.Point;

import java.util.List;

public class ResultRestaurant {

    public String name;
    public com.yandex.mapkit.geometry.Point point;
    public List<String> dishList;
    public Integer allSum;
    public Integer allCount;

    public ResultRestaurant(String name, Point point, List<String> dishList, Integer allSum, Integer allCount) {
        this.name = name;
        this.point = point;
        this.dishList = dishList;
        this.allSum = allSum;
        this.allCount = allCount;
    }
}