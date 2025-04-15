package com.minh.shoemanagement.activities.admin;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.minh.shoemanagement.R;
import com.minh.shoemanagement.activities.admin.adapter.BillAdapter;
import com.minh.shoemanagement.utils.MyDatabase;

import java.util.ArrayList;

public class AdminBill extends AppCompatActivity {
    public static ArrayList<ArrayList<String>> arrayLists;
    Button btnStatsUser, btnStatsCategory;
    ListView listViewStats;
    public MyDatabase database;
    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_bill);

        database = new MyDatabase(this);
        listViewStats = findViewById(R.id.listViewStats);

        btnStatsUser = findViewById(R.id.btnStatsByUser);
        btnStatsUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statsByUser();
                textView = findViewById(R.id.textViewAdminStatsHeader1);
                textView.setText("Id");
                textView = findViewById(R.id.textViewAdminStatsHeader2);
                textView.setText("Tài khoản");
                textView = findViewById(R.id.textViewAdminStatsHeader3);
                textView.setText("Số lượng");
            }
        });

        btnStatsCategory = findViewById(R.id.btnStatsByCategory);
        btnStatsCategory.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statsByCategory();
                textView = findViewById(R.id.textViewAdminStatsHeader1);
                textView.setText("Mã danh mục");
                textView = findViewById(R.id.textViewAdminStatsHeader2);
                textView.setText("Tên danh mục");
                textView = findViewById(R.id.textViewAdminStatsHeader3);
                textView.setText("Số lượng");
            }
        }));
    }

    public void statsByUser(){
        if(arrayLists == null)
            arrayLists = new ArrayList<>();
        else{
            arrayLists.removeAll(arrayLists);
        }

        Cursor cursor = database.statsByUser();
        while(cursor.moveToNext()){
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(cursor.getString(0));
            arrayList.add(cursor.getString(1));
            arrayList.add(cursor.getString(2));
            arrayLists.add(arrayList);
        }
        if(arrayLists != null){
            listViewStats.setAdapter(new BillAdapter(this));
        }
    }

    public void statsByCategory(){
        if(arrayLists == null)
            arrayLists = new ArrayList<>();
        else{
            arrayLists.removeAll(arrayLists);
        }

        Cursor cursor = database.statsByCategory();
        while(cursor.moveToNext()){
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(cursor.getString(0));
            arrayList.add(cursor.getString(1));
            arrayList.add(cursor.getString(2));
            arrayLists.add(arrayList);
        }
        if(arrayLists != null){
            listViewStats.setAdapter(new BillAdapter(this));
        }
    }
}
