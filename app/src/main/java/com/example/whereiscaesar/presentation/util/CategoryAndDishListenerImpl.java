package com.example.whereiscaesar.presentation.util;

import androidx.lifecycle.MutableLiveData;

import com.example.domain.listener.CategoryAndDishListener;
import com.example.domain.models.CategoryOrDishModelDomain;

import java.util.List;

import javax.inject.Inject;

public class CategoryAndDishListenerImpl implements CategoryAndDishListener {

    MutableLiveData<List<CategoryOrDishModelDomain>> mutableLiveData;

    public CategoryAndDishListenerImpl(MutableLiveData<List<CategoryOrDishModelDomain>> mutableLiveData){
        this.mutableLiveData = mutableLiveData;
    }
    @Override
    public void getCategoryAndDish(List<CategoryOrDishModelDomain> categoryOrDishModelDomainList) {
        mutableLiveData.setValue(categoryOrDishModelDomainList);
    }
}
