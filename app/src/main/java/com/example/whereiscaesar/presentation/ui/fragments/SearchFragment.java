package com.example.whereiscaesar.presentation.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.domain.models.CategoryOrDishModelDomain;
import com.example.whereiscaesar.R;
import com.example.whereiscaesar.databinding.FragmentSearchBinding;
import com.example.whereiscaesar.presentation.ui.recycler.MyAdapter;
import com.example.whereiscaesar.presentation.ui.recycler.MyResultAdapter;
import com.example.whereiscaesar.presentation.util.CardClickListener;
import com.example.whereiscaesar.presentation.util.ResultCardClickListener;
import com.example.whereiscaesar.presentation.viewmodels.SearchFragmentViewModel;
import com.example.whereiscaesar.presentation.viewmodels.SearchFragmentViewModelFactory;
import com.example.whereiscaesar.presentation.viewmodels.SharedViewModel;


public class SearchFragment extends Fragment {

    private MyAdapter adapter;
    private MyResultAdapter resultAdapter;
    private CardClickListener listener;
    private ResultCardClickListener resultListener;


    private SearchFragmentViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LIVE", "SEARCH_FRAGMENT_ONCREATE");
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSearchBinding binding = FragmentSearchBinding.inflate(LayoutInflater.from(requireContext()));
        Log.d("LIVE", "SEARCH_FRAGMENT_ONCREATEVIEW");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentSearchBinding binding = FragmentSearchBinding.bind(view);
        Log.d("LIVE", "SEARCH_FRAGMENT_ONVIEWCREATED");
        listener = new CardClickListener() {
            @Override
            public void onCardClick(CategoryOrDishModelDomain categoryOrDishModelDomain) {
                Bundle bundle = new Bundle();
                bundle.putString("categoryName", categoryOrDishModelDomain.title);
                bundle.putString("imageUrl", categoryOrDishModelDomain.imageUrl);
                bundle.putBoolean("isCategory", categoryOrDishModelDomain.isCategory);
                Navigation.findNavController(requireActivity(), R.id.fragmentContainerViewMain).navigate(R.id.categoryFragment, bundle);
            }


            };
        resultListener = new ResultCardClickListener() {
            @Override
            public void onButtonClick(CategoryOrDishModelDomain categoryOrDishModelDomain) {
                resultAdapter.deleteItem(categoryOrDishModelDomain);
            }
        };

        adapter = new MyAdapter(getContext(), listener);
        RecyclerView recyclerView = binding.recyclerview;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        resultAdapter = new MyResultAdapter(getContext(), resultListener);
        RecyclerView resultRecyclerView = binding.resultRecyclerview;
        resultRecyclerView.setAdapter(resultAdapter);
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel = new ViewModelProvider(this, new SearchFragmentViewModelFactory()).get(SearchFragmentViewModel.class);
        viewModel.getCategoryOrDishModelDomain().observe(getViewLifecycleOwner(), categoryOrDishModelDomains -> adapter.setItemList(categoryOrDishModelDomains));
        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(SharedViewModel.class);
        sharedViewModel.getData().observe(getViewLifecycleOwner(), categoryOrDishModelDomainList -> {
            resultAdapter.setItemList(categoryOrDishModelDomainList);
        });


        binding.button3.setOnClickListener(v -> {
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerViewMain).navigate(R.id.searchBoxFragment);
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d("LIVE", "SEARCH_FRAGMENT_ONSTART");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("LIVE", "SEARCH_FRAGMENT_ONRESUME");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("LIVE", "SEARCH_FRAGMENT_ONPAUSE");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("LIVE", "SEARCH_FRAGMENT_ONSTOP");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("LIVE", "SEARCH_FRAGMENT_ONDESTROYVIEW");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("LIVE", "SEARCH_FRAGMENT_ONDESTROY");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("LIVE", "SEARCH_FRAGMENT_ONDETACH");
    }
}