package com.example.whereiscaesar.presentation.di;

import androidx.lifecycle.MutableLiveData;

import com.example.data.repository.CategoryOrDishRepositoryImpl;
import com.example.data.storage.CategoryOrDishStorage;
import com.example.data.storage.firebase.CategoryOrDishStorageImpl;
import com.example.domain.listener.CategoryAndDishListener;
import com.example.domain.models.CategoryOrDishModelDomain;
import com.example.domain.repository.CategoryOrDishRepository;
import com.example.whereiscaesar.presentation.util.CategoryAndDishListenerImpl;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DataModule {
    @Provides
    @Singleton
    public static CategoryOrDishStorage provideCategoryOrDishStorage(){
        return new CategoryOrDishStorageImpl();
    }

    @Provides
    @Singleton
    public static CategoryOrDishRepository provideCategoryOrDishRepository(CategoryOrDishStorage categoryOrDishStorage){
        return new CategoryOrDishRepositoryImpl(categoryOrDishStorage);
    }
    @Provides
    @Singleton
    public static CategoryAndDishListener provideCategoryAndDishListener(MutableLiveData<List<CategoryOrDishModelDomain>> mutableLiveData){
        return new CategoryAndDishListenerImpl(mutableLiveData);
    }



}
