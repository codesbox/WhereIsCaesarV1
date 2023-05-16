package com.example.data.repository;


import com.example.data.storage.CategoryOrDishStorage;
import com.example.domain.listener.CategoryAndDishListener;
import com.example.domain.repository.CategoryOrDishRepository;


public class CategoryOrDishRepositoryImpl implements CategoryOrDishRepository {

    CategoryOrDishStorage categoryOrDishStorage;

    public CategoryOrDishRepositoryImpl(CategoryOrDishStorage categoryOrDishStorage){
        this.categoryOrDishStorage = categoryOrDishStorage;
    }

    @Override
    public void GetCategoryOrDish(String tag, CategoryAndDishListener listener) {
        categoryOrDishStorage.GetCategoryOrDish(tag, listener);
    }

}
