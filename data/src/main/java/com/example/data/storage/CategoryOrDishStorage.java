package com.example.data.storage;



import com.example.domain.listener.CategoryAndDishListener;


public interface CategoryOrDishStorage {

    void GetCategoryOrDish(String tag, CategoryAndDishListener listener);


}
