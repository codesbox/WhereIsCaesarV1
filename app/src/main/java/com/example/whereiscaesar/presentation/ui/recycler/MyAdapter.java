package com.example.whereiscaesar.presentation.ui.recycler;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.domain.models.CategoryOrDishModelDomain;
import com.example.whereiscaesar.R;
import com.example.whereiscaesar.databinding.SearchMainfargmentItemBinding;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private android.content.Context context;
    private List<CategoryOrDishModelDomain> itemList = new ArrayList<>();
    public MyAdapter(android.content.Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SearchMainfargmentItemBinding binding = SearchMainfargmentItemBinding.inflate(inflater);
        return new ViewHolder(binding, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        holder.bind(itemList.get(position));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setItemList(List<CategoryOrDishModelDomain>  categoryOrDishModelDomainList){
        this.itemList = categoryOrDishModelDomainList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final SearchMainfargmentItemBinding binding;
        private android.content.Context context;
        public ViewHolder(SearchMainfargmentItemBinding binding, android.content.Context context) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
        }
        public void bind(CategoryOrDishModelDomain categoryOrDishModelDomain){
            binding.name.setText(categoryOrDishModelDomain.title);
            Glide.with(context)
                    .load(categoryOrDishModelDomain.imageUrl)
                    .error(R.drawable.image_not_supported)
                    .into(binding.imageView);
        }
    }
}
