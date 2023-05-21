package com.example.whereiscaesar.presentation.util;

import android.view.View;

import com.example.domain.models.CategoryOrDishModelDomain;

import java.util.List;

public interface CardClickListener {

    void onCardClick(CategoryOrDishModelDomain categoryOrDishModelDomain);
    void progressBarr(List<CategoryOrDishModelDomain> categoryOrDishModelDomainList);
}
