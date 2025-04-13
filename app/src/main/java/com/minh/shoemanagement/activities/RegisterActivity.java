package com.minh.shoemanagement.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.minh.shoemanagement.R;
import com.minh.shoemanagement.entities.User;
import com.minh.shoemanagement.utils.MyDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtName, edtAddress, edtPhoneNumber, edtUsername, edtPassword;

    MyDatabase database;

    // get data from EditText
    public User getUserDataFromEditText() {

        edtName = findViewById(R.id.edtName);
        edtAddress = findViewById(R.id.edtAddress);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);

        User user = new User();
        user.setName(edtName.getText().toString().trim());
        user.setAddress(edtAddress.getText().toString().trim());
        user.setPhone(edtPhoneNumber.getText().toString().trim());
        user.setUsername(edtUsername.getText().toString().trim());
        user.setPassword(edtPassword.getText().toString().trim());
        user.setIsAdmin(0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            user.setCreatedDate(formatter.format(LocalDate.now()));
        }

        return user;
    }

    public void createUser(View view) {
        User user = getUserDataFromEditText();


        // validate username,
        Cursor cursorUser = database.searchUserByUsername(user.getUsername());
        if(cursorUser!=null && cursorUser.getCount() > 0){
            Toast.makeText(getApplicationContext(), "Tên đăng nhập đã được đăng ký!", Toast.LENGTH_SHORT).show();
            edtUsername.setText(null);
            return;
        }

        if (database.addUser(user) != -1) {
            Toast.makeText(getApplicationContext(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

            edtName.setText(null);
            edtAddress.setText(null);
            edtPhoneNumber.setText(null);
            edtUsername.setText(null);
            edtPassword.setText(null);

            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        this.database = new MyDatabase(this);

        Button buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(this::createUser);

        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
