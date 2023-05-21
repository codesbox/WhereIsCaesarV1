package com.example.whereiscaesar.presentation.ui.fragments.accountFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whereiscaesar.R;
import com.example.whereiscaesar.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentProfileBinding binding = FragmentProfileBinding.bind(view);

//        binding.button5.setOnClickListener(v -> {
//            FirebaseAuth.getInstance().signOut();
//            NavHostFragment.findNavController(this).navigate(R.id.action_profileFragment_to_mainAccountFragment);
//        });
//        binding.addFeedBackButton.setOnClickListener(v -> {
//            Navigation.findNavController(requireActivity(), R.id.fragmentContainerViewMain).navigate(R.id.feedBackFragment);
//
//        });
    }
}