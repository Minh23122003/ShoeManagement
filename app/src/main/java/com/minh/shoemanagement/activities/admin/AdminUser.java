package com.minh.shoemanagement.activities.admin;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minh.shoemanagement.R;
import com.minh.shoemanagement.activities.admin.adapter.UserAdapter;
import com.minh.shoemanagement.entities.User;
import com.minh.shoemanagement.utils.DBHelper;
import com.minh.shoemanagement.utils.MyDatabase;

import java.time.LocalDate;
import java.util.ArrayList;

public class AdminUser extends AppCompatActivity {
    public static ArrayList<User> users;
    RecyclerView recyclerView;
    public MyDatabase database;
    Button btnInsert, btnUpdate, btnDelete, btnDeleteData;
    EditText editTextUsername, editTextPassword, editTextName, editTextAddress, editTextPhone;
    RadioButton radioButtonYes, radioButtonNo;
    TextView textViewError;
    private static long pos = -1;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user);

        database = new MyDatabase(this);

        textViewError = findViewById(R.id.textViewUserError);
        editTextUsername = findViewById(R.id.editTextUserUsername);
        editTextPassword = findViewById(R.id.editTextUserPassword);
        editTextName = findViewById(R.id.editTextUserName);
        editTextAddress = findViewById(R.id.editTextUserAddress);
        editTextPhone = findViewById(R.id.editTextUserPhone);
        radioButtonYes = findViewById(R.id.rdBtnIsAdminYes);
        radioButtonNo = findViewById(R.id.rdBtnIsAdminNo);

        recyclerView = findViewById(R.id.recyclerViewAdminUser);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminUser.this));
        loadUsers();

        btnInsert = findViewById(R.id.btnInsertUser);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextUsername.getText().toString().length() != 0 &&
                        editTextPassword.getText().toString().length() != 0 &&
                        editTextName.getText().toString().length() != 0 &&
                        editTextAddress.getText().toString().length() != 0 &&
                        editTextPhone.getText().toString().length() != 0){
                    User user = getData();
                    if(database.insertUser(user) != -1){
                        loadUsers();
                        deleteData();

                    }
                }else{
                    textViewError.setText("Vui lòng nhập đầy đủ thông tin!");
                }

            }
        });

        btnUpdate = findViewById(R.id.btnUpdateUser);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pos != -1){
                    if(editTextUsername.getText().toString().length() != 0 &&
                            editTextPassword.getText().toString().length() != 0 &&
                            editTextName.getText().toString().length() != 0 &&
                            editTextAddress.getText().toString().length() != 0 &&
                            editTextPhone.getText().toString().length() != 0){
                        User user = getData();
                        if(database.updateUser(user) != -1){
                            loadUsers();
                            deleteData();
                        }
                    }else{
                        textViewError.setText("Vui lòng nhập đầy đủ thông tin!");
                    }
                }else{
                    textViewError.setText("Bạn chưa chọn người dùng cần sửa!");
                }
            }
        });

        btnDelete = findViewById(R.id.btnDeleteUser);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pos != -1){
                    if(database.deleteUser(pos) != -1){
                        loadUsers();
                        deleteData();
                    }
                }else{
                    textViewError.setText("Bạn chưa chọn người dùng cần xóa!");
                }
            }
        });

        btnDeleteData = findViewById(R.id.btnDeleteDataUser);
        btnDeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });
    }

    public void loadUsers(){
        if(users == null){
            users = new ArrayList<>();
        }else{
            users.removeAll(users);
        }

        Cursor cursor = database.getUsers();
        if(cursor != null){
            while(cursor.moveToNext()){
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.USER_ID))));
                user.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.USER_USERNAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.USER_PASSWORD)));
                user.setName(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.USER_NAME)));
                user.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.USER_ADDRESS)));
                user.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.USER_PHONE)));
                user.setIsAdmin(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.USER_IS_ADMIN))));
                user.setCreatedDate(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.USER_CREATED_DATE)));
                users.add(user);
            }
        }

        if(users != null){
            UserAdapter adapter = new UserAdapter(users, new UserAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(User user) {
                    pos = user.getId();
                    editTextUsername.setText(user.getUsername());
                    editTextPassword.setText(user.getPassword());
                    editTextName.setText(user.getName());
                    editTextAddress.setText(user.getAddress());
                    editTextPhone.setText(user.getPhone());
                    if(user.getIsAdmin() == 0)
                        radioButtonNo.setChecked(true);
                    else
                        radioButtonYes.setChecked(true);
                }
            });
            recyclerView.setAdapter(adapter);
        }
    }

    public void deleteData(){
        pos = -1;
        textViewError.setText("");
        editTextUsername.setText("");
        editTextPassword.setText("");
        editTextName.setText("");
        editTextAddress.setText("");
        editTextPhone.setText("");
        radioButtonNo.setChecked(true);
    }

    public User getData(){
        User user = new User();
        user.setId(pos);
        user.setUsername(editTextUsername.getText().toString());
        user.setPassword(editTextPassword.getText().toString());
        user.setName(editTextName.getText().toString());
        user.setAddress(editTextAddress.getText().toString());
        user.setPhone(editTextPhone.getText().toString());
        if(radioButtonYes.isChecked() == true)
            user.setIsAdmin(1);
        else
            user.setIsAdmin(0);
        user.setCreatedDate(LocalDate.now().toString());

        return user;
    }
}
