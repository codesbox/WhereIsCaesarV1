package com.example.whereiscaesar.presentation.ui.recycler;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whereiscaesar.databinding.MyFeedbackCardBinding;
import com.example.whereiscaesar.databinding.RestaurantCardBinding;
import com.example.whereiscaesar.presentation.util.MapClicCardListener;
import com.example.whereiscaesar.presentation.util.MyFeedback;
import com.example.whereiscaesar.presentation.util.Restaurant;
import com.example.whereiscaesar.presentation.util.RestaurantCard;
import com.example.whereiscaesar.presentation.util.RestaurantResultClickListener;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsRecyclerView extends RecyclerView.Adapter<RestaurantsRecyclerView.ViewHolder> {
    private android.content.Context context;
    private List<RestaurantCard> itemList = new ArrayList<>();
    RestaurantResultClickListener listener;
    public RestaurantsRecyclerView(android.content.Context context, RestaurantResultClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RestaurantsRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RestaurantCardBinding binding = RestaurantCardBinding.inflate(inflater);
        return new ViewHolder(binding, context, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantsRecyclerView.ViewHolder holder, int position) {
        holder.bind(itemList.get(position));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setItemList(List<RestaurantCard>  restaurantCardList){
        this.itemList = restaurantCardList;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final RestaurantCardBinding binding;
        private android.content.Context context;
        private RestaurantResultClickListener listener;
        public ViewHolder(RestaurantCardBinding binding, android.content.Context context, RestaurantResultClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
            this.listener = listener;
        }
        public void bind(RestaurantCard restaurantCard){
            Log.d("QWERTYU", restaurantCard.restaurantName);
            binding.name.setText(restaurantCard.restaurantName);
            binding.restEstimation.setText(restaurantCard.resEstimation.toString());
            binding.counter.setText(String.format("Оценок: %s", restaurantCard.counter.toString()));
            binding.cardView.setOnClickListener(v -> {listener.onCardClick(restaurantCard);});

        }
    }
}