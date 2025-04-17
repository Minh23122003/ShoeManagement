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
import com.minh.shoemanagement.activities.admin.AdminUser;
import com.minh.shoemanagement.entities.Category;
import com.minh.shoemanagement.entities.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private ArrayList<User> users;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(User user);
    }

    public UserAdapter(ArrayList<User> users, OnItemClickListener listener) {
        this.users = users;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_admin, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(users.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewId, textViewUsername, textViewCreatedDate;

        public MyViewHolder(View itemView){
            super(itemView);
            textViewId = itemView.findViewById(R.id.textViewUserId);
            textViewUsername = itemView.findViewById(R.id.textViewUserUsername);
            textViewCreatedDate = itemView.findViewById(R.id.textViewUserCreatedDate);
        }

        public void bind(final User user, final UserAdapter.OnItemClickListener listener){
            textViewId.setText(String.valueOf(user.getId()));
            textViewUsername.setText(user.getUsername());
            textViewCreatedDate.setText(user.getCreatedDate());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(user);
                }
            });
        }
    }
}
