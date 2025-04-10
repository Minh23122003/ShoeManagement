package com.minh.shoemanagement.activities.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minh.shoemanagement.R;
import com.minh.shoemanagement.activities.admin.AdminShoe;
import com.minh.shoemanagement.entities.Shoe;

public class ShoeAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    TextView textView;

    public ShoeAdapter(Context context){
        inflater = LayoutInflater.from(context);
        this.context = context;
    }
    @Override
    public int getCount() {
        return AdminShoe.shoes.size();
    }

    @Override
    public Object getItem(int position) {
        return AdminShoe.shoes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return AdminShoe.shoes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewShoe;
        if(convertView == null){
            viewShoe = View.inflate(parent.getContext(), R.layout.item_shoe_admin, null);
        }else{
            viewShoe = convertView;
        }

        textView = viewShoe.findViewById(R.id.textViewAdminShoeId);
        textView.setText(String.valueOf(AdminShoe.shoes.get(position).getId()));
        textView = viewShoe.findViewById(R.id.textViewAdminShoeName);
        textView.setText(AdminShoe.shoes.get(position).getName());
        return viewShoe;
    }
}
