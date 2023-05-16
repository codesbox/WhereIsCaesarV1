package com.example.whereiscaesar.presentation.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whereiscaesar.R;
import com.example.whereiscaesar.databinding.FragmentTabsBinding;

public class TabsFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LIVE", "TABS_FRAGMENT_ONCREATE");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("LIVE", "TABS_FRAGMENT_ONCREATEVIEW");

        return inflater.inflate(R.layout.fragment_tabs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentTabsBinding binding = FragmentTabsBinding.bind(view);

        NavHost navHost = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.fragmentContainerViewTabs);
        NavController navController = navHost.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavMenu, navController);
        Log.d("LIVE", "TABS_FRAGMENT_ONVIEWCREATED");
    }



    @Override
    public void onStart() {
        super.onStart();
        Log.d("LIVE", "TABS_FRAGMENT_ONSTART");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("LIVE", "TABS_FRAGMENT_ONRESUME");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("LIVE", "TABS_FRAGMENT_ONPAUSE");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("LIVE", "TABS_FRAGMENT_ONSTOP");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("LIVE", "TABS_FRAGMENT_ONDESTROYVIEW");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("LIVE", "TABS_FRAGMENT_ONDESTROY");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("LIVE", "TABS_FRAGMENT_ONDETACH");
    }
}