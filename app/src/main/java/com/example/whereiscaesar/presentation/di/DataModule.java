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





}
