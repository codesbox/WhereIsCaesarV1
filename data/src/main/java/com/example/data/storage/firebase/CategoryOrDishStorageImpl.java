package com.example.data.storage.firebase;

import static android.content.ContentValues.TAG;
import android.util.Log;

import com.example.data.storage.CategoryOrDishStorage;
import com.example.data.storage.models.CategoryOrDishModel;
import com.example.domain.listener.CategoryAndDishListener;
import com.example.domain.models.CategoryOrDishModelDomain;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class CategoryOrDishStorageImpl implements CategoryOrDishStorage {



    private final String IMAGE_URL = "image";
    private final String IS_CATEGORY = "isc";
    private final String MAIN_TAG = "maintag";
    private final String COLLECTION = "Категории и блюда";



    @Override
    public void GetCategoryOrDish(String tag, CategoryAndDishListener listener) {
        String myValue = "main";

        List<CategoryOrDishModel> documentsList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection(COLLECTION);

        Query query = collectionRef.whereEqualTo(MAIN_TAG, tag);


        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){

                List<DocumentSnapshot> documents = task.getResult().getDocuments();

                for (DocumentSnapshot document : documents){
                    documentsList.add(new CategoryOrDishModel(
                        document.getId(), document.getString(IMAGE_URL), document.getBoolean(IS_CATEGORY)));
                }

                List<CategoryOrDishModelDomain> categoryOrDishModelDomainList = new ArrayList<>();
                for (CategoryOrDishModel document : documentsList){
                    categoryOrDishModelDomainList.add(new CategoryOrDishModelDomain(
                            document.title, document.imageUrl, document.IsCategory)
                    );
                }

                listener.getCategoryAndDish(categoryOrDishModelDomainList);
            }
            else {
                Log.w(TAG, "Error getting documents", task.getException());
            }
        });

    }
}
