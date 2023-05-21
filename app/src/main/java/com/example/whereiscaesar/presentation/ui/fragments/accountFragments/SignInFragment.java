package com.example.whereiscaesar.presentation.ui.fragments.accountFragments;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.whereiscaesar.R;
import com.example.whereiscaesar.databinding.FragmentSignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;


public class SignInFragment extends Fragment {

    private FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentSignInBinding binding = FragmentSignInBinding.bind(view);
        mAuth = FirebaseAuth.getInstance();
        binding.signInButton.setOnClickListener(v -> {

            String email = binding.email.getText().toString();
            String password = binding.password.getText().toString();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                NavHostFragment.findNavController(SignInFragment.this).navigate(R.id.action_signInFragment_to_mainAccountFragment);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());

                            }
                        }
                    });


        });
        binding.signUpButton.setOnClickListener(v -> {
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerViewMain).navigate(R.id.signUpFragment);

        });


    }
}