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
import com.example.whereiscaesar.databinding.SearchMainfragmentResultItemBinding;
import com.example.whereiscaesar.presentation.util.CardClickListener;
import com.example.whereiscaesar.presentation.util.ResultCardClickListener;

import java.util.ArrayList;
import java.util.List;

public class MyResultAdapter extends RecyclerView.Adapter<MyResultAdapter.ViewHolder> {
    private android.content.Context context;
    ResultCardClickListener listener;
    private List<CategoryOrDishModelDomain> itemList = new ArrayList<>();
    public MyResultAdapter(android.content.Context context, ResultCardClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SearchMainfragmentResultItemBinding binding = SearchMainfragmentResultItemBinding.inflate(inflater);
        return new ViewHolder(binding, context, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyResultAdapter.ViewHolder holder, int position) {
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
    public void deleteItem(CategoryOrDishModelDomain categoryOrDishModelDomain){
        int index = itemList.indexOf(categoryOrDishModelDomain);
        if (index != -1){
            itemList.remove(index);
            notifyItemRemoved(index);
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final SearchMainfragmentResultItemBinding binding;
        private android.content.Context context;
        private ResultCardClickListener listener;
        public ViewHolder(SearchMainfragmentResultItemBinding binding, android.content.Context context, ResultCardClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
            this.listener = listener;
        }
        public void bind(CategoryOrDishModelDomain categoryOrDishModelDomain){
            binding.name.setText(categoryOrDishModelDomain.title);
            Glide.with(context)
                    .load(categoryOrDishModelDomain.imageUrl)
                    .error(R.drawable.image_not_supported)
                    .into(binding.imageView);
            binding.deleteBut.setOnClickListener(v -> listener.onButtonClick(categoryOrDishModelDomain));
        }
    }
}