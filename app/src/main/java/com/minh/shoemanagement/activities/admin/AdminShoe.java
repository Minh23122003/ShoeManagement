package com.minh.shoemanagement.activities.admin;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cloudinary.Cloudinary;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.cloudinary.utils.ObjectUtils;

import com.minh.shoemanagement.R;
import com.minh.shoemanagement.activities.admin.adapter.ShoeAdapter;
import com.minh.shoemanagement.entities.Category;
import com.minh.shoemanagement.entities.Shoe;
import com.minh.shoemanagement.utils.DBHelper;
import com.minh.shoemanagement.utils.MyDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminShoe extends AppCompatActivity {
    public static ArrayList<Shoe> shoes;
    public static ArrayList<Category> categories;
    RecyclerView recyclerView;
    Spinner spinner;
    public MyDatabase database;
    Button btnInsert, btnUpdate, btnDelete, btnDeleteData, btnImage;
    EditText editTextName, editTextInformation, editTextQuantity, editTextPrice, editTextNote;
    TextView textViewError;
    ImageView imageView;
    private static long pos = -1;
    private static long posCategory;
    Cloudinary cloudinary;
    private Uri imagePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_shoe);

        database = new MyDatabase(this);

        spinner = findViewById(R.id.spinnerCategory);
        editTextName = findViewById(R.id.editTextAdminShoeName);
        editTextInformation = findViewById(R.id.editTextAdminShoeInformation);
        editTextQuantity = findViewById(R.id.editTextAdminShoeQuantity);
        editTextPrice = findViewById(R.id.editTextAdminShoePrice);
        editTextNote = findViewById(R.id.editTextAdminShoeNote);
        textViewError = findViewById(R.id.textViewAdminShoeError);
        imageView = findViewById(R.id.imageViewAdminShoe);
        btnImage = findViewById(R.id.btnAdminShoeImage);

        recyclerView = findViewById(R.id.recyclerViewAdminShoe);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminShoe.this));

        Map config = new HashMap();
        config.put("cloud_name", "dyehwnue5");
        config.put("api_key", "944547956246838");
        config.put("api_secret", "RoCUyh0je2qJ79EnJMhu-3cmyIY");
        cloudinary = new Cloudinary(config);
        MediaManager.init(this, config);

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

        btnInsert = findViewById(R.id.btnInsertAdminShoe);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    shoe.setCategory(categories.get((int) posCategory));

                    if(imagePath != null){
                        MediaManager.get().upload(imagePath).callback(new UploadCallback() {
                            @Override
                            public void onStart(String requestId) {

                            }

                            @Override
                            public void onProgress(String requestId, long bytes, long totalBytes) {

                            }

                            @Override
                            public void onSuccess(String requestId, Map resultData) {
                                shoe.setImage(resultData.get("secure_url").toString());

                                if(database.insertShoe(shoe) != -1){
                                    loadShoes();
                                    deleteData();
                                }
                            }

                            @Override
                            public void onError(String requestId, ErrorInfo error) {

                            }

                            @Override
                            public void onReschedule(String requestId, ErrorInfo error) {

                            }
                        }).dispatch();
                    }else{
                        shoe.setImage(shoes.get((int) pos).getImage());
                        if(database.insertShoe(shoe) != -1){
                            loadShoes();
                            deleteData();
                        }
                    }
                }else{
                    textViewError.setText("Vui lòng nhập đầy đủ thông tin!");
                }
            }
        });

        btnUpdate = findViewById(R.id.btnUpdateAdminShoe);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pos != -1){
                    if(!editTextName.getText().toString().isEmpty() &&
                            !editTextInformation.getText().toString().isEmpty() &&
                            !editTextQuantity.getText().toString().isEmpty() &&
                            !editTextPrice.getText().toString().isEmpty() &&
                            !editTextNote.getText().toString().isEmpty()){
                        Shoe shoe = new Shoe();
                        shoe.setId(pos);
                        shoe.setName(editTextName.getText().toString());
                        shoe.setInformation(editTextInformation.getText().toString());
                        shoe.setQuantity(Long.parseLong(editTextQuantity.getText().toString()));
                        shoe.setPrice(Long.parseLong(editTextPrice.getText().toString()));
                        shoe.setNote(editTextNote.getText().toString());
                        shoe.setCategory(categories.get((int) posCategory));

                        if(imagePath != null){
                            MediaManager.get().upload(imagePath).callback(new UploadCallback() {
                                @Override
                                public void onStart(String requestId) {

                                }

                                @Override
                                public void onProgress(String requestId, long bytes, long totalBytes) {

                                }

                                @Override
                                public void onSuccess(String requestId, Map resultData) {
                                    shoe.setImage(resultData.get("secure_url").toString());
                                    if(database.updateShoe(shoe) != -1){
                                        loadShoes();
                                        deleteData();
                                    }
                                }

                                @Override
                                public void onError(String requestId, ErrorInfo error) {

                                }

                                @Override
                                public void onReschedule(String requestId, ErrorInfo error) {

                                }
                            }).dispatch();
                        }else{
                            for(int i = 0; i < shoes.size(); i++)
                                if(shoes.get(i).getId() == pos)
                                    shoe.setImage(shoes.get((int) i).getImage());
                            if(database.updateShoe(shoe) != -1){
                                loadShoes();
                                deleteData();
                            }
                        }
                    }else{
                        textViewError.setText("Vui lòng nhập đầy đủ thông tin!");
                    }
                }else{
                    textViewError.setText("Vui lòng chọn giày cần sửa!");
                }
            }
        });

        btnDelete = findViewById(R.id.btnDeleteAdminShoe);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pos != -1){
                    if(database.deleteShoe(pos) != -1){
                        loadShoes();
                        deleteData();
                    }
                }else{
                    textViewError.setText("Bạn chưa chọn giày cần xóa!");
                }
            }
        });

        btnDeleteData = findViewById(R.id.btnDeleteDataShoe);
        btnDeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
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
                shoe.setImage(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.SHOE_IMAGE)));
                shoe.setNote(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.SHOE_NOTE)));
                for(int i = 0; i < categories.size(); i ++)
                    if(categories.get(i).getId() == Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.SHOE_CATEGORY_ID))))
                        shoe.setCategory(categories.get(i));
                shoes.add(shoe);
            }
        }

        if(shoes != null){
            ShoeAdapter adapter = new ShoeAdapter(shoes, new ShoeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Shoe shoe) {
                    pos = shoe.getId();
                    editTextName.setText(shoe.getName());
                    editTextInformation.setText(shoe.getInformation());
                    editTextQuantity.setText(String.valueOf(shoe.getQuantity()));
                    editTextPrice.setText(String.valueOf(shoe.getPrice()));
                    editTextNote.setText(shoe.getNote());
                    for(int i = 0; i < categories.size(); i++){
                        if(categories.get(i).getId() == shoe.getCategory().getId())
                            spinner.setSelection(i);
                    }
                    Glide.with(getApplicationContext()).load(shoe.getImage()).into(imageView);
                }
            });
            recyclerView.setAdapter(adapter);
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
    private void imageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launchSomeActivity.launch(intent);

    }

    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if(result.getResultCode() == Activity.RESULT_OK){
            Intent data = result.getData();
            if(data != null && data.getData() != null){
                imagePath = data.getData();
                Bitmap bitmap = null;
                try{
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagePath);
                }catch(IOException e){
                    e.printStackTrace();
                }
                if(bitmap != null){
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    });

    public void deleteData(){
        pos = -1;
        textViewError.setText("");
        editTextName.setText("");
        editTextInformation.setText("");
        editTextQuantity.setText("");
        editTextPrice.setText("");
        editTextNote.setText("");
        imageView.setImageBitmap(null);
        imagePath = null;
        spinner.setSelection(0);
    }
}
