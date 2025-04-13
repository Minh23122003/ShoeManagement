package com.minh.shoemanagement.activities.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minh.shoemanagement.R;
import com.minh.shoemanagement.activities.admin.AdminUser;

public class UserAdapter extends BaseAdapter {
    LayoutInflater inflater;
    TextView textView;
    Context context;

    public UserAdapter(Context context){
        inflater = LayoutInflater.from(context);
        this.context = context;
    }
    @Override
    public int getCount() {
        return AdminUser.users.size();
    }

    @Override
    public Object getItem(int position) {
        return AdminUser.users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return AdminUser.users.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewUser;
        if(convertView == null){
            viewUser = View.inflate(parent.getContext(), R.layout.item_user_admin, null);
        }else
            viewUser = convertView;
        textView = (TextView) viewUser.findViewById(R.id.textViewUserId);
        textView.setText(String.valueOf(AdminUser.users.get(position).getId()));
        textView = (TextView) viewUser.findViewById(R.id.textViewUserUsername);
        textView.setText(AdminUser.users.get(position).getUsername());
        textView = (TextView) viewUser.findViewById(R.id.textViewUserCreatedDate);
        textView.setText(AdminUser.users.get(position).getCreatedDate());

        return viewUser;
    }
}
