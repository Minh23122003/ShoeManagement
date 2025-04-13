package com.minh.shoemanagement.activities.user;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minh.shoemanagement.R;
import com.minh.shoemanagement.activities.LoginActivity;
import com.minh.shoemanagement.activities.user.adapter.ShoesAdapter;
import com.minh.shoemanagement.entities.Category;
import com.minh.shoemanagement.entities.Shoe;
import com.minh.shoemanagement.utils.DBHelper;
import com.minh.shoemanagement.utils.MyDatabase;

import java.util.ArrayList;

public class ShoesActivity extends AppCompatActivity {

    private MyDatabase database;
    private RecyclerView recyclerView;

    private ArrayList<Shoe> shoesList;

    private EditText edtSearch;

    Category category;

    private ShoesAdapter adapter;

    public void loadShoesList (){
        if(shoesList == null){
            shoesList = new ArrayList<>();
        }else{
            shoesList.removeAll(shoesList);
        }

        if(category != null){
            Cursor cursorShoe = database.getListShoeByCategory(category, edtSearch.getText().toString());

            Toast.makeText(this, "Giá tị của EditText Search là " + edtSearch.getText().toString(), Toast.LENGTH_SHORT).show();
            
            if (cursorShoe != null && cursorShoe.getCount() > 0){
                while (cursorShoe.moveToNext()){
                    int shoesId = cursorShoe.getInt(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_ID));
                    String shoesName = cursorShoe.getString(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_NAME));
                    String shoesInfo = cursorShoe.getString(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_INFORMATION));
                    long shoesPrice = cursorShoe.getLong(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_PRICE));
                    long shoesQuantity = cursorShoe.getLong(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_QUANTITY));
                    String shoesNote = cursorShoe.getString(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_NOTE));
                    String shoesImage = cursorShoe.getString(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_IMAGE));

                    Shoe shoe = new Shoe(shoesId, shoesName, shoesInfo, shoesQuantity, category, shoesPrice, shoesImage, shoesNote);
                    shoesList.add(shoe);
                }
            }
        }

        Shoe shoe1 = new Shoe(1, "Nike 1", "Info: Nike sport", 100, category, 500000, "https://giaybongro.vn/upload/images/1011459600/86/4435_1735813672_thumb2.jpg", "Sport shoes");
        Shoe shoe2 = new Shoe(2, "Nike 2", "Info: Nike sport", 100, category, 500000, "https://giaybongro.vn/upload/images/1008781200/60/5976_1734604728_thumb2.jpg", "Sport shoes");
        Shoe shoe3 = new Shoe(3, "Nike 3", "Info: Nike sport", 100, category, 500000, "https://giaybongro.vn/upload/images/1008781200/60/437_1734604908_thumb2.jpg", "Sport shoes");
        Shoe shoe4 = new Shoe(4, "Nike 4", "Info: Nike sport", 100, category, 500000, "https://giaybongro.vn/upload/images/1008781200/60/9625_1734604838_thumb2.jpg", "Sport shoes");
        Shoe shoe5 = new Shoe(5, "Nike 5", "Info: Nike sport", 100, category, 500000, "https://giaybongro.vn/upload/images/1008781200/43/9306_1727173532_thumb2.jpg", "Sport shoes");

        shoesList.add(shoe1);
        shoesList.add(shoe2);
        shoesList.add(shoe3);
        shoesList.add(shoe4);
        shoesList.add(shoe5);

        recyclerView = findViewById(R.id.recyclerShoe);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new ShoesAdapter(this, shoesList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_shoes);

        database = new MyDatabase(this);

        category = (Category) getIntent().getSerializableExtra("category");

        edtSearch = findViewById(R.id.editTextSearch);

        TextView textViewTitle = findViewById(R.id.textViewTitle);
        textViewTitle.setText("Bán Giày - " + category.getName());

        loadShoesList();

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

        Button buttonCart = findViewById(R.id.buttonCart);
        buttonCart.setOnClickListener(v -> {
            startActivity(new Intent(this, CartActivity.class));
            finish();
        });


        ImageView buttonSearch = findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(v -> {
            loadShoesList();
        });
    }
}
