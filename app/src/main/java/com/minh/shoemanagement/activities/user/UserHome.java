package com.minh.shoemanagement.activities.user;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minh.shoemanagement.R;
import com.minh.shoemanagement.activities.LoginActivity;
import com.minh.shoemanagement.activities.user.adapter.BestSellerShoeAdapter;
import com.minh.shoemanagement.activities.user.adapter.NewShoeAdapter;
import com.minh.shoemanagement.entities.Category;
import com.minh.shoemanagement.entities.Shoe;
import com.minh.shoemanagement.entities.User;
import com.minh.shoemanagement.utils.DBHelper;
import com.minh.shoemanagement.utils.MyDatabase;

import java.util.ArrayList;

public class UserHome extends AppCompatActivity {

    public static RecyclerView recycler;

    public static ArrayList<Shoe> bestSellerShoesList;

    public static ArrayList<Shoe> newShoesList;

    public static MyDatabase database;

    public static User currentUser;


    public User getCurrentUser(String username) {

        Cursor userCursor = database.searchUserByUsername(username);
        userCursor.moveToFirst();

        int id = userCursor.getInt(userCursor.getColumnIndexOrThrow(DBHelper.USER_ID));
        String username1 = userCursor.getString(userCursor.getColumnIndexOrThrow(DBHelper.USER_NAME));
        String password = userCursor.getString(userCursor.getColumnIndexOrThrow(DBHelper.USER_PASSWORD));
        String name = userCursor.getString(userCursor.getColumnIndexOrThrow(DBHelper.USER_NAME));
        String address = userCursor.getString(userCursor.getColumnIndexOrThrow(DBHelper.USER_ADDRESS));
        String phone = userCursor.getString(userCursor.getColumnIndexOrThrow(DBHelper.USER_PHONE));
        long is_admin = userCursor.getLong(userCursor.getColumnIndexOrThrow(DBHelper.USER_IS_ADMIN));
        String createdDate = userCursor.getString(userCursor.getColumnIndexOrThrow(DBHelper.USER_CREATED_DATE));

        return new User(id, username1, password, name, address, phone, is_admin, createdDate);
    }

    public void loadBestSellerShoes() {
        if (bestSellerShoesList == null) {
            bestSellerShoesList = new ArrayList<>();
        } else {
            bestSellerShoesList.removeAll(bestSellerShoesList);
        }

        Cursor cursorBestSeller = database.getBestSellerShoe();
        if (cursorBestSeller != null && cursorBestSeller.getCount() > 0) {
            while (cursorBestSeller.moveToNext()) {
                long shoeId = cursorBestSeller.getLong(cursorBestSeller.getColumnIndexOrThrow(DBHelper.BILL_SHOE_ID));
                Cursor cursorShoe = database.getShoeById(shoeId);
                if (cursorShoe != null && cursorShoe.moveToFirst()) {
                    int shoesId = cursorShoe.getInt(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_ID));
                    String shoesName = cursorShoe.getString(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_NAME));
                    String shoesInfo = cursorShoe.getString(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_INFORMATION));
                    long shoesPrice = cursorShoe.getLong(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_PRICE));
                    long shoesQuantity = cursorShoe.getLong(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_QUANTITY));
                    String shoesNote = cursorShoe.getString(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_NOTE));
                    String shoesImage = cursorShoe.getString(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_IMAGE));

                    long cateId = cursorShoe.getLong(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_CATEGORY_ID));
                    Cursor cursorCate = database.getCategoryById(cateId);
                    if (cursorCate != null && cursorCate.moveToFirst()) {
                        String cateName = cursorCate.getString(cursorCate.getColumnIndexOrThrow(DBHelper.CATEGORY_NAME));

                        Category category = new Category(cateId, cateName);

                        Shoe shoe = new Shoe(shoesId, shoesName, shoesInfo, shoesQuantity, category, shoesPrice, shoesImage, shoesNote);
                        bestSellerShoesList.add(shoe);
                    }

                }

            }
        }else{
            Toast.makeText(this, "Không lấy được thông tin giày bán chạy nhất", Toast.LENGTH_SHORT).show();
        }

        //du lieu gia
        Category category = new Category(1, "Sport");

        Shoe shoe1 = new Shoe(1, "Nike 1", "Info: Nike sport", 100, category, 500000, "https://giaybongro.vn/upload/images/1011459600/86/4435_1735813672_thumb2.jpg", "Sport shoes");
        Shoe shoe2 = new Shoe(2, "Nike 2", "Info: Nike sport", 100, category, 500000, "https://giaybongro.vn/upload/images/1008781200/60/5976_1734604728_thumb2.jpg", "Sport shoes");
        Shoe shoe3 = new Shoe(3, "Nike 3", "Info: Nike sport", 100, category, 500000, "https://giaybongro.vn/upload/images/1008781200/60/437_1734604908_thumb2.jpg", "Sport shoes");
        Shoe shoe4 = new Shoe(4, "Nike 4", "Info: Nike sport", 100, category, 500000, "https://giaybongro.vn/upload/images/1008781200/60/9625_1734604838_thumb2.jpg", "Sport shoes");
        Shoe shoe5 = new Shoe(5, "Nike 5", "Info: Nike sport", 100, category, 500000, "https://giaybongro.vn/upload/images/1008781200/43/9306_1727173532_thumb2.jpg", "Sport shoes");

        bestSellerShoesList.add(shoe1);
        bestSellerShoesList.add(shoe2);
        bestSellerShoesList.add(shoe3);
        bestSellerShoesList.add(shoe4);
        bestSellerShoesList.add(shoe5);


        if (bestSellerShoesList != null) {
            recycler = findViewById(R.id.recyclerBestSellers);
            recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)); // hoặc VERTICAL
            recycler.setAdapter(new BestSellerShoeAdapter(bestSellerShoesList, this));
        }
    }

    public void loadNewsShoes() {
        if (newShoesList == null) {
            newShoesList = new ArrayList<>();
        } else {
            newShoesList.removeAll(newShoesList);
        }

        Cursor cursorShoe = database.getTopNewShoe();
        if (cursorShoe != null && cursorShoe.getCount() > 0) {
            while (cursorShoe.moveToNext()) {
                int shoesId = cursorShoe.getInt(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_ID));
                String shoesName = cursorShoe.getString(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_NAME));
                String shoesInfo = cursorShoe.getString(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_INFORMATION));
                long shoesPrice = cursorShoe.getLong(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_PRICE));
                long shoesQuantity = cursorShoe.getLong(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_QUANTITY));
                String shoesNote = cursorShoe.getString(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_NOTE));
                String shoesImage = cursorShoe.getString(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_IMAGE));

                long cateId = cursorShoe.getLong(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_CATEGORY_ID));
                Cursor cursorCate = database.getCategoryById(cateId);
                if (cursorCate != null && cursorCate.moveToFirst()) {
                    String cateName = cursorCate.getString(cursorCate.getColumnIndexOrThrow(DBHelper.CATEGORY_NAME));

                    Category category = new Category(cateId, cateName);

                    Shoe shoe = new Shoe(shoesId, shoesName, shoesInfo, shoesQuantity, category, shoesPrice, shoesImage, shoesNote);
                    newShoesList.add(shoe);
                }
            }
        }

        //du lieu gia
        Category category = new Category(1, "Sport");

        Shoe shoe1 = new Shoe(1, "Nike 1", "Info: Nike sport", 100, category, 500000, "https://giaybongro.vn/upload/images/1011459600/86/4435_1735813672_thumb2.jpg", "Sport shoes");
        Shoe shoe2 = new Shoe(2, "Nike 2", "Info: Nike sport", 100, category, 500000, "https://giaybongro.vn/upload/images/1008781200/60/5976_1734604728_thumb2.jpg", "Sport shoes");
        Shoe shoe3 = new Shoe(3, "Nike 3", "Info: Nike sport", 100, category, 500000, "https://giaybongro.vn/upload/images/1008781200/60/437_1734604908_thumb2.jpg", "Sport shoes");
        Shoe shoe4 = new Shoe(4, "Nike 4", "Info: Nike sport", 100, category, 500000, "https://giaybongro.vn/upload/images/1008781200/60/9625_1734604838_thumb2.jpg", "Sport shoes");
        Shoe shoe5 = new Shoe(5, "Nike 5", "Info: Nike sport", 100, category, 500000, "https://giaybongro.vn/upload/images/1008781200/43/9306_1727173532_thumb2.jpg", "Sport shoes");

        newShoesList.add(shoe1);
        newShoesList.add(shoe2);
        newShoesList.add(shoe3);
        newShoesList.add(shoe4);
        newShoesList.add(shoe5);


        if (newShoesList != null) {
            recycler = findViewById(R.id.recyclerNewProducts);
            recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)); // hoặc VERTICAL
            recycler.setAdapter(new NewShoeAdapter(newShoesList, this));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        database = new MyDatabase(this);
        // Khởi tạo currentUser
        String username = getIntent().getStringExtra("username");
        currentUser = getCurrentUser(username);

        loadBestSellerShoes();
        loadNewsShoes();

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

        Button buttonCart = findViewById(R.id.buttonCart);
        buttonCart.setOnClickListener(v -> {
            startActivity(new Intent(this, CartActivity.class));
            finish();
        });

    }
}
