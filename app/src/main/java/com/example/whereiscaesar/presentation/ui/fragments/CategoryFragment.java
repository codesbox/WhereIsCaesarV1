package com.example.whereiscaesar.presentation.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.data.repository.CategoryOrDishRepositoryImpl;
import com.example.data.storage.CategoryOrDishStorage;
import com.example.data.storage.firebase.CategoryOrDishStorageImpl;
import com.example.domain.listener.CategoryAndDishListener;
import com.example.domain.models.CategoryOrDishModelDomain;
import com.example.domain.repository.CategoryOrDishRepository;
import com.example.domain.useCase.GetCategoryAndDishUseCase;
import com.example.whereiscaesar.R;
import com.example.whereiscaesar.databinding.FragmentCategoryBinding;
import com.example.whereiscaesar.presentation.ui.recycler.MyAdapter;
import com.example.whereiscaesar.presentation.util.CardClickListener;
import com.example.whereiscaesar.presentation.viewmodels.SharedViewModel;

import java.util.List;

public class CategoryFragment extends Fragment {
    String categoryName;
    String imageUrl;
    Boolean isCategory;

    private MyAdapter adapter;
    private CardClickListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LIVE", "CATEGORY_FRAGMENT_ONCREATE");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("IDDDDDD", String.valueOf(R.id.categoryFragment));
        FragmentCategoryBinding binding = FragmentCategoryBinding.inflate(LayoutInflater.from(requireContext()));
        Log.d("LIVE", "CATEGORY_FRAGMENT_ONCREATEVIEW");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("LIVE", "CATEGORY_FRAGMENT_ONVIEWCREATED");
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


        listener = new CardClickListener() {
            @Override
            public void onCardClick(CategoryOrDishModelDomain categoryOrDishModelDomain) {
                if (categoryOrDishModelDomain.isCategory){

                    Bundle bundle = new Bundle();
                    bundle.putString("categoryName", categoryOrDishModelDomain.title);
                    bundle.putString("imageUrl", categoryOrDishModelDomain.imageUrl);
                    bundle.putBoolean("isCategory", categoryOrDishModelDomain.isCategory);
                    Navigation.findNavController(requireActivity(), R.id.fragmentContainerViewMain).navigate(R.id.categoryFragment, bundle);
                }
                else{

                    SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(SharedViewModel.class);
                    sharedViewModel.addElement(categoryOrDishModelDomain);
                    //NavController navController = Navigation.findNavController(requireView());
                    //navController.navigate(R.id.searchFragment);

                    //NavHostFragment.findNavController(CategoryFragment.this).popBackStack(R.id.searchFragment, false);
                    //Navigation.findNavController(requireActivity(), R.id.fragmentContainerViewMain).navigate(R.id.tabsFragment);
                    Navigation.findNavController(requireActivity(), R.id.fragmentContainerViewMain).popBackStack(R.id.tabsFragment, false);


                }


            }


        };
        adapter = new MyAdapter(getContext(), listener);
        RecyclerView recyclerView = binding.recyclerview;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        CategoryOrDishStorage categoryOrDishStorage = new CategoryOrDishStorageImpl();
        CategoryOrDishRepository categoryOrDishRepository = new CategoryOrDishRepositoryImpl(categoryOrDishStorage);
        CategoryAndDishListener categoryAndDishListener = new CategoryAndDishListener() {
            @Override
            public void getCategoryAndDish(List<CategoryOrDishModelDomain> categoryOrDishModelDomainList) {
                adapter.setItemList(categoryOrDishModelDomainList);
            }
        };
        GetCategoryAndDishUseCase getCategoryAndDishUseCase = new GetCategoryAndDishUseCase(categoryOrDishRepository, categoryAndDishListener, categoryName);
        getCategoryAndDishUseCase.execute();


    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("LIVE", "CATEGORY_FRAGMENT_ONSTART");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("LIVE", "CATEGORY_FRAGMENT_ONRESUME");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("LIVE", "CATEGORY_FRAGMENT_ONPAUSE");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("LIVE", "CATEGORY_FRAGMENT_ONSTOP");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("LIVE", "CATEGORY_FRAGMENT_ONDESTROYVIEW");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("LIVE", "CATEGORY_FRAGMENT_ONDESTROY");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("LIVE", "CATEGORY_FRAGMENT_ONDETACH");
    }
}