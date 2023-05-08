package com.example.domain.listener;

import com.example.domain.models.CategoryOrDishModelDomain;

import java.util.List;

public interface CategoryAndDishListener {
    void getCategoryAndDish(List<CategoryOrDishModelDomain> categoryOrDishModelDomainList);
}
