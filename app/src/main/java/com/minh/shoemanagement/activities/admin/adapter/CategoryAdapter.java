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
import com.minh.shoemanagement.activities.admin.AdminCategory;
import com.minh.shoemanagement.entities.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends BaseAdapter {
        LayoutInflater inflater;
    TextView textView;
    Context context;

    public CategoryAdapter(Context context){
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return AdminCategory.categories.size();
    }

    @Override
    public Object getItem(int position) {
        return AdminCategory.categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return AdminCategory.categories.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewCategory;
        if(convertView == null)
            viewCategory = View.inflate(parent.getContext(), R.layout.item_category_admin,  null);
        else
            viewCategory = convertView;
        textView = (TextView) viewCategory.findViewById(R.id.textViewCategoryId);
        textView.setText(String.valueOf(AdminCategory.categories.get(position).getId()));
        textView = (TextView) viewCategory.findViewById(R.id.textViewCategoryName);
        textView.setText(AdminCategory.categories.get(position).getName());
        return viewCategory;
    }
}
