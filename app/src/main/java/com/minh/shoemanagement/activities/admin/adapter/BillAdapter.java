package com.minh.shoemanagement.activities.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minh.shoemanagement.R;
import com.minh.shoemanagement.activities.admin.AdminBill;

public class BillAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    TextView textView;
    public BillAdapter(Context context){
        inflater = LayoutInflater.from(context);
        this.context = context;
    }
    @Override
    public int getCount() {
        return AdminBill.arrayLists.size();
    }

    @Override
    public Object getItem(int position) {
        return AdminBill.arrayLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(AdminBill.arrayLists.get(position).get(0));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewStats;
        if(convertView == null){
            viewStats = View.inflate(parent.getContext(), R.layout.item_bill_admin, null);
        }else{
            viewStats = convertView;
        }
        textView = viewStats.findViewById(R.id.textViewAdminStatsCol1);
        textView.setText(AdminBill.arrayLists.get(position).get(0));
        textView = viewStats.findViewById(R.id.textViewAdminStatsCol2);
        textView.setText(AdminBill.arrayLists.get(position).get(1));
        textView = viewStats.findViewById(R.id.textViewAdminStatsCol3);
        textView.setText(AdminBill.arrayLists.get(position).get(2));

        return viewStats;
    }
}
