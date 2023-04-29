package com.example.whereiscaesar.presentation.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import android.os.Bundle;
import com.example.whereiscaesar.R;
import com.example.whereiscaesar.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        NavigationUI.setupWithNavController(bottomNavigationView, navController);


    }
}