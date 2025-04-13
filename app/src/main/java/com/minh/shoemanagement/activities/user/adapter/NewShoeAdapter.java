package com.minh.shoemanagement.activities.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.minh.shoemanagement.R;
import com.minh.shoemanagement.activities.user.ShoesDetailActivity;
import com.minh.shoemanagement.entities.Shoe;

import java.util.List;

public class NewShoeAdapter extends RecyclerView.Adapter<NewShoeAdapter.ShoeViewHolder> {

    private List<Shoe> shoeList;
    private Context context;

    public NewShoeAdapter(List<Shoe> shoeList, Context context) {
        this.shoeList = shoeList;
        this.context = context;
    }

    @NonNull
    @Override
    public ShoeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shoe, parent, false);

        return new ShoeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoeViewHolder holder, int position) {
        Shoe shoe = shoeList.get(position);

        Glide.with(context).load(shoe.getImage()).into(holder.imageView);
        holder.tvName.setText(shoe.getName());
        holder.tvInformation.setText(shoe.getInformation());
        holder.tvPrice.setText(String.valueOf(shoe.getPrice()));
        holder.tvCategory.setText(shoe.getCategory().getName());

        holder.imageView.setOnClickListener(v -> {

            // Chuyen den trang Shoes Detail
            Intent intent = new Intent(context, ShoesDetailActivity.class);
            intent.putExtra("shoesId", shoe.getId());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if(shoeList!=null)return shoeList.size();
        return 0;
    }

    class ShoeViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        private TextView tvName, tvInformation, tvPrice, tvCategory;

        public ShoeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewShoe);
            tvName = itemView.findViewById(R.id.textViewShoeName);
            tvInformation = itemView.findViewById(R.id.textViewShoeInfo);
            tvPrice = itemView.findViewById(R.id.textViewShoePrice);
            tvCategory = itemView.findViewById(R.id.textViewShoeCategory);
        }
    }
}
