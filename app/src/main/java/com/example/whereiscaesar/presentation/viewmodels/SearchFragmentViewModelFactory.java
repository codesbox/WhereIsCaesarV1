package com.example.whereiscaesar.presentation.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.data.repository.CategoryOrDishRepositoryImpl;
import com.example.data.storage.CategoryOrDishStorage;
import com.example.data.storage.firebase.CategoryOrDishStorageImpl;
import com.example.domain.listener.CategoryAndDishListener;
import com.example.domain.models.CategoryOrDishModelDomain;
import com.example.domain.repository.CategoryOrDishRepository;
import com.example.domain.useCase.GetCategoryAndDishUseCase;
import com.example.whereiscaesar.presentation.util.CategoryAndDishListenerImpl;

import java.util.ArrayList;
import java.util.List;

public class SearchFragmentViewModelFactory implements ViewModelProvider.Factory {

    CategoryOrDishRepository categoryOrDishRepository;
    GetCategoryAndDishUseCase getCategoryAndDishUseCase;
    CategoryAndDishListener categoryAndDishListener;

    MutableLiveData<List<CategoryOrDishModelDomain>> mutableLiveData = new MutableLiveData<>();

    List<CategoryOrDishModelDomain> categoryOrDishModelDomainList;


    public SearchFragmentViewModelFactory(){
        CategoryOrDishStorage categoryOrDishStorage = new CategoryOrDishStorageImpl();
        categoryOrDishRepository = new CategoryOrDishRepositoryImpl(categoryOrDishStorage);
        categoryAndDishListener = new CategoryAndDishListenerImpl(mutableLiveData);
        getCategoryAndDishUseCase = new GetCategoryAndDishUseCase(categoryOrDishRepository, categoryAndDishListener, "main");
        categoryOrDishModelDomainList = new ArrayList<>();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SearchFragmentViewModel(getCategoryAndDishUseCase, mutableLiveData, categoryOrDishModelDomainList);
    }




}
