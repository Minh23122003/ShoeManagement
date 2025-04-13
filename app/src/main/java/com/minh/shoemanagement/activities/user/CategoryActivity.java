package com.minh.shoemanagement.activities.user;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minh.shoemanagement.R;
import com.minh.shoemanagement.activities.LoginActivity;
import com.minh.shoemanagement.activities.user.adapter.CategoryAdapter;
import com.minh.shoemanagement.activities.user.adapter.NewShoeAdapter;
import com.minh.shoemanagement.entities.Category;
import com.minh.shoemanagement.utils.DBHelper;
import com.minh.shoemanagement.utils.MyDatabase;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    public static RecyclerView recycler;

    public static MyDatabase database;

    ArrayList<Category> categories;


    public void loadCategoryList() {
        if (categories == null) {
            categories = new ArrayList<>();
        } else {
            categories.removeAll(categories);
        }

        Cursor cursorCategory = database.getCategories();
        if (cursorCategory != null && cursorCategory.getCount() > 0) {
            while (cursorCategory.moveToNext()) {
                int categoryId = Integer.parseInt(cursorCategory.getString(cursorCategory.getColumnIndexOrThrow(DBHelper.CATEGORY_ID)));
                String categoryName = cursorCategory.getString(cursorCategory.getColumnIndexOrThrow(DBHelper.CATEGORY_NAME));

                Category category = new Category(categoryId, categoryName);
                categories.add(category);
            }
        }

        //Tạo data giả
        Category cate1 = new Category(1, "Giày thể thao");
        Category cate2 = new Category(2, "Giày bóng đá");
        Category cate3 = new Category(3, "Giày bóng rổ");
        Category cate4 = new Category(4, "Giày thời trang nữ");

        categories.add(cate1);
        categories.add(cate2);
        categories.add(cate3);
        categories.add(cate4);

        //

        if(categories != null){
            recycler = findViewById(R.id.recyclerCategory);
            recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recycler.setAdapter(new CategoryAdapter(this,categories));
        }


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_category);

        database = new MyDatabase(this.getApplicationContext());

        loadCategoryList();

        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
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
