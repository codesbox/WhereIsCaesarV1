package com.example.whereiscaesar.presentation.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.whereiscaesar.R;
import com.example.whereiscaesar.databinding.FragmentCategoryBinding;
import com.example.whereiscaesar.databinding.FragmentSearchBinding;

public class CategoryFragment extends Fragment {
    String categoryName;
    String imageUrl;
    Boolean isCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCategoryBinding binding = FragmentCategoryBinding.inflate(LayoutInflater.from(requireContext()));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentCategoryBinding binding = FragmentCategoryBinding.bind(view);
        categoryName = getArguments().getString("categoryName");
        imageUrl = getArguments().getString("imageUrl");
        isCategory = getArguments().getBoolean("isCategory");
        Glide.with(requireContext())
                .load(imageUrl)
                .error(R.drawable.image_not_supported)
                .into(binding.topImage);
        binding.topCategoryName.setText(categoryName);
        binding.categoryNameTextView.setText(categoryName);
        binding.popToBackStackButton.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).popBackStack();
        });





    }
}