package com.example.domain.repository;

import com.example.domain.listener.CategoryAndDishListener;

public interface CategoryOrDishRepository {

    void GetCategoryOrDish(String collection, CategoryAndDishListener listener);
}
