package com.example.whereiscaesar.presentation.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whereiscaesar.R;

public class SearchFragment extends Fragment {

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("Q", "onAttachFragment");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Q", "onCreateFragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Q", "onCreateViewFragment");
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Q", "onStartFragment");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Q", "onResumeFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Q", "onPauseFragment");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("Q", "onStopFragment");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Q", "onDestroyViewFragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Q", "onDestroyFragment");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("Q", "onDetachFragment");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Q", "onSaveFragment");
    }
}