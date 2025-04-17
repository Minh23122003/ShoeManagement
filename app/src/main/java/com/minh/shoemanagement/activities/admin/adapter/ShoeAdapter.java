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
import com.minh.shoemanagement.activities.admin.AdminShoe;
import com.minh.shoemanagement.entities.Category;
import com.minh.shoemanagement.entities.Shoe;

import java.util.ArrayList;

public class ShoeAdapter extends RecyclerView.Adapter<ShoeAdapter.MyViewHolder> {
    private ArrayList<Shoe> shoes;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(Shoe shoe);
    }

    public ShoeAdapter(ArrayList<Shoe> shoes, OnItemClickListener listener) {
        this.shoes = shoes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shoe_admin, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(shoes.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return shoes.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewId, textViewName;

        public MyViewHolder(View itemView){
            super(itemView);
            textViewId = itemView.findViewById(R.id.textViewAdminShoeId);
            textViewName = itemView.findViewById(R.id.textViewAdminShoeName);
        }

        public void bind(final Shoe shoe, final OnItemClickListener listener){
            textViewId.setText(String.valueOf(shoe.getId()));
            textViewName.setText(shoe.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(shoe);
                }
            });
        }
    }
}
