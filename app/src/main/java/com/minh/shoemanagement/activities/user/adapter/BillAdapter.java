package com.minh.shoemanagement.activities.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minh.shoemanagement.R;
import com.minh.shoemanagement.entities.Bill;

import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder>{

    private Context context;
    private List<Bill> billList;

    public BillAdapter(Context context, List<Bill> billList) {
        this.context = context;
        this.billList = billList;
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bill, parent, false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        Bill bill = billList.get(position);

        holder.tvBillId.setText("#"+bill.getId());
        holder.tvShoeName.setText("Tên giày: "+ bill.getShoe().getName());
        holder.tvQuantitySize.setText("Số lượng: " + bill.getQuantity() + " | Size: "  + bill.getSize());
        holder.tvCreatedDate.setText("Ngày đặt: " + bill.getCreatedDate());
        holder.tvPaymentDate.setText("Ngày thanh toán: "+bill.getPaymentDate());
        holder.tvAmount.setText("Số tiền: " + bill.getShoe().getPrice() * bill.getQuantity() + " VND");
    }

    @Override
    public int getItemCount() {
        return billList.size();
    }

    class BillViewHolder extends RecyclerView.ViewHolder {
        private TextView tvShoeName, tvQuantitySize, tvCreatedDate, tvPaymentDate, tvBillId, tvAmount;


        public BillViewHolder(@NonNull View itemView) {
            super(itemView);

            tvShoeName = itemView.findViewById(R.id.textViewShoeName);
            tvQuantitySize = itemView.findViewById(R.id.textViewQuantitySize);
            tvCreatedDate = itemView.findViewById(R.id.textViewCreatedDate);
            tvPaymentDate = itemView.findViewById(R.id.textViewPaymentDate);
            tvBillId = itemView.findViewById(R.id.textViewBillId);
            tvAmount = itemView.findViewById(R.id.textViewAmount);
        }
    }
}
