package com.example.whereiscaesar.presentation.ui.recycler;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.whereiscaesar.R;
import com.example.whereiscaesar.databinding.DishCardBinding;
import com.example.whereiscaesar.databinding.MapFragmentItemBinding;
import com.example.whereiscaesar.presentation.util.Feedback;
import com.example.whereiscaesar.presentation.util.MapClicCardListener;
import com.example.whereiscaesar.presentation.util.MapDishCard;

import java.util.ArrayList;
import java.util.List;

public class MapFeedbackAdapter extends RecyclerView.Adapter<MapFeedbackAdapter.ViewHolder> {
    private android.content.Context context;
    private List<Feedback> itemList = new ArrayList<>();
    public MapFeedbackAdapter(android.content.Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MapFeedbackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        DishCardBinding binding = DishCardBinding.inflate(inflater);
        return new ViewHolder(binding, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MapFeedbackAdapter.ViewHolder holder, int position) {
        holder.bind(itemList.get(position));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setItemList(List<Feedback>  feedbackList){
        this.itemList = feedbackList;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final DishCardBinding binding;
        private android.content.Context context;
        public ViewHolder(DishCardBinding binding, android.content.Context context) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
        }
        public void bind(Feedback feedback){
            binding.name.setText(feedback.userName);
            binding.estimation.setText(feedback.estimation.toString());
            binding.level.setText(String.format("Критик %s уровня", feedback.level.toString()));
            binding.feedback.setText(feedback.feedback);

        }
    }
}
