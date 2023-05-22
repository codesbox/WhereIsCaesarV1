package com.example.whereiscaesar.presentation.ui.fragments.accountFragments;

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

import com.example.whereiscaesar.R;
import com.example.whereiscaesar.databinding.FragmentProfileBinding;
import com.example.whereiscaesar.presentation.util.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfileFragment extends Fragment {
    private User user;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentProfileBinding binding = FragmentProfileBinding.bind(view);


        FirebaseFirestore db = FirebaseFirestore.getInstance();

// Получаем идентификатор пользователя, зарегистрированного в приложении
        String userId = FirebaseAuth.getInstance().getUid();// Замените на реальный идентификатор пользователя

// Ссылка на коллекцию "users"
        CollectionReference usersCollection = db.collection("Users");

// Выполняем запрос
        usersCollection.whereEqualTo("id", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (!querySnapshot.isEmpty()) {

                            // Получаем первый документ из результатов запроса
                            DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                            String firstName = documentSnapshot.getString("firstName");
                            String lastName = documentSnapshot.getString("lastName");
                            String id = documentSnapshot.getString("id");
                            Long feedbackC = documentSnapshot.getLong("feedbackCount");
                            Integer feedbackCount = 0;
                            if (feedbackC != null) {
                                feedbackCount = feedbackC.intValue();
                            }
                            user = new User(firstName, lastName, id, feedbackCount + 1);
                            binding.userName.setText(String.format("Здравствуйте, %s", user.firstName));
                            binding.userCount.setText(String.format("Критик еды %s уровня", user.feedbackCount.toString()));


                        } else {
                            // Документ с указанным идентификатором не найден
                        }
                    } else {
                        // Обработка ошибки запроса
                        Exception e = task.getException();
                        // TODO: Обработка ошибки
                    }
                });



        binding.qqq.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            NavHostFragment.findNavController(this).navigate(R.id.action_profileFragment_to_mainAccountFragment);
        });
        binding.addFeedback.setOnClickListener(v -> {
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerViewMain).navigate(R.id.feedBackFragment);

        });
        binding.myFeedbacks.setOnClickListener(v -> {
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerViewMain).navigate(R.id.myFeedbacksFragment);

        });
    }
}