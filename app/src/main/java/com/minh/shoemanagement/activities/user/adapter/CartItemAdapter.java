package com.minh.shoemanagement.activities.user.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.minh.shoemanagement.R;
import com.minh.shoemanagement.activities.user.CartActivity;
import com.minh.shoemanagement.entities.Bill;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>{

    private Context context;
    private List<Bill> cartItemList;

    public CartItemAdapter(Context context, List<Bill> cartItemList) {
        this.context = context;
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bill_in_cart, parent, false);
        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        Bill bill = cartItemList.get(position);

        holder.tvShoeName.setText("Tên giày: " + bill.getShoe().getName());
        holder.tvQuantitySize.setText("Số lượng: " + bill.getQuantity() + " | Size: "  + bill.getSize());
        holder.tvAmount.setText("Số tiền: " + bill.getShoe().getPrice() * bill.getQuantity() + " VND");
        holder.tvCreatedDate.setText("Ngày thêm vào giỏ hàng: " + bill.getCreatedDate());

        Glide.with(context).load(bill.getShoe().getImage()).into(holder.imageViewShoe);

        holder.buttonPay.setOnClickListener(v -> {
            showPaymentDialog(bill, position);

        });

        holder.buttonCancel.setOnClickListener(v -> {
            showDeleteDialog(bill, position);
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    class CartItemViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewShoe;
        TextView tvShoeName, tvQuantitySize, tvCreatedDate, tvAmount;

        Button buttonPay, buttonCancel;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewShoe = itemView.findViewById(R.id.imageViewShoe);
            tvShoeName = itemView.findViewById(R.id.textViewShoeName);
            tvQuantitySize = itemView.findViewById(R.id.textViewQuantitySize);
            tvCreatedDate = itemView.findViewById(R.id.textViewCreatedDate);
            tvAmount = itemView.findViewById(R.id.textViewAmount);

            buttonPay = itemView.findViewById(R.id.buttonPay);
            buttonCancel = itemView.findViewById(R.id.buttonCancel);
        }
    }


    public  void showDeleteDialog (Bill bill, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_delete_cart_item, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);
        Button buttonDelete = dialogView.findViewById(R.id.buttonDelete);

        buttonCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        buttonDelete.setOnClickListener(v -> {
            //delete Bill
            if(deleteItem(bill) != -1){
                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

                cartItemList.remove(position);

                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartItemList.size());
            }else{
                Toast.makeText(context, "Xảy ra lỗi khi xóa", Toast.LENGTH_SHORT).show();
            }

        });


        dialog.show();
    }
    public  void showPaymentDialog (Bill bill, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_payment_confirm, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        Button buttonConfirm = dialogView.findViewById(R.id.buttonConfirm);
        Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);

        buttonConfirm.setOnClickListener(v -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                bill.setCreatedDate(formatter.format(LocalDateTime.now()));
                bill.setPaymentDate(formatter.format(LocalDateTime.now()));
            }

            if(confirmPayment(bill)!=-1){
                //show toast
                Toast.makeText(context, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                //notifi

                cartItemList.remove(position);

                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartItemList.size());
            }else{
                Toast.makeText(context, "Xảy ra lỗi khi thanh toán", Toast.LENGTH_SHORT).show();
            }
        });

        buttonCancel.setOnClickListener(v -> {

            dialog.dismiss();
        });

        dialog.show();
    }

    //Khi khách hàng xác nhận thanh toán
    public long confirmPayment (Bill bill){
        if(CartActivity.database == null){
            return -1;
        }else{
            return CartActivity.database.userPaymentProcess(bill);
        }
    }

    //Khi khách hàng xác nhận xóa item khỏi cart
    public long deleteItem (Bill bill){
        if (CartActivity.database == null)return -1;
        else{
            return CartActivity.database.userDeleteCartItem(bill);
        }
    }
}
