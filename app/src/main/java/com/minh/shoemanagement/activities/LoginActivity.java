package com.minh.shoemanagement.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.minh.shoemanagement.R;
import com.minh.shoemanagement.activities.admin.AdminHome;
import com.minh.shoemanagement.activities.user.UserHome;
import com.minh.shoemanagement.entities.User;
import com.minh.shoemanagement.utils.DBHelper;
import com.minh.shoemanagement.utils.MyDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LoginActivity extends AppCompatActivity {

    //username và password mặc định để test


    MyDatabase database;

    EditText editTextUsername;
    EditText editTextPassword;

    public void login(View view) {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        Cursor userCursor = database.searchUserByUsername(username);


        if (userCursor != null && userCursor.moveToFirst()) {
            String dbPassword = userCursor.getString(userCursor.getColumnIndexOrThrow(DBHelper.USER_PASSWORD));

            if (!dbPassword.equals(password)) {
                editTextPassword.setText(null);
                Toast.makeText(this, "Sai mật khẩu !!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Đăng nhập thành công !!", Toast.LENGTH_SHORT).show();

                int userRole = userCursor.getInt(userCursor.getColumnIndexOrThrow(DBHelper.USER_IS_ADMIN));
                Class<?> targetActivity = (userRole == 0) ? UserHome.class : AdminHome.class;

                Intent intent = new Intent(this, targetActivity);
                //bind username
                intent.putExtra("username", userCursor.getString(userCursor.getColumnIndexOrThrow(DBHelper.USER_USERNAME)));

                startActivity(intent);
                finish();
            }

            userCursor.close();
        } else {
            Toast.makeText(this, "Sai thông tin đăng nhập !!", Toast.LENGTH_SHORT).show();
        }

    }

    public void initAdmin (){
        Cursor adminCursor = database.searchUserByUsername("admin");

        if(adminCursor != null && adminCursor.moveToFirst()){
            Log.d("Init admin", "initAdmin: base admin existed");
        }else{
            User user = new User();
            user.setUsername("admin");
            user.setPassword("123");
            user.setName("admin");
            user.setPhone("");
            user.setAddress("");
            user.setPhone("");
            user.setIsAdmin(1);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                user.setCreatedDate(formatter.format(LocalDate.now()));
            }

            if(database.addUser(user)!=-1){
                Log.d("Init admin", "initAdmin: Init Successfully !");
            }else{
                Log.d("Init admin", "initAdmin: Init Fail !");
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.database = new MyDatabase(this);
        initAdmin();
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        Button loginButton = findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(this::login);

        Button buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });



    }

}
