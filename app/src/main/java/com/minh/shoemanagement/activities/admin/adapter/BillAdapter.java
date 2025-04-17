package com.minh.shoemanagement.activities.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minh.shoemanagement.R;
import com.minh.shoemanagement.activities.admin.AdminBill;
import com.minh.shoemanagement.entities.Category;

import java.util.ArrayList;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.MyViewHolder> {
    private ArrayList<ArrayList<String>> arrayLists;

    public BillAdapter(ArrayList<ArrayList<String>> arrayLists) {
        this.arrayLists = arrayLists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill_admin, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView1.setText(arrayLists.get(position).get(0));
        holder.textView2.setText(arrayLists.get(position).get(1));
        holder.textView3.setText(arrayLists.get(position).get(2));
    }

    @Override
    public int getItemCount() {
        return arrayLists.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textView1, textView2, textView3;

        public MyViewHolder(View itemView){
            super(itemView);
            textView1 = itemView.findViewById(R.id.textViewAdminStatsCol1);
            textView2 = itemView.findViewById(R.id.textViewAdminStatsCol2);
            textView3 = itemView.findViewById(R.id.textViewAdminStatsCol3);
        }
    }
}
