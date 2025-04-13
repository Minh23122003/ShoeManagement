package com.minh.shoemanagement.activities.user;

import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.minh.shoemanagement.R;
import com.minh.shoemanagement.activities.LoginActivity;
import com.minh.shoemanagement.activities.user.adapter.RatingAdapter;
import com.minh.shoemanagement.entities.Bill;
import com.minh.shoemanagement.entities.Category;
import com.minh.shoemanagement.entities.Rating;
import com.minh.shoemanagement.entities.Shoe;
import com.minh.shoemanagement.entities.User;
import com.minh.shoemanagement.utils.DBHelper;
import com.minh.shoemanagement.utils.MyDatabase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ShoesDetailActivity extends AppCompatActivity {

    private MyDatabase database;

    private long shoeId;
    Shoe shoe;

    private ImageView shoeImage;
    private TextView tvTitle, tvShoeName, tvShoeInfo, tvShoePrice, tvShoeCate, tvShoeQuantity;

    long billSize, billQuantity;

    private ArrayList<Rating> ratingList;

    RecyclerView recyclerViewRating;

    RatingAdapter ratingAdapter;

    public Shoe getShoeById (){
        Cursor cursorShoe = database.getShoeById(shoeId);

        if (cursorShoe != null && cursorShoe.moveToFirst()){
            int shoesId = cursorShoe.getInt(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_ID));
            String shoesName = cursorShoe.getString(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_NAME));
            String shoesInfo = cursorShoe.getString(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_INFORMATION));
            long shoesPrice = cursorShoe.getLong(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_PRICE));
            long shoesQuantity = cursorShoe.getLong(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_QUANTITY));
            String shoesNote = cursorShoe.getString(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_NOTE));
            String shoesImage = cursorShoe.getString(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_IMAGE));

            long shoeCateId = cursorShoe.getLong(cursorShoe.getColumnIndexOrThrow(DBHelper.SHOE_CATEGORY_ID));
            Cursor cursorCate = database.getCategoryById(shoeCateId);
            cursorCate.moveToFirst();

            long cateId = cursorCate.getLong(cursorCate.getColumnIndexOrThrow(DBHelper.CATEGORY_ID));
            String cateName = cursorCate.getString(cursorCate.getColumnIndexOrThrow(DBHelper.CATEGORY_NAME));

            Category category = new Category(cateId, cateName);

            Shoe shoe = new Shoe(shoesId, shoesName, shoesInfo, shoesQuantity, category, shoesPrice, shoesImage, shoesNote);
            return shoe;
        }

        return null;
    }

    public long addToCart (View view){

        if(shoe == null)return -1;

        Bill bill = new Bill();
        bill.setUser(UserHome.currentUser);
        bill.setShoe(shoe);
        bill.setQuantity(billQuantity);
        bill.setSize(billSize);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            bill.setCreatedDate(formatter.format(LocalDate.now()));
        }

        bill.setPaymentDate("pending");

        return database.userAddToCart(bill);
    }


    private void showAddToCartDialog (){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_to_cart, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        ImageView imageViewShoe = dialogView.findViewById(R.id.imageViewShoe);
        TextView tvShoeName = dialogView.findViewById(R.id.textViewShoeName);
        TextView tvShoeInfo = dialogView.findViewById(R.id.textViewShoeInfo);
        TextView tvShoePrice = dialogView.findViewById(R.id.textViewShoePrice);
        TextView tvShoeCate = dialogView.findViewById(R.id.textViewShoeCategory);
        TextView tvShoeQuantity = dialogView.findViewById(R.id.textViewShoeQuantity);

        EditText edtBillSize = dialogView.findViewById(R.id.editTextSize);
        EditText edtBillQuantity = dialogView.findViewById(R.id.editTextQuantity);

        // Lấy dữ liệu Shoe để hiển thị
        if(shoe!=null){
            Glide.with(this).load(shoe.getImage()).into(imageViewShoe);
            tvShoeName.setText(shoe.getName());
            tvShoeInfo.setText(shoe.getInformation());
            tvShoePrice.setText(String.valueOf(shoe.getPrice()));
            tvShoeCate.setText(shoe.getCategory().getName());
            tvShoeQuantity.setText(String.valueOf(shoe.getQuantity()));
        }


        Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        Button buttonConfirm = dialogView.findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(v -> {

            if(edtBillSize.getText().toString().isEmpty() || edtBillQuantity.getText().toString().isEmpty()){
                Toast.makeText(this, "Bạn chưa chọn kích thước hoặc số lượng", Toast.LENGTH_SHORT).show();
            }else{
                billSize = Long.parseLong(edtBillSize.getText().toString());
                billQuantity = Long.parseLong(edtBillQuantity.getText().toString());

                if(addToCart(v) != -1){
                    Toast.makeText(this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                    showAfterAddDialog();
                    dialog.dismiss();
                }else{
                    Toast.makeText(this, "Xảy ra lỗi khi thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    private void showAfterAddDialog(){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_after_add_to_cart, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();


        //set onclick
        Button buttonGoToCart = dialogView.findViewById(R.id.buttonGoToCart);
        buttonGoToCart.setOnClickListener(v -> {
            Intent intent = new Intent(ShoesDetailActivity.this, CartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            dialog.dismiss();
        });

        Button buttonContinue = dialogView.findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener(v -> {

            if(shoe!=null){
                Intent intent = new Intent(ShoesDetailActivity.this, ShoesActivity.class);
                intent.putExtra("category", shoe.getCategory());
                startActivity(intent);
                dialog.dismiss();
            }else{
                Toast.makeText(this, "Không tìm thấy thông tin để chuyển trang", Toast.LENGTH_SHORT).show();
            }

        });


        dialog.show();
    }

    public void loadRating (){
        if(ratingList == null){
            ratingList = new ArrayList<>();
        }else{
            ratingList.removeAll(ratingList);
        }

        Cursor cursorRating = database.getRatingByShoeId(shoeId);
        if(cursorRating!=null && cursorRating.getCount() > 0){

            while (cursorRating.moveToNext()){

                Rating rating;

                int id = cursorRating.getInt(cursorRating.getColumnIndexOrThrow(DBHelper.RATING_ID));
                long star = cursorRating.getLong(cursorRating.getColumnIndexOrThrow(DBHelper.RATING_STAR));
                String content = cursorRating.getString(cursorRating.getColumnIndexOrThrow(DBHelper.RATING_CONTENT));
                String createdDate = cursorRating.getString(cursorRating.getColumnIndexOrThrow(DBHelper.RATING_CREATED_DATE));

                long userId = cursorRating.getLong(cursorRating.getColumnIndexOrThrow(DBHelper.RATING_USER_ID));
                Cursor cursorUser = database.getUserById(userId);

                User user = new User();
                if(cursorUser!=null && cursorUser.moveToFirst()){
                    user.setIsAdmin(userId);
                    user.setName(cursorUser.getString(cursorUser.getColumnIndexOrThrow(DBHelper.USER_NAME)));
                }

                rating = new Rating(id, star, content, user, shoe, createdDate);

                ratingList.add(rating);
            }
        }

        //du lieu test
        User user1 = new User();
        user1.setName("JohnSon");
        Rating rating1 = new Rating(1, 4, "Good product. I love it", user1, null, "01/02/2025");

        User user2 = new User();
        user2.setName("Alex");
        Rating rating2 = new Rating(2, 5, "Its really nice, absolutely fit", user2, null, "16/03/2025");

        User user3 = new User();
        user3.setName("Kendra");
        Rating rating3 = new Rating(2, 5, "Gorgeous !!", user3, null, "25/12/2025");

        ratingList.add(rating1);
        ratingList.add(rating2);
        ratingList.add(rating3);

        if(recyclerViewRating != null){
            recyclerViewRating.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            ratingAdapter = new RatingAdapter(this, ratingList);
            recyclerViewRating.setAdapter(ratingAdapter);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_shoes_detail);

        database = new MyDatabase(this);

        shoeId = getIntent().getLongExtra("shoesId", -1);
        shoe = getShoeById();

        recyclerViewRating = findViewById(R.id.recyclerRating);
        
        if(shoe != null){
            shoeImage = findViewById(R.id.imageViewShoe);
            tvTitle = findViewById(R.id.textViewTitle);
            tvShoeName = findViewById(R.id.textViewShoeName);
            tvShoeInfo = findViewById(R.id.textViewShoeInfo);
            tvShoePrice = findViewById(R.id.textViewShoePrice);
            tvShoeCate = findViewById(R.id.textViewShoeCategory);
            tvShoeQuantity= findViewById(R.id.textViewShoeQuantity);

            String title = "Bán Giày - " + shoe.getName();
            tvTitle.setText(title);

            Glide.with(this).load(shoe.getImage()).into(shoeImage);
            tvShoeName.setText(shoe.getName());
            tvShoeInfo.setText(shoe.getInformation());
            tvShoePrice.setText(String.valueOf(shoe.getPrice()));
            tvShoeCate.setText(shoe.getCategory().getName());
            tvShoeQuantity.setText(String.valueOf(shoe.getQuantity()));
        }else {
            Toast.makeText(this, "Không tìm thấy thông tin", Toast.LENGTH_SHORT).show();
        }

        loadRating();

        Spinner spinner = findViewById(R.id.spinnerRatingStar);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"1 ⭐", "2 ⭐", "3 ⭐", "4 ⭐", "5 ⭐"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        EditText ratingContent = findViewById(R.id.editTextRatingContent);

        Button buttonCreateRating = findViewById(R.id.buttonCreateRating);
        buttonCreateRating.setOnClickListener(v -> {
            String ratingStar = spinner.getSelectedItem().toString().substring(0,1);
            String content = ratingContent.getText().toString().trim();


            if(shoe!=null || content.isEmpty()){
                Rating rating = new Rating();
                rating.setShoe(shoe);

                DateTimeFormatter formatter = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    rating.setCreatedDate(formatter.format(LocalDate.now()));
                }

                rating.setUser(UserHome.currentUser);
                rating.setStar(Long.parseLong(ratingStar));
                rating.setContent(content);

                if (database.userCreateRating(rating)!=-1){
                    ratingList.add(rating);
                    ratingAdapter.notifyItemInserted(ratingList.size() - 1);

                    ratingContent.setText(null);
                    Toast.makeText(this, "Đánh giá sản phẩm thành công", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Lỗi khi tạo đánh giá", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Lỗi khi tạo đánh giá", Toast.LENGTH_SHORT).show();
            }

        });

        //get button buttonAddToCart to add onclick
        Button buttonAddToCart = findViewById(R.id.buttonAddToCart);
        buttonAddToCart.setOnClickListener(v -> {
            showAddToCartDialog();
        });

        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });


        Button buttonCategory = findViewById(R.id.buttonByCategory);
        buttonCategory.setOnClickListener(v -> {
            startActivity(new Intent(this, CategoryActivity.class));
            finish();
        });

        Button buttonBills = findViewById(R.id.buttonHistory);
        buttonBills.setOnClickListener(v -> {
            Intent intent = new Intent(this, BillsActivity.class);
            startActivity(intent);
            finish();
        });

        Button buttonCart = findViewById(R.id.buttonCart);
        buttonCart.setOnClickListener(v -> {
            startActivity(new Intent(this, CartActivity.class));
            finish();
        });
    }
}
