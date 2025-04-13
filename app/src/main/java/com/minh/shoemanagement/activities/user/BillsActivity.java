package com.minh.shoemanagement.activities.user;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minh.shoemanagement.R;
import com.minh.shoemanagement.activities.LoginActivity;
import com.minh.shoemanagement.activities.user.adapter.BillAdapter;
import com.minh.shoemanagement.entities.Bill;
import com.minh.shoemanagement.entities.Shoe;
import com.minh.shoemanagement.utils.DBHelper;
import com.minh.shoemanagement.utils.MyDatabase;

import java.util.ArrayList;

public class BillsActivity extends AppCompatActivity {

    private MyDatabase database;

    private ArrayList<Bill> billList;

    private RecyclerView recyclerViewBill;

    public void loadBillListByUser() {
        if (billList == null) {
            billList = new ArrayList<>();
        } else {
            billList.removeAll(billList);
        }


        //Load dữ liệu thật từ db
        Cursor billCursor = database.getBillByUserId(UserHome.currentUser.getId());

        if (billCursor != null && billCursor.getCount() > 0) {
            while (billCursor.moveToNext()) {
                int billId = billCursor.getInt(billCursor.getColumnIndexOrThrow(DBHelper.BILL_ID));

                //get shoe id sau do chuyen thanh Shoe
                long billQuantity = billCursor.getLong(billCursor.getColumnIndexOrThrow(DBHelper.BILL_QUANTITY));
                long billSize = billCursor.getLong(billCursor.getColumnIndexOrThrow(DBHelper.BILL_SIZE));
                String billCreatedDate = billCursor.getString(billCursor.getColumnIndexOrThrow(DBHelper.BILL_CREATED_DATE));
                String billPaymentDate = billCursor.getString(billCursor.getColumnIndexOrThrow(DBHelper.BILL_PAYMENT_DATE));

                long billShoeId = billCursor.getLong(billCursor.getColumnIndexOrThrow(DBHelper.BILL_SHOE_ID));
                Cursor shoeCursor = database.getShoeById(billShoeId);
                if(shoeCursor!= null && shoeCursor.moveToFirst()){
                    String shoeName = shoeCursor.getString(shoeCursor.getColumnIndexOrThrow(DBHelper.SHOE_NAME));

                    Shoe shoe = new Shoe();
                    shoe.setName(shoeName);

                    Bill bill = new Bill(billId, UserHome.currentUser, shoe, billQuantity, billSize, billCreatedDate, billPaymentDate);

                    billList.add(bill);
                }

            }
        }
        //end Load dữ liệu thật từ db


        //Load dữ liệu giả
//        Shoe shoe = new Shoe();
//        shoe.setName("Nikeeeee");
//        shoe.setPrice(250000);
//
//        Bill bill1 = new Bill(1, UserHome.currentUser, shoe, 2, 5, "11/4/2025", "12/4/2025");
//        Bill bill2 = new Bill(2, UserHome.currentUser, shoe, 2, 5, "11/4/2025", "12/4/2025");
//        Bill bill3 = new Bill(3, UserHome.currentUser, shoe, 2, 5, "11/4/2025", "12/4/2025");
//
//        billList.add(bill1);
//        billList.add(bill2);
//        billList.add(bill3);
        //end Load dữ liệu giả


        recyclerViewBill = findViewById(R.id.recyclerBill);
        recyclerViewBill.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewBill.setAdapter(new BillAdapter(this, billList));

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bill_list);

        database = new MyDatabase(this);

        loadBillListByUser();

        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        Button buttonCategory = findViewById(R.id.buttonByCategory);
        buttonCategory.setOnClickListener(v -> {
            startActivity(new Intent(this, CategoryActivity.class));
            finish();
        });

        //button cart
        Button buttonCart = findViewById(R.id.buttonCart);
        buttonCart.setOnClickListener(v -> {
            startActivity(new Intent(this, CartActivity.class));
            finish();
        });
    }
}
