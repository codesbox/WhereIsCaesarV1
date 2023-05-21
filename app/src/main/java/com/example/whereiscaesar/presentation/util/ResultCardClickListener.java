package com.example.whereiscaesar.presentation.util;

import com.example.domain.models.CategoryOrDishModelDomain;

import java.util.List;

public interface ResultCardClickListener {

    void onButtonClick(CategoryOrDishModelDomain categoryOrDishModelDomain);
    void changeVisibility(List<CategoryOrDishModelDomain> categoryOrDishModelDomainList);
}
