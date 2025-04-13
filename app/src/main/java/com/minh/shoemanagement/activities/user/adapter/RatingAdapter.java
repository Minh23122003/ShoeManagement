package com.minh.shoemanagement.activities.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minh.shoemanagement.R;
import com.minh.shoemanagement.entities.Rating;

import java.util.ArrayList;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.RatingViewHolder>{
    private Context context;
    private ArrayList<Rating> ratingList;

    public RatingAdapter(Context context, ArrayList<Rating> ratingList) {
        this.context = context;
        this.ratingList = ratingList;
    }

    @NonNull
    @Override
    public RatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rating, parent, false);
        return new RatingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingViewHolder holder, int position) {
        Rating rating = ratingList.get(position);

        holder.tvUserName.setText(rating.getUser().getName());
        holder.tvCreatedDate.setText(rating.getCreatedDate());
        holder.tvStar.setText("⭐ " + rating.getStar());
        holder.tvContent.setText(rating.getContent());
    }

    @Override
    public int getItemCount() {
        return ratingList.size();
    }


    class RatingViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUserName, tvCreatedDate, tvStar, tvContent;


        public RatingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.textUserName);
            tvCreatedDate = itemView.findViewById(R.id.textCommentDate);
            tvStar = itemView.findViewById(R.id.textRatingStar);
            tvContent = itemView.findViewById(R.id.textCommentContent);
        }
    }
}
