package com.minh.shoemanagement.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.minh.shoemanagement.R;
import com.minh.shoemanagement.activities.admin.AdminHome;
import com.minh.shoemanagement.activities.user.UserHome;

public class LoginActivity extends AppCompatActivity {

    //username và password mặc định để test
    private final String adminUsername = "admin";
    private final String adminPassword = "123";

    private final String userUsername = "user";
    private final String userPassword = "123";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText editTextUsername = findViewById(R.id.editTextUsername);
        EditText editTextPassword = findViewById(R.id.editTextPassword);

        Button loginButton = findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (username.equals(adminUsername) && password.equals(adminPassword)) {
                Intent intent = new Intent(LoginActivity.this, AdminHome.class);
                startActivity(intent);
                finish();
            } else if (username.equals(userUsername) && password.equals(userPassword)) {
                Intent intent = new Intent(LoginActivity.this, UserHome.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Sai thông tin đăng nhập !!",
                        Toast.LENGTH_SHORT).show();
            }

        });

    }

}
