package com.minh.shoemanagement.activities.user;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minh.shoemanagement.R;
import com.minh.shoemanagement.activities.user.adapter.BestSellerShoeAdapter;
import com.minh.shoemanagement.activities.user.adapter.NewShoeAdapter;
import com.minh.shoemanagement.entities.Category;
import com.minh.shoemanagement.entities.Shoe;
import com.minh.shoemanagement.utils.MyDatabase;

import java.util.ArrayList;

public class UserHome extends AppCompatActivity {

    public static RecyclerView recycler;

    public static ArrayList<Shoe> bestSellerShoesList;

    public static ArrayList<Shoe> newShoesList;

    public static MyDatabase database;

    public void loadBestSellerShoes() {
        if (bestSellerShoesList == null) {
            bestSellerShoesList = new ArrayList<>();
        } else {
            bestSellerShoesList.removeAll(bestSellerShoesList);
        }

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
            recycler.setAdapter(new BestSellerShoeAdapter(bestSellerShoesList, this.getApplicationContext()));
        }
    }

    public void loadNewsShoes() {
        if (newShoesList == null) {
            newShoesList = new ArrayList<>();
        } else {
            newShoesList.removeAll(newShoesList);
        }

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
            recycler.setAdapter(new NewShoeAdapter(newShoesList, this.getApplicationContext()));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        loadBestSellerShoes();
        loadNewsShoes();
    }
}
