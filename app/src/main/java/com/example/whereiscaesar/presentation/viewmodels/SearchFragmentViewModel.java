package com.example.whereiscaesar.presentation.viewmodels;

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

    CategoryOrDishRepository categoryOrDishRepository;
    GetCategoryAndDishUseCase getCategoryAndDishUseCase;
    CategoryAndDishListener categoryAndDishListener;


    public SearchFragmentViewModel(){
        CategoryOrDishStorage categoryOrDishStorage = new CategoryOrDishStorageImpl();
        categoryOrDishRepository = new CategoryOrDishRepositoryImpl(categoryOrDishStorage);
        categoryAndDishListener = new CategoryAndDishListenerImpl(categoryOrDishModelDomain);
        getCategoryAndDishUseCase = new GetCategoryAndDishUseCase(categoryOrDishRepository, categoryAndDishListener);
        getCategoryAndDishUseCase.execute();
    }
    MutableLiveData<List<CategoryOrDishModelDomain>> categoryOrDishModelDomain = new MutableLiveData<>();

    public LiveData<List<CategoryOrDishModelDomain>> getItems(){

        return categoryOrDishModelDomain;
    }







}
