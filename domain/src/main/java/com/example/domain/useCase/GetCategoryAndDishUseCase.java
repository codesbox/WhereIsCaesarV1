package com.example.domain.useCase;


import com.example.domain.listener.CategoryAndDishListener;
import com.example.domain.repository.CategoryOrDishRepository;

import javax.inject.Inject;

public class GetCategoryAndDishUseCase {


    CategoryOrDishRepository categoryOrDishRepository;
    CategoryAndDishListener listener;


    public GetCategoryAndDishUseCase(CategoryOrDishRepository categoryOrDishRepository, CategoryAndDishListener listener){
        this.categoryOrDishRepository = categoryOrDishRepository;
        this.listener = listener;
    }
    public void execute(){
         categoryOrDishRepository.GetCategoryOrDish("Категории и блюда", listener);
    }
}
