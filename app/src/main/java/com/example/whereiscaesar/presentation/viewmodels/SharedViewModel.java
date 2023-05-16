package com.example.whereiscaesar.presentation.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.domain.models.CategoryOrDishModelDomain;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {

    List<CategoryOrDishModelDomain> list;
    MutableLiveData<List<CategoryOrDishModelDomain>> mutableLiveData = new MutableLiveData<>();
    public SharedViewModel(){
        list = new ArrayList<>();
    }

    public void addElement(CategoryOrDishModelDomain categoryOrDishModelDomain){
        list.add(categoryOrDishModelDomain);
        mutableLiveData.setValue(list);

    }
    public LiveData<List<CategoryOrDishModelDomain>> getData(){
        return mutableLiveData;
    }



}
