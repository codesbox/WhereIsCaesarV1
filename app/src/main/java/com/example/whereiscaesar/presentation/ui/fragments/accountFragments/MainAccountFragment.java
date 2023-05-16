package com.example.whereiscaesar.presentation.ui.fragments.accountFragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whereiscaesar.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainAccountFragment extends Fragment {

    private FirebaseAuth mAuth;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            NavHostFragment.findNavController(MainAccountFragment.this).navigate(R.id.action_mainAccountFragment_to_profileFragment);
        }
        else{
            NavHostFragment.findNavController(MainAccountFragment.this).navigate(R.id.action_mainAccountFragment_to_signInFragment);
        }
    }
}