package com.minh.shoemanagement.activities.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.minh.shoemanagement.R;
import com.minh.shoemanagement.activities.user.ShoesDetailActivity;
import com.minh.shoemanagement.entities.Shoe;

import java.util.List;

public class ShoesAdapter extends RecyclerView.Adapter<ShoesAdapter.ShoeViewHolder>{

    private Context context;
    private List<Shoe> shoeList;

    public ShoesAdapter(Context context, List<Shoe> shoeList) {
        this.context = context;
        this.shoeList = shoeList;
    }

    @NonNull
    @Override
    public ShoeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shoe, parent, false);

        return new ShoeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoeViewHolder holder, int position) {
        Shoe shoe = shoeList.get(position);

        Glide.with(context).load(shoe.getImage()).into(holder.imageView);
        holder.tvName.setText(shoe.getName());
        holder.tvInfo.setText(shoe.getInformation());
        holder.tvPrice.setText(String.valueOf(shoe.getPrice()));
        holder.tvCategoryName.setText(shoe.getCategory().getName());

        holder.imageView.setOnClickListener(v -> {

            // Chuyen den trang Shoes Detail
            Intent intent = new Intent(context, ShoesDetailActivity.class);
            intent.putExtra("shoesId", shoe.getId());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return shoeList.size();
    }

    class ShoeViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView tvName, tvInfo, tvPrice, tvCategoryName;


        public ShoeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewShoe);
            tvName = itemView.findViewById(R.id.textViewShoeName);
            tvInfo = itemView.findViewById(R.id.textViewShoeInfo);
            tvPrice = itemView.findViewById(R.id.textViewShoePrice);
            tvCategoryName = itemView.findViewById(R.id.textViewShoeCategory);
        }
    }
}
