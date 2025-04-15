package com.minh.shoemanagement.activities.admin;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.minh.shoemanagement.R;
import com.minh.shoemanagement.activities.admin.adapter.CategoryAdapter;
import com.minh.shoemanagement.entities.Category;
import com.minh.shoemanagement.utils.DBHelper;
import com.minh.shoemanagement.utils.MyDatabase;

import java.util.ArrayList;

public class AdminCategory extends AppCompatActivity {
    public static ArrayList<Category> categories;
    ListView listView;
    public MyDatabase database;
    Button btnInsert, btnUpdate, btnDelete, btnDeleteData;
    EditText editText;
    TextView textViewError;
    private static long pos = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        database = new MyDatabase(this);

        listView = findViewById(R.id.listViewCategory);
        loadCategories();

        editText = findViewById(R.id.editTextCategoryName);
        textViewError = findViewById(R.id.textViewUserError);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editText.setText(categories.get(position).getName());
                pos = categories.get(position).getId();
            }
        });

        btnInsert = findViewById(R.id.btnInsertAdminCategory);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().length() != 0){
                    Category category = getData();
                    if (database.insertCategory(category) != -1){
                        loadCategories();
                        deleteData();
                    }
                }else{
                    textViewError.setText("Bạn chưa nhập danh mục cần thêm!");
                }

            }
        });

        btnUpdate = findViewById(R.id.btnUpdateAdminCategory);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pos != -1){
                    Category category = getData();
                    if(database.updateCategory(category) != -1){
                        loadCategories();
                        deleteData();
                    }
                }else{
                    textViewError.setText("Bạn chưa chọn danh mục cần sửa!");
                }
            }
        });

        btnDelete = findViewById(R.id.btnDeleteAdminCategory);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pos != -1){
                    if(database.deleteCategory(pos) != -1){
                        loadCategories();
                        deleteData();
                    }
                }else{
                    textViewError.setText("Bạn chưa chọn danh mục cần xóa!");
                }
            }
        });

        btnDeleteData = findViewById(R.id.btnDeleteDataCategory);
        btnDeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });


    }

    public void loadCategories(){
        if (categories == null){
            categories = new ArrayList<>();
        }else{
            categories.removeAll(categories);
        }

        Cursor cursor = database.getCategories();
        if(cursor != null){
            while(cursor.moveToNext()){
                Category category = new Category();
                category.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.CATEGORY_ID))));
                category.setName(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.CATEGORY_NAME)));
                categories.add(category);
            }
        }
        if (categories != null)
            listView.setAdapter(new CategoryAdapter(this));
    }

    public void deleteData(){
        editText.setText("");
        textViewError.setText("");
        pos = -1;
    }

    public Category getData(){
        return new Category(pos, editText.getText().toString());
    }
}
