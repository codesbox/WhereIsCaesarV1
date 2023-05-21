package com.example.whereiscaesar.presentation.ui.fragments;

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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.whereiscaesar.R;

import com.example.whereiscaesar.databinding.FragmentMapBinding;
import com.example.whereiscaesar.databinding.RestaurantLayoutBinding;
import com.example.whereiscaesar.presentation.util.Restaurant;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.LatLng;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateReason;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.Rect;
import com.yandex.mapkit.map.VisibleRegion;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment {
    private MapView mapView;
    private static boolean  isMapKitInitialized = false;
    private static boolean qqq = false;

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





    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentMapBinding binding = FragmentMapBinding.bind(view);

        LinearLayout bottomSheetContainer = binding.bottomSheetContainer;
        BottomSheetBehavior<LinearLayout> bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetContainer);



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

                        Point point = new Point(latitude, longitude);
                        Restaurant restaurant = new Restaurant(name, point);
                        restaurantList.add(restaurant);
                    }
                    ImageProvider imageProvider = ImageProvider.fromResource(
                            requireContext(),
                            R.drawable.free);

                    for (Restaurant restaurant : restaurantList){
                        PlacemarkMapObject marker = mapView.getMap().getMapObjects().addPlacemark(restaurant.point, imageProvider);
                        marker.setDraggable(true);
                        marker.setUserData(restaurant.name);
                        marker.addTapListener(new MapObjectTapListener() {
                            @Override
                            public boolean onMapObjectTap(@NonNull MapObject mapObject, @NonNull Point point) {
                                View bottomSheetView = LayoutInflater.from(requireContext()).inflate(R.layout.restaurant_layout, null);
                                bottomSheetContainer.removeAllViews();
                                bottomSheetContainer.addView(bottomSheetView);
                                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                RestaurantLayoutBinding binding1 = RestaurantLayoutBinding.bind(bottomSheetView);

                                binding1.restaurantName.setText((String) marker.getUserData());









                                return false;
                            }
                        });
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