package com.example.whereiscaesar.presentation.ui.fragments.accountFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whereiscaesar.R;
import com.example.whereiscaesar.databinding.FragmentMyFeedbacksBinding;
import com.example.whereiscaesar.presentation.ui.recycler.MapAdapter;
import com.example.whereiscaesar.presentation.ui.recycler.MapFeedbackAdapter;
import com.example.whereiscaesar.presentation.ui.recycler.MyFeedbackAdapter;
import com.example.whereiscaesar.presentation.util.MyFeedback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class MyFeedbacksFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_feedbacks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentMyFeedbacksBinding binding = FragmentMyFeedbacksBinding.bind(view);
        List<MyFeedback> myFeedbackList = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference feedbacksRef = db.collection("Feedbacks");

        feedbacksRef.whereEqualTo("userId", FirebaseAuth.getInstance().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                List<DocumentSnapshot> documentSnapshots = querySnapshot.getDocuments();
                                for(DocumentSnapshot documentSnapshot: documentSnapshots){
                                    myFeedbackList.add(new MyFeedback(documentSnapshot.getString("restaurantName"), documentSnapshot.getString("dishName"), documentSnapshot.getString("feedbackText"), documentSnapshot.getDouble("estimation").intValue()));
                                }

                                MyFeedbackAdapter adapter = new MyFeedbackAdapter(getContext());
                                RecyclerView recyclerView = binding.recyclerView;
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                adapter.setItemList(myFeedbackList);

                            } else {
                                Log.d("FirestoreExample", "Документ не найден");
                            }
                        } else {
                            Log.e("FirestoreExample", "Ошибка при получении документа: ", task.getException());
                        }
                    }
                });
    }
}