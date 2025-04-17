package com.minh.shoemanagement.activities.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minh.shoemanagement.R;
import com.minh.shoemanagement.activities.admin.AdminCategory;
import com.minh.shoemanagement.entities.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private ArrayList<Category> categories;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(Category category);
    }

    public CategoryAdapter(ArrayList<Category> categories, OnItemClickListener listener){
        this.categories = categories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_admin, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(categories.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewId, textViewName;

        public MyViewHolder(View itemView){
            super(itemView);
            textViewId = itemView.findViewById(R.id.textViewCategoryId);
            textViewName = itemView.findViewById(R.id.textViewCategoryName);
        }

        public void bind(final Category category, final OnItemClickListener listener){
            textViewId.setText(String.valueOf(category.getId()));
            textViewName.setText(category.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(category);
                }
            });
        }
    }
}
