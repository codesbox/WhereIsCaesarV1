package com.example.whereiscaesar.presentation.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.domain.models.CategoryOrDishModelDomain;
import com.example.whereiscaesar.R;
import com.example.whereiscaesar.databinding.FragmentSearchBinding;
import com.example.whereiscaesar.presentation.ui.recycler.MyAdapter;
import com.example.whereiscaesar.presentation.viewmodels.SearchFragmentViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


public class SearchFragment extends Fragment {

    private MyAdapter adapter;


    private SearchFragmentViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSearchBinding binding = FragmentSearchBinding.inflate(LayoutInflater.from(requireContext()));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentSearchBinding binding = FragmentSearchBinding.bind(view);

        adapter = new MyAdapter(getContext());
        RecyclerView recyclerView = binding.recyclerview;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel = new ViewModelProvider(this).get(SearchFragmentViewModel.class);
        viewModel.getItems().observe(getViewLifecycleOwner(), new Observer<List<CategoryOrDishModelDomain>>() {
            @Override
            public void onChanged(List<CategoryOrDishModelDomain> categoryOrDishModelDomains) {
                adapter.setItemList(categoryOrDishModelDomains);
            }
        });




        binding.searchToSearchBox.setOnClickListener(v -> {
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(R.id.searchBoxFragment);
        });




    }


}