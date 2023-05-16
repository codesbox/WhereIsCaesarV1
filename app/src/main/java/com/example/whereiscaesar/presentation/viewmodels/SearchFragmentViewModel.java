package com.example.whereiscaesar.presentation.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.data.repository.CategoryOrDishRepositoryImpl;
import com.example.data.storage.CategoryOrDishStorage;
import com.example.data.storage.firebase.CategoryOrDishStorageImpl;
import com.example.domain.listener.CategoryAndDishListener;
import com.example.domain.models.CategoryOrDishModelDomain;
import com.example.domain.repository.CategoryOrDishRepository;
import com.example.domain.useCase.GetCategoryAndDishUseCase;
import com.example.whereiscaesar.presentation.util.CategoryAndDishListenerImpl;

import java.util.List;

public class SearchFragmentViewModel extends ViewModel {

    MutableLiveData<List<CategoryOrDishModelDomain>> mutableLiveData;
    List<CategoryOrDishModelDomain> categoryOrDishModelDomainList;



    public SearchFragmentViewModel(GetCategoryAndDishUseCase getCategoryAndDishUseCase,
                                   MutableLiveData<List<CategoryOrDishModelDomain>> mutableLiveData,
                                   List<CategoryOrDishModelDomain> categoryOrDishModelDomainList){
        this.mutableLiveData = mutableLiveData;
        this.categoryOrDishModelDomainList = categoryOrDishModelDomainList;
        getCategoryAndDishUseCase.execute();

    }

    public LiveData<List<CategoryOrDishModelDomain>> getCategoryOrDishModelDomain(){

        return mutableLiveData;
    }








}
