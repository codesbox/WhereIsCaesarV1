package com.example.whereiscaesar.presentation.ui.fragments;

import static com.example.whereiscaesar.presentation.app.App.qqq;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.whereiscaesar.R;

import com.example.whereiscaesar.databinding.DishCardLayoutBinding;
import com.example.whereiscaesar.databinding.FragmentMapBinding;
import com.example.whereiscaesar.databinding.RestaurantLayoutBinding;
import com.example.whereiscaesar.presentation.ui.recycler.MapAdapter;
import com.example.whereiscaesar.presentation.ui.recycler.MapFeedbackAdapter;
import com.example.whereiscaesar.presentation.ui.recycler.MyAdapter;
import com.example.whereiscaesar.presentation.util.Feedback;
import com.example.whereiscaesar.presentation.util.MapClicCardListener;
import com.example.whereiscaesar.presentation.util.MapDishCard;
import com.example.whereiscaesar.presentation.util.MapTapListener;
import com.example.whereiscaesar.presentation.util.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.type.LatLng;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateReason;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.Rect;
import com.yandex.mapkit.map.VisibleRegion;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MapFragment extends Fragment {
    private MapView mapView;


    double yotcLat = 55.751244;
    double yotcLon = 37.618426;
    private static boolean  isMapKitInitialized = false;









    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (qqq){

        }else{
            MapKitFactory.setApiKey("6c6cd304-9d0b-4a28-a718-27e056899465");
            qqq = true;
        }
        FragmentMapBinding binding = FragmentMapBinding.inflate(LayoutInflater.from(requireContext()));

        if (isMapKitInitialized) {

        } else {
            MapKitFactory.initialize(requireContext());
            isMapKitInitialized = true;
        }
        mapView = binding.mapView;
        return binding.getRoot();
    }


    private FirebaseFirestore db;
    private List<Restaurant> restaurantList;
    LinearLayout bottomSheetContainer;
    BottomSheetBehavior<LinearLayout> bottomSheetBehavior;
    PlacemarkMapObject marker;





    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentMapBinding binding = FragmentMapBinding.bind(view);

        bottomSheetContainer = binding.bottomSheetContainer;
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetContainer);



        db = FirebaseFirestore.getInstance();
        restaurantList = new ArrayList<>();

        CollectionReference restaurantsRef = db.collection("Restaurants");
        restaurantsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null) {
                    Log.d("wwwwww", String.valueOf(querySnapshot.size()));
                    for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                        Log.d("Я ТУТУ", "sdsdsds");
                        String name = documentSnapshot.getString("name");
                        GeoPoint geoPoint = documentSnapshot.getGeoPoint("geopoint");
                        double latitude = geoPoint.getLatitude();
                        double longitude = geoPoint.getLongitude();
                        Integer allSum = documentSnapshot.getDouble("allSum").intValue();
                        Integer allCount = documentSnapshot.getDouble("allCount").intValue();

                        Point point = new Point(latitude, longitude);
                        Restaurant restaurant = new Restaurant(name, point, allSum, allCount);
                        restaurantList.add(restaurant);
                    }
                    ImageProvider imageProvider = ImageProvider.fromResource(
                            requireContext(),
                            R.drawable.free);
                    List<PlacemarkMapObject> markers = new ArrayList<>();

                    for (Restaurant restaurant : restaurantList){
                        marker = mapView.getMap().getMapObjects().addPlacemark(restaurant.point, imageProvider);
                        marker.setDraggable(true);
                        ArrayList<String> list = new ArrayList<>();
                        list.add(restaurant.name);
                        list.add(restaurant.allCount.toString());
                        list.add(restaurant.allSum.toString());
                        list.add(String.valueOf(restaurant.point.getLongitude()) + " " + String.valueOf(restaurant.point.getLatitude()));
                        marker.setUserData(list);

                        marker.addTapListener(new MapTapListener(requireContext(), bottomSheetContainer, bottomSheetBehavior, marker));
                        markers.add(marker);
                    }
                    // Выполнение действий с полученным массивом restaurantList
                }
            } else {
                // Обработка ошибки
            }
        });










        ImageProvider imageProvider = ImageProvider.fromResource(
                requireContext(),
                R.drawable.free);


        mapView.getMap().move(
                new CameraPosition(new Point(yotcLat, yotcLon), 10.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 1),
                null);
        Log.d("qqqqq", String.valueOf(restaurantList.size()));
        //bottomSheetBehavior.setPeekHeight(300);
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setHalfExpandedRatio(0.5f);











    }
    private final MapObjectTapListener mapObjectTapListener = new MapObjectTapListener() {
        @Override
        public boolean onMapObjectTap(@NonNull MapObject mapObject, @NonNull Point point) {
            View bottomSheetView = LayoutInflater.from(requireContext()).inflate(R.layout.restaurant_layout, null);
            bottomSheetContainer.removeAllViews();
            bottomSheetContainer.addView(bottomSheetView);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            RestaurantLayoutBinding binding1 = RestaurantLayoutBinding.bind(bottomSheetView);

            binding1.restaurantName.setText((String) marker.getUserData());

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference restaurantsRef = db.collection("Restaurants");
            DocumentReference docRef = restaurantsRef.document(marker.getUserData().toString());

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
                                    hotDishes.add(new MapDishCard(entry.getKey(), (String) dishValueMap.get("imageUrl"), (Integer) dishValueMap.get("count"), (Integer) dishValueMap.get("sum")));
                                }
                                if (Objects.equals((String) dishValueMap.get("category"), "Салаты и закуски")){
                                    salats.add(new MapDishCard(entry.getKey(), (String) dishValueMap.get("imageUrl"), (Integer) dishValueMap.get("count"), (Integer) dishValueMap.get("sum")));
                                }
                                if (Objects.equals((String) dishValueMap.get("category"), "Десерты и выпечка")){
                                    desserts.add(new MapDishCard(entry.getKey(), (String) dishValueMap.get("imageUrl"), (Integer) dishValueMap.get("count"), (Integer) dishValueMap.get("sum")));
                                }
                                if (Objects.equals((String) dishValueMap.get("category"), "Напитки")){
                                    drinks.add(new MapDishCard(entry.getKey(), (String) dishValueMap.get("imageUrl"), (Integer) dishValueMap.get("count"), (Integer) dishValueMap.get("sum")));
                                }
                                if (Objects.equals((String) dishValueMap.get("category"), "Супы и бульоны")){
                                    soups.add(new MapDishCard(entry.getKey(), (String) dishValueMap.get("imageUrl"), ((Double) dishValueMap.get("count")).intValue(), ((Double) dishValueMap.get("sum")).intValue()));
                                }


                            }
                            MapClicCardListener listener = new MapClicCardListener() {
                                @Override
                                public void onCardClick(MapDishCard mapDishCard) {
                                    View bottomSheetView2 = LayoutInflater.from(requireContext()).inflate(R.layout.dish_card_layout, null);
                                    bottomSheetContainer.removeAllViews();
                                    bottomSheetContainer.addView(bottomSheetView2);
                                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                    DishCardLayoutBinding binding2 = DishCardLayoutBinding.bind(bottomSheetView2);

                                    binding2.dishName.setText(mapDishCard.dishName);
                                    binding2.estimation.setText(mapDishCard.estimation.toString());
                                    binding2.feedBackCount.setText(String.format("Оценок: %s", mapDishCard.counter.toString()));


                                    CollectionReference feedbacksRef = db.collection("Feedbacks");

                                    feedbacksRef.whereEqualTo("dishName", mapDishCard.dishName)
                                            .whereEqualTo("restaurantName", marker.getUserData().toString())
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
                                                        MapFeedbackAdapter feedbackadapter = new MapFeedbackAdapter(getContext());
                                                        RecyclerView feedbackrecyclerView = binding2.recyclerview;
                                                        feedbackrecyclerView.setAdapter(feedbackadapter);
                                                        feedbackrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                                        feedbackadapter.setItemList(feedbackList);


                                                    } else {
                                                        Log.e("FirestoreExample", "Ошибка при получении документов: ", task.getException());
                                                    }
                                                }
                                            });


                                }
                            };

                            MapAdapter hotadapter = new MapAdapter(getContext(), listener);
                            RecyclerView hotrecyclerView = binding1.hotDishRecyclerView;
                            hotrecyclerView.setAdapter(hotadapter);
                            hotrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                            MapAdapter salatsadapter = new MapAdapter(getContext(), listener);
                            RecyclerView salatrecyclerView = binding1.saladsAndSnacksRecyclerView;
                            salatrecyclerView.setAdapter(salatsadapter);
                            salatrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                            MapAdapter dessertadapter = new MapAdapter(getContext(), listener);
                            RecyclerView dessertsrecyclerView = binding1.dessertsRecyclerView;
                            dessertsrecyclerView.setAdapter(dessertadapter);
                            dessertsrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                            MapAdapter drinksadapter = new MapAdapter(getContext(), listener);
                            RecyclerView drinkrecyclerView = binding1.drinksRecyclerView;
                            drinkrecyclerView.setAdapter(drinksadapter);
                            drinkrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                            MapAdapter soupsadapter = new MapAdapter(getContext(), listener);
                            RecyclerView souprecyclerView = binding1.soupsAndBrothsRecyclerView;
                            souprecyclerView.setAdapter(soupsadapter);
                            souprecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                            hotadapter.setItemList(hotDishes);
                            salatsadapter.setItemList(salats);
                            dessertadapter.setItemList(desserts);
                            drinksadapter.setItemList(drinks);
                            salatsadapter.setItemList(soups);



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
    };




    @Override
    public void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();

    }
}