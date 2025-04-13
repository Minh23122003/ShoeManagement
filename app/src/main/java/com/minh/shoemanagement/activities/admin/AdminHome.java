package com.minh.shoemanagement.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.minh.shoemanagement.R;

public class AdminHome extends AppCompatActivity {
    Button btnCategory, btnShoe, btnUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        btnCategory = findViewById(R.id.buttonManageCategories);
        btnShoe = findViewById(R.id.buttonManageShoes);
        btnUser = findViewById(R.id.buttonManageUsers);

        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, AdminCategory.class);
                startActivity(intent);
            }
        });

        btnShoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, AdminShoe.class);
                startActivity(intent);
            }
        });

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, AdminUser.class);
                startActivity(intent);
            }
        });
    }
}
