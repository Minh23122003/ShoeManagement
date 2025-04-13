package com.minh.shoemanagement.activities.user;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minh.shoemanagement.R;
import com.minh.shoemanagement.activities.LoginActivity;
import com.minh.shoemanagement.activities.user.adapter.CartItemAdapter;
import com.minh.shoemanagement.entities.Bill;
import com.minh.shoemanagement.entities.Category;
import com.minh.shoemanagement.entities.Shoe;
import com.minh.shoemanagement.utils.DBHelper;
import com.minh.shoemanagement.utils.MyDatabase;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    public static MyDatabase database;

    private ArrayList<Bill> cartItemList;

    private RecyclerView rcvCartItem;

    public void loadCartItemList () {
        if(cartItemList == null ){
            cartItemList = new ArrayList<>();
        }else{
            cartItemList.removeAll(cartItemList);
        }

        Cursor cursorItem = database.getCartItem(UserHome.currentUser.getId());
        if(cursorItem != null && cursorItem.getCount() > 0){
            while (cursorItem.moveToNext()){
                Bill bill = new Bill();

                int id = cursorItem.getInt(cursorItem.getColumnIndexOrThrow(DBHelper.BILL_ID));
                long quantity = cursorItem.getLong(cursorItem.getColumnIndexOrThrow(DBHelper.BILL_QUANTITY));
                long size = cursorItem.getLong(cursorItem.getColumnIndexOrThrow(DBHelper.BILL_SIZE));
                String createdDate = cursorItem.getString(cursorItem.getColumnIndexOrThrow(DBHelper.BILL_CREATED_DATE));
                String paymentDate = cursorItem.getString(cursorItem.getColumnIndexOrThrow(DBHelper.BILL_PAYMENT_DATE));

                long shoeId = cursorItem.getLong(cursorItem.getColumnIndexOrThrow(DBHelper.BILL_SHOE_ID));
                Shoe shoe = new Shoe();
                Cursor cursorShoe = database.getShoeById(shoeId);
                if(cursorShoe!=null && cursorShoe.moveToFirst()){
                    // ten gia hinh
                    String name = cursorShoe.getString(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_NAME));
                    long price = cursorShoe.getLong(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_PRICE));
                    String image = cursorShoe.getString(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_IMAGE));

                    shoe.setName(name);
                    shoe.setPrice(price);
                    shoe.setImage(image);
                    shoe.setId(shoeId);
                }

                bill.setId(id);
                bill.setQuantity(quantity);
                bill.setSize(size);
                bill.setShoe(shoe);
                bill.setCreatedDate(createdDate);
                bill.setPaymentDate(paymentDate);
                bill.setUser(UserHome.currentUser);

                cartItemList.add(bill);
            }
        }

//        Category category = new Category(1, "Sport");
//
//        Shoe shoe = new Shoe(1, "Adidas", "adidas Sport", 200, category, 300000, "https://giaybongro.vn/upload/images/1011459600/86/4435_1735813672_thumb2.jpg", "adidas sport");
//
//        Bill bill1 = new Bill(1, UserHome.currentUser, shoe, 2, 2, "9/11/2025", "pending");
//        Bill bill2 = new Bill(2, UserHome.currentUser, shoe, 1, 2, "9/4/2025", "pending");
//        Bill bill3 = new Bill(3, UserHome.currentUser, shoe, 4, 2, "13/11/2025", "pending");
//
//        cartItemList.add(bill1);
//        cartItemList.add(bill2);
//        cartItemList.add(bill3);

        rcvCartItem.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcvCartItem.setAdapter(new CartItemAdapter(this, cartItemList));
    }





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cart);

        database = new MyDatabase(this);
        rcvCartItem = findViewById(R.id.recyclerCartItem);

        loadCartItemList();

        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(v -> {
            UserHome.currentUser = null;
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        Button buttonCategory = findViewById(R.id.buttonByCategory);
        buttonCategory.setOnClickListener(v -> {
            startActivity(new Intent(this, CategoryActivity.class));
            finish();
        });

        Button buttonBills = findViewById(R.id.buttonHistory);
        buttonBills.setOnClickListener(v -> {
            Intent intent = new Intent(this, BillsActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
