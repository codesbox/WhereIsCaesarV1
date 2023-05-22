package com.example.whereiscaesar.presentation.ui.recycler;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.domain.models.CategoryOrDishModelDomain;
import com.example.whereiscaesar.R;
import com.example.whereiscaesar.databinding.MapFragmentItemBinding;
import com.example.whereiscaesar.databinding.RestaurantLayoutBinding;
import com.example.whereiscaesar.databinding.SearchMainfargmentItemBinding;
import com.example.whereiscaesar.presentation.util.CardClickListener;
import com.example.whereiscaesar.presentation.util.MapClicCardListener;
import com.example.whereiscaesar.presentation.util.MapDishCard;

import java.util.ArrayList;
import java.util.List;

public class MapAdapter extends RecyclerView.Adapter<MapAdapter.ViewHolder> {
    private android.content.Context context;
    MapClicCardListener listener;
    private List<MapDishCard> itemList = new ArrayList<>();
    public MapAdapter(android.content.Context context, MapClicCardListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MapAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MapFragmentItemBinding binding = MapFragmentItemBinding.inflate(inflater);
        return new ViewHolder(binding, context, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MapAdapter.ViewHolder holder, int position) {
        holder.bind(itemList.get(position));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setItemList(List<MapDishCard>  mapDishCardList){
        this.itemList = mapDishCardList;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final MapFragmentItemBinding binding;
        private android.content.Context context;
        private MapClicCardListener listener;
        public ViewHolder(MapFragmentItemBinding binding, android.content.Context context, MapClicCardListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
            this.listener = listener;
        }
        public void bind(MapDishCard mapDishCard){
            binding.name.setText(mapDishCard.dishName);
            binding.estimation.setText(mapDishCard.estimation.toString());
            binding.counter.setText(String.format("Отзывов: %s", mapDishCard.counter.toString()));
            Glide.with(context)
                    .load(mapDishCard.imageUrl)
                    .error(R.drawable.image_not_supported)
                    .into(binding.imageView);
            binding.cardView.setOnClickListener(v -> {listener.onCardClick(mapDishCard);});
        }
    }
}