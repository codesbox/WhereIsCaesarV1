package com.example.whereiscaesar.presentation.ui.fragments.accountFragments;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.whereiscaesar.R;
import com.example.whereiscaesar.databinding.FragmentSignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpFragment extends Fragment {


    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentSignUpBinding binding = FragmentSignUpBinding.bind(view);
        mAuth = FirebaseAuth.getInstance();

        binding.signUpButton.setOnClickListener(v -> {

            String email = binding.email.getText().toString();
            String password = binding.password.getText().toString();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");

                                String userId = task.getResult().getUser().getUid();
                                String firstName = binding.firstName.getText().toString();
                                String lastName = binding.lastName.getText().toString();

                                Map<String, Object> userData = new HashMap<>();
                                userData.put("id", userId);
                                userData.put("firstName", firstName);
                                userData.put("lastName", lastName);
                                userData.put("feedbackCount", 0);// Replace with the actual name value
                                // Add new document to Users collection
                                FirebaseFirestore.getInstance().collection("Users").document(userId)
                                        .set(userData)
                                        .addOnSuccessListener(aVoid -> {
                                            // Document added successfully
                                            NavHostFragment.findNavController(SignUpFragment.this).popBackStack();
                                        })
                                        .addOnFailureListener(e -> {
                                            // Error adding document
                                            Log.e(TAG, "Error adding user document", e);
                                            Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                                        });

                            } else {
                                Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            }
                        }
                    });



        });
        binding.signInButton.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).popBackStack();
        });
    }
}