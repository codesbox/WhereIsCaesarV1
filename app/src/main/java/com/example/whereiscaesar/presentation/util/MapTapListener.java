package com.example.whereiscaesar.presentation.util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whereiscaesar.R;
import com.example.whereiscaesar.databinding.DishCardLayoutBinding;
import com.example.whereiscaesar.databinding.RestaurantLayoutBinding;
import com.example.whereiscaesar.presentation.ui.recycler.MapAdapter;
import com.example.whereiscaesar.presentation.ui.recycler.MapFeedbackAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.PlacemarkMapObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MapTapListener implements MapObjectTapListener {
    android.content.Context context;
    LinearLayout bottomSheetContainer;
    BottomSheetBehavior bottomSheetBehavior;
    PlacemarkMapObject marker;

    public MapTapListener(Context context, LinearLayout bottomSheetContainer, BottomSheetBehavior bottomSheetBehavior, PlacemarkMapObject marker){
        this.context = context;
        this.bottomSheetContainer = bottomSheetContainer;
        this.bottomSheetBehavior = bottomSheetBehavior;
        this.marker = marker;




    }
    @Override
    public boolean onMapObjectTap(@NonNull MapObject mapObject, @NonNull Point point) {
        View bottomSheetView = LayoutInflater.from(context).inflate(R.layout.restaurant_layout, null);
        bottomSheetContainer.removeAllViews();
        bottomSheetContainer.addView(bottomSheetView);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        RestaurantLayoutBinding binding1 = RestaurantLayoutBinding.bind(bottomSheetView);
        List<String> list = (List<String>) marker.getUserData();

        binding1.restaurantName.setText(list.get(0));
        binding1.imageView3.setText(String.valueOf(Integer.parseInt(list.get(2)) / Integer.parseInt(list.get(1))));
        binding1.feedBackCount.setText(String.format("Оценок: %s", list.get(1)));
        binding1.textView10.setText(list.get(3));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference restaurantsRef = db.collection("Restaurants");
        DocumentReference docRef = restaurantsRef.document(list.get(0));

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String json = new Gson().toJson(document.get("dishes"));
                        Type type = new TypeToken<Map<String, Object>>(){}.getType();
                        Map<String, Object> dishesMap = new Gson().fromJson(json, type);
                        Log.d("BBBBBBBBB", String.valueOf(dishesMap.size()));
                        List<MapDishCard> salats = new ArrayList<>();
                        List<MapDishCard> hotDishes = new ArrayList<>();
                        List<MapDishCard> soups = new ArrayList<>();
                        List<MapDishCard> drinks = new ArrayList<>();
                        List<MapDishCard> desserts = new ArrayList<>();
                        for (Map.Entry<String, Object> entry : dishesMap.entrySet()){
                            Map<String, Object> dishValueMap = (Map<String, Object>) entry.getValue();
                            if (Objects.equals((String) dishValueMap.get("category"), "Горячие блюда")){
                                hotDishes.add(new MapDishCard(entry.getKey(), (String) dishValueMap.get("imageUrl"), ((Double) dishValueMap.get("count")).intValue(), ((Double) dishValueMap.get("sum")).intValue()));
                            }
                            if (Objects.equals((String) dishValueMap.get("category"), "Салаты и закуски")){
                                salats.add(new MapDishCard(entry.getKey(), (String) dishValueMap.get("imageUrl"), ((Double) dishValueMap.get("count")).intValue(), ((Double) dishValueMap.get("sum")).intValue()));
                            }
                            if (Objects.equals((String) dishValueMap.get("category"), "Десерты и выпечка")){
                                desserts.add(new MapDishCard(entry.getKey(), (String) dishValueMap.get("imageUrl"), ((Double) dishValueMap.get("count")).intValue(), ((Double) dishValueMap.get("sum")).intValue()));
                            }
                            if (Objects.equals((String) dishValueMap.get("category"), "Напитки")){
                                drinks.add(new MapDishCard(entry.getKey(), (String) dishValueMap.get("imageUrl"), ((Double) dishValueMap.get("count")).intValue(), ((Double) dishValueMap.get("sum")).intValue()));
                            }
                            if (Objects.equals((String) dishValueMap.get("category"), "Супы и бульоны")){
                                soups.add(new MapDishCard(entry.getKey(), (String) dishValueMap.get("imageUrl"), ((Double) dishValueMap.get("count")).intValue(), ((Double) dishValueMap.get("sum")).intValue()));
                            }


                        }
                        MapClicCardListener listener = new MapClicCardListener() {
                            @Override
                            public void onCardClick(MapDishCard mapDishCard) {
                                View bottomSheetView2 = LayoutInflater.from(context).inflate(R.layout.dish_card_layout, null);
                                bottomSheetContainer.removeAllViews();
                                bottomSheetContainer.addView(bottomSheetView2);
                                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                DishCardLayoutBinding binding2 = DishCardLayoutBinding.bind(bottomSheetView2);

                                binding2.dishName.setText(mapDishCard.dishName);
                                binding2.estimation.setText(mapDishCard.estimation.toString());
                                binding2.feedBackCount.setText(String.format("Оценок: %s", mapDishCard.counter.toString()));


                                CollectionReference feedbacksRef = db.collection("Feedbacks");

                                feedbacksRef.whereEqualTo("dishName", mapDishCard.dishName)
                                        .whereEqualTo("restaurantName", list.get(0))
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    // Получение списка документов
                                                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                                                    List<Feedback> feedbackList = new ArrayList<>();
                                                    for (DocumentSnapshot document : documents) {
                                                        feedbackList.add(new Feedback(document.getString("firstName") + " " + document.getString("lastName"), document.getDouble("estimation").intValue(), document.getString("feedbackText"), document.getDouble("userLevel").intValue()));






                                                    }
                                                    MapFeedbackAdapter feedbackadapter = new MapFeedbackAdapter(context);
                                                    RecyclerView feedbackrecyclerView = binding2.recyclerview;
                                                    feedbackrecyclerView.setAdapter(feedbackadapter);
                                                    feedbackrecyclerView.setLayoutManager(new LinearLayoutManager(context));
                                                    feedbackadapter.setItemList(feedbackList);


                                                } else {
                                                    Log.e("FirestoreExample", "Ошибка при получении документов: ", task.getException());
                                                }
                                            }
                                        });


                            }
                        };

                        MapAdapter hotadapter = new MapAdapter(context, listener);
                        RecyclerView hotrecyclerView = binding1.hotDishRecyclerView;
                        hotrecyclerView.setAdapter(hotadapter);
                        hotrecyclerView.setLayoutManager(new LinearLayoutManager(context));

                        MapAdapter salatsadapter = new MapAdapter(context, listener);
                        RecyclerView salatrecyclerView = binding1.saladsAndSnacksRecyclerView;
                        salatrecyclerView.setAdapter(salatsadapter);
                        salatrecyclerView.setLayoutManager(new LinearLayoutManager(context));

                        MapAdapter dessertadapter = new MapAdapter(context, listener);
                        RecyclerView dessertsrecyclerView = binding1.dessertsRecyclerView;
                        dessertsrecyclerView.setAdapter(dessertadapter);
                        dessertsrecyclerView.setLayoutManager(new LinearLayoutManager(context));

                        MapAdapter drinksadapter = new MapAdapter(context, listener);
                        RecyclerView drinkrecyclerView = binding1.drinksRecyclerView;
                        drinkrecyclerView.setAdapter(drinksadapter);
                        drinkrecyclerView.setLayoutManager(new LinearLayoutManager(context));

                        MapAdapter soupsadapter = new MapAdapter(context, listener);
                        RecyclerView souprecyclerView = binding1.soupsAndBrothsRecyclerView;
                        souprecyclerView.setAdapter(soupsadapter);
                        souprecyclerView.setLayoutManager(new LinearLayoutManager(context));

                        hotadapter.setItemList(hotDishes);
                        salatsadapter.setItemList(salats);
                        dessertadapter.setItemList(desserts);
                        drinksadapter.setItemList(drinks);
                        soupsadapter.setItemList(soups);



                        Log.d("fgfgggv", String.valueOf(soups.size()));





                    } else {
                        Log.d("Firestore", "Документ не существует");
                    }
                } else {
                    Log.d("Firestore", "Ошибка получения документа: " + task.getException());
                }
            }
        });



        return false;
    }
}
