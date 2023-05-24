package com.example.whereiscaesar.presentation.ui.fragments;


import static com.example.whereiscaesar.presentation.app.App.qqq;

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
import android.widget.LinearLayout;

import com.example.whereiscaesar.R;
import com.example.whereiscaesar.databinding.DishCardLayoutBinding;
import com.example.whereiscaesar.databinding.FragmentMapBinding;
import com.example.whereiscaesar.databinding.FragmentMapResultBinding;
import com.example.whereiscaesar.databinding.RestaurantLayoutBinding;
import com.example.whereiscaesar.databinding.RestaurantLayoutResultBinding;
import com.example.whereiscaesar.databinding.RestaurantsListLayoutBinding;
import com.example.whereiscaesar.presentation.ui.recycler.MapAdapter;
import com.example.whereiscaesar.presentation.ui.recycler.MapFeedbackAdapter;
import com.example.whereiscaesar.presentation.ui.recycler.RestaurantsRecyclerView;
import com.example.whereiscaesar.presentation.util.Feedback;
import com.example.whereiscaesar.presentation.util.MapClicCardListener;
import com.example.whereiscaesar.presentation.util.MapDishCard;
import com.example.whereiscaesar.presentation.util.MapTapListener;
import com.example.whereiscaesar.presentation.util.Restaurant;
import com.example.whereiscaesar.presentation.util.RestaurantCard;
import com.example.whereiscaesar.presentation.util.RestaurantResultClickListener;
import com.example.whereiscaesar.presentation.util.ResultRestaurant;
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
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.ScreenRect;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateReason;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.VisibleRegion;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class MapResultFragment extends Fragment {


    private MapView mapView;
    private FirebaseFirestore db;
    private static boolean  isMapKitInitialized = false;
    RestaurantsRecyclerView adapter;
    List<ResultRestaurant> restaurantList;


    double yotcLat = 55.751244;
    double yotcLon = 37.618426;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (qqq){

        }else{
            MapKitFactory.setApiKey("6c6cd304-9d0b-4a28-a718-27e056899465");
            qqq = true;
        }
        FragmentMapResultBinding binding = FragmentMapResultBinding.inflate(LayoutInflater.from(requireContext()));

        if (isMapKitInitialized) {

        } else {
            MapKitFactory.initialize(requireContext());
            isMapKitInitialized = true;
        }
        mapView = binding.mapView;

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentMapResultBinding binding = FragmentMapResultBinding.bind(view);

        ArrayList<String> dishList = getArguments().getStringArrayList("dishList");
        LinearLayout bottomSheetContainer = binding.bottomSheetContainer;
        BottomSheetBehavior<LinearLayout> bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetContainer);

        ImageProvider imageProvider = ImageProvider.fromResource(
                requireContext(),
                R.drawable.free);


        mapView.getMap().move(
                new CameraPosition(new Point(yotcLat, yotcLon), 5.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);



        restaurantList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

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

                        Point point = new Point(latitude, longitude);

                        String json = new Gson().toJson(documentSnapshot.get("dishes"));
                        Type type = new TypeToken<Map<String, Object>>(){}.getType();
                        Map<String, Object> dishesMap = new Gson().fromJson(json, type);
                        List<String> dishNameList = new ArrayList<>();
                        for (Map.Entry<String, Object> entry : dishesMap.entrySet()){
                            dishNameList.add(entry.getKey());

                        }
                        Integer allSum = documentSnapshot.getDouble("allSum").intValue();
                        Integer allCount = documentSnapshot.getDouble("allCount").intValue();
                        ResultRestaurant restaurant = new ResultRestaurant(name, point, dishNameList, allSum, allCount);
                        if (new HashSet<>(restaurant.dishList).containsAll(dishList)){
                            restaurantList.add(restaurant);
                        }


                    }
                    Log.d("ETETDDTYDAAAAAAA", String.valueOf(restaurantList.size()));
                    List<PlacemarkMapObject> markers = new ArrayList<>();
                    for (ResultRestaurant restaurant : restaurantList) {
                        if (new HashSet<>(restaurant.dishList).containsAll(dishList)){
                            PlacemarkMapObject marker = mapView.getMap().getMapObjects().addPlacemark(restaurant.point, imageProvider);
                            marker.setDraggable(true);
                            markers.add(marker);

                        }


                    }

                    View bottomSheetView = LayoutInflater.from(requireContext()).inflate(R.layout.restaurants_list_layout, null);
                    bottomSheetContainer.removeAllViews();
                    bottomSheetContainer.addView(bottomSheetView);
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    bottomSheetBehavior.setHideable(true);
                    bottomSheetBehavior.setHalfExpandedRatio(0.5f);
                    RestaurantsListLayoutBinding binding2 = RestaurantsListLayoutBinding.bind(bottomSheetView);
                    List<RestaurantCard> resultRestaurantListq = new ArrayList<>();
                    Log.d("B", "GHBYVHVHBJH");
                    for (ResultRestaurant resultRestaurant : restaurantList){
                        VisibleRegion visibleRegion = mapView.getMap().getVisibleRegion();
                        Point topLeft = visibleRegion.getTopLeft();
                        Point topRight = visibleRegion.getTopRight();
                        Point bottomLeft = visibleRegion.getBottomLeft();
                        Point bottomRight = visibleRegion.getBottomRight();
                        boolean isVisible = resultRestaurant.point.getLatitude() >= bottomLeft.getLatitude()
                                && resultRestaurant.point.getLatitude() <= topLeft.getLatitude()
                                && resultRestaurant.point.getLongitude() >= topLeft.getLongitude()
                                && resultRestaurant.point.getLongitude() <= topRight.getLongitude()
                                && resultRestaurant.point.getLongitude() >= bottomLeft.getLongitude()
                                && resultRestaurant.point.getLongitude() <= bottomRight.getLongitude();
                        if (isVisible){
                            resultRestaurantListq.add(new RestaurantCard(resultRestaurant.name, resultRestaurant.allSum / resultRestaurant.allCount, 16, resultRestaurant.allCount, resultRestaurant.allSum, resultRestaurant.allCount));

                        }


                    }

                    Log.d("HHHHHHHHHHHH", String.valueOf(resultRestaurantListq.size()));
                    adapter = new RestaurantsRecyclerView(getContext(), new RestaurantResultClickListener() {
                        @Override
                        public void onCardClick(RestaurantCard restaurantCard) {
                            View bottomSheetView = LayoutInflater.from(requireContext()).inflate(R.layout.restaurant_layout_result, null);
                            bottomSheetContainer.removeAllViews();
                            bottomSheetContainer.addView(bottomSheetView);
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            RestaurantLayoutResultBinding binding1 = RestaurantLayoutResultBinding.bind(bottomSheetView);
                            binding1.restaurantName.setText(restaurantCard.restaurantName);
                            binding1.imageView3.setText(String.valueOf(restaurantCard.allSum / restaurantCard.allCount));
                            binding1.feedBackCount.setText(String.format("Оценок: %s", restaurantCard.allCount));
                            //binding1.textView10.setText(restaurantCard.);

                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            CollectionReference restaurantsRef = db.collection("Restaurants");
                            DocumentReference docRef = restaurantsRef.document(restaurantCard.restaurantName);

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
                                            List<MapDishCard> selects = new ArrayList<>();
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
                                                if (dishList.contains(entry.getKey())){
                                                    selects.add(new MapDishCard(entry.getKey(), (String) dishValueMap.get("imageUrl"), ((Double) dishValueMap.get("count")).intValue(), ((Double) dishValueMap.get("sum")).intValue()));
                                                }
                                                Log.d("MMMMMMMMMM", restaurantList.get(0).toString());



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
                                                            .whereEqualTo("restaurantName", restaurantCard.restaurantName)
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

                                            MapAdapter selectadapter = new MapAdapter(getContext(), listener);
                                            RecyclerView selectrecyclerView = binding1.selectedRecyclerView;
                                            selectrecyclerView.setAdapter(selectadapter);
                                            selectrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                                            hotadapter.setItemList(hotDishes);
                                            salatsadapter.setItemList(salats);
                                            dessertadapter.setItemList(desserts);
                                            drinksadapter.setItemList(drinks);
                                            soupsadapter.setItemList(soups);
                                            selectadapter.setItemList(selects);



                                            Log.d("fgfgggv", String.valueOf(soups.size()));





                                        } else {
                                            Log.d("Firestore", "Документ не существует");
                                        }
                                    } else {
                                        Log.d("Firestore", "Ошибка получения документа: " + task.getException());
                                    }
                                }
                            });



                        }
                    });
                    RecyclerView recyclerView = binding2.recyclerviewqqq;
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter.setItemList(resultRestaurantListq);

                    mapView.getMap().addCameraListener(cameraListener);
                }
            }

        });




    }



    private final CameraListener cameraListener = new CameraListener() {


        @Override
        public void onCameraPositionChanged(@NonNull com.yandex.mapkit.map.Map map, @NonNull CameraPosition cameraPosition, @NonNull CameraUpdateReason cameraUpdateReason, boolean b) {
            List<RestaurantCard> resultRestaurantListq = new ArrayList<>();
            Log.d("Я здесь", "qqq");
            for (ResultRestaurant resultRestaurant : restaurantList) {


                VisibleRegion visibleRegion = mapView.getMap().getVisibleRegion();
                Point topLeft = visibleRegion.getTopLeft();
                Point topRight = visibleRegion.getTopRight();
                Point bottomLeft = visibleRegion.getBottomLeft();
                Point bottomRight = visibleRegion.getBottomRight();
                boolean isVisible = resultRestaurant.point.getLatitude() >= bottomLeft.getLatitude()
                        && resultRestaurant.point.getLatitude() <= topLeft.getLatitude()
                        && resultRestaurant.point.getLongitude() >= topLeft.getLongitude()
                        && resultRestaurant.point.getLongitude() <= topRight.getLongitude()
                        && resultRestaurant.point.getLongitude() >= bottomLeft.getLongitude()
                        && resultRestaurant.point.getLongitude() <= bottomRight.getLongitude();
                if (isVisible) {
                    resultRestaurantListq.add(new RestaurantCard(resultRestaurant.name, resultRestaurant.allSum / resultRestaurant.allCount, 16, resultRestaurant.allCount, resultRestaurant.allSum, resultRestaurant.allCount));
                    Log.d("QWERTY", "BJHBJBUGBBG");


                }
            }
            adapter.setItemList(resultRestaurantListq);
        }
    };
}