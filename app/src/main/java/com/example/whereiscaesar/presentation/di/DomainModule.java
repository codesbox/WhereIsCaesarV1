package com.example.whereiscaesar.presentation.di;

import com.example.data.storage.CategoryOrDishStorage;
import com.example.data.storage.firebase.CategoryOrDishStorageImpl;
import com.example.domain.listener.CategoryAndDishListener;
import com.example.domain.repository.CategoryOrDishRepository;
import com.example.domain.useCase.GetCategoryAndDishUseCase;
import com.example.whereiscaesar.presentation.viewmodels.SearchFragmentViewModel;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(ViewModelComponent.class)
public class DomainModule {


    @Provides
    public static GetCategoryAndDishUseCase provideGetCategoryAndDishUseCase(CategoryOrDishRepository categoryOrDishRepository, CategoryAndDishListener listener){
        return new GetCategoryAndDishUseCase(categoryOrDishRepository, listener);
    }




}
