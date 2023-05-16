package com.example.domain.useCase;


import com.example.domain.listener.CategoryAndDishListener;
import com.example.domain.repository.CategoryOrDishRepository;

public class GetCategoryAndDishUseCase {


    CategoryOrDishRepository categoryOrDishRepository;
    CategoryAndDishListener listener;
    String tag;


    public GetCategoryAndDishUseCase(CategoryOrDishRepository categoryOrDishRepository, CategoryAndDishListener listener, String tag){
        this.categoryOrDishRepository = categoryOrDishRepository;
        this.listener = listener;
        this.tag = tag;
    }
    public void execute(){
         categoryOrDishRepository.GetCategoryOrDish(tag, listener);
    }
}
