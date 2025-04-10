package com.minh.shoemanagement.activities.admin;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.minh.shoemanagement.R;
import com.minh.shoemanagement.activities.admin.adapter.CategoryAdapter;
import com.minh.shoemanagement.activities.admin.adapter.ShoeAdapter;
import com.minh.shoemanagement.entities.Category;
import com.minh.shoemanagement.entities.Shoe;
import com.minh.shoemanagement.utils.DBHelper;
import com.minh.shoemanagement.utils.MyDatabase;

import java.util.ArrayList;

public class AdminShoe extends AppCompatActivity {
    public static ArrayList<Shoe> shoes;
    public static ArrayList<Category> categories;
    ListView listView;
    Spinner spinner;
    public MyDatabase database;
    Button btnInsert, btnUpdate, btnDelete, btnDeleteData;
    EditText editTextName, editTextInformation, editTextQuantity, editTextPrice, editTextNote;
    TextView textViewError;
    private static long pos = -1;
    private static long posCategory;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aactivity_admin_shoe);

        database = new MyDatabase(this);
        btnInsert = findViewById(R.id.btnInsertAdminShoe);
        btnUpdate = findViewById(R.id.btnUpdateAdminShoe);
        btnDelete = findViewById(R.id.btnDeleteAdminShoe);
        btnDeleteData = findViewById(R.id.btnDeleteDataShoe);
        listView = findViewById(R.id.listViewAdminShoe);
        spinner = findViewById(R.id.spinnerCategory);
        editTextName = findViewById(R.id.editTextAdminShoeName);
        editTextInformation = findViewById(R.id.editTextAdminShoeInformation);
        editTextQuantity = findViewById(R.id.editTextAdminShoeQuantity);
        editTextPrice = findViewById(R.id.editTextAdminShoePrice);
        editTextNote = findViewById(R.id.editTextAdminShoeNote);

        loadCategories();
        ArrayList<String> arrayList = new ArrayList<>();
        for(int i=0; i < categories.size(); i++){
            arrayList.add(categories.get(i).getName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        loadShoes();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posCategory = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextName.getText().toString().length() != 0 &&
                editTextInformation.getText().toString().length() != 0 &&
                editTextQuantity.getText().toString().length() != 0 &&
                editTextPrice.getText().toString().length() != 0 &&
                editTextNote.getText().toString().length() != 0){
                    Shoe shoe = new Shoe();
                    shoe.setId(-1);
                    shoe.setName(editTextName.getText().toString());
                    shoe.setInformation(editTextInformation.getText().toString());
                    shoe.setQuantity(Long.parseLong(editTextQuantity.getText().toString()));
                    shoe.setPrice(Long.parseLong(editTextPrice.getText().toString()));
                    shoe.setNote(editTextNote.getText().toString());
                    shoe.setImage("gege");
                    shoe.setCategory(categories.get((int) posCategory));

                    if(database.insertShoe(shoe) != -1){
                        loadShoes();
                        textViewError.setText("");
                        editTextName.setText("");
                        editTextInformation.setText("");
                        editTextQuantity.setText("");
                        editTextPrice.setText("");
                        editTextNote.setText("");
                    }
                }else{
                    textViewError.setText("Vui lòng nhập đầy đủ thông tin!");
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = shoes.get(position).getId();
                editTextName.setText(shoes.get(position).getName());
                editTextInformation.setText(shoes.get(position).getInformation());
                editTextQuantity.setText(String.valueOf(shoes.get(position).getQuantity()));
                editTextPrice.setText(String.valueOf(shoes.get(position).getPrice()));
                editTextNote.setText(shoes.get(position).getNote());
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pos != -1){
                    if(editTextName.getText().toString().length() != 0 &&
                            editTextInformation.getText().toString().length() != 0 &&
                            editTextQuantity.getText().toString().length() != 0 &&
                            editTextPrice.getText().toString().length() != 0 &&
                            editTextNote.getText().toString().length() != 0){
                        Shoe shoe = new Shoe();
                        shoe.setId(pos);
                        shoe.setName(editTextName.getText().toString());
                        shoe.setInformation(editTextInformation.getText().toString());
                        shoe.setQuantity(Long.parseLong(editTextQuantity.getText().toString()));
                        shoe.setPrice(Long.parseLong(editTextPrice.getText().toString()));
                        shoe.setNote(editTextNote.getText().toString());
                        shoe.setImage("gege");
                        shoe.setCategory(categories.get((int) posCategory));

                        if(database.updateShoe(shoe) != -1){
                            loadShoes();
                            textViewError.setText("");
                            editTextName.setText("");
                            editTextInformation.setText("");
                            editTextQuantity.setText("");
                            editTextPrice.setText("");
                            editTextNote.setText("");
                            loadCategories();
                        }
                    }else{
                        textViewError.setText("Vui lòng nhập đầy đủ thông tin!");
                    }
                }else{
                    textViewError.setText("Vui lòng chọn giày cần sửa!");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pos != -1){
                    if(database.deleteShoe(pos) != -1){
                        loadShoes();
                        pos = -1;
                        textViewError.setText("");
                        editTextName.setText("");
                        editTextInformation.setText("");
                        editTextQuantity.setText("");
                        editTextPrice.setText("");
                        editTextNote.setText("");
                        loadCategories();
                    }
                }else{
                    textViewError.setText("Bạn chưa chọn giày cần xóa!");
                }
            }
        });

        btnDeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = -1;
                textViewError.setText("");
                editTextName.setText("");
                editTextInformation.setText("");
                editTextQuantity.setText("");
                editTextPrice.setText("");
                editTextNote.setText("");
                loadCategories();
            }
        });


    }

    public void loadShoes(){
        if(shoes == null){
            shoes = new ArrayList<>();
        }else{
            shoes.removeAll(shoes);
        }
        Cursor cursor = database.getShoes();
        if(cursor != null){
            while(cursor.moveToNext()){
                Shoe shoe = new Shoe();
                shoe.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.SHOE_ID))));
                shoe.setName(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.SHOE_NAME)));
                shoe.setInformation(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.SHOE_INFORMATION)));
                shoe.setQuantity(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.SHOE_QUANTITY))));
                shoe.setPrice(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.SHOE_PRICE))));
                shoe.setImage("");
                shoe.setNote(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.SHOE_NOTE)));
                for(int i = 0; i < categories.size(); i ++)
                    if(categories.get(i).getId() == Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.SHOE_CATEGORY_ID))))
                        shoe.setCategory(categories.get(i));
                shoes.add(shoe);
            }
        }

        if(shoes != null){
            listView.setAdapter(new ShoeAdapter(this));
        }
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
    }
}
