package com.example.whereiscaesar.presentation.di;

import androidx.lifecycle.MutableLiveData;

import com.example.domain.listener.CategoryAndDishListener;
import com.example.domain.models.CategoryOrDishModelDomain;
import com.example.domain.repository.CategoryOrDishRepository;
import com.example.domain.useCase.GetCategoryAndDishUseCase;
import com.example.whereiscaesar.presentation.util.CategoryAndDishListenerImpl;
import com.example.whereiscaesar.presentation.viewmodels.SearchFragmentViewModel;

import java.util.List;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;

@Module
@InstallIn(ViewModelComponent.class)
public class DomainModule {







}
