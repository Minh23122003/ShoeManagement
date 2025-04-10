package com.minh.shoemanagement.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.minh.shoemanagement.entities.Bill;
import com.minh.shoemanagement.entities.Category;
import com.minh.shoemanagement.entities.Rating;
import com.minh.shoemanagement.entities.Shoe;
import com.minh.shoemanagement.entities.User;

public class MyDatabase {
    SQLiteDatabase database;
    DBHelper helper;

    public MyDatabase(Context context){
        helper = new DBHelper(context);
        database = helper.getWritableDatabase();
    }

    public Cursor getCategories(){
        return database.query(DBHelper.TABLE_CATEGORY, null, null, null, null, null, null);
    }

    public long insertCategory(Category category){
        ContentValues values = new ContentValues();
        values.put(DBHelper.CATEGORY_NAME, category.getName());

        return database.insert(DBHelper.TABLE_CATEGORY, null, values);
    }

    public long deleteCategory(long id){
        return database.delete(DBHelper.TABLE_CATEGORY, DBHelper.CATEGORY_ID + " = " + id, null);
    }

    public long updateCategory(Category category){
        ContentValues values = new ContentValues();
        values.put(DBHelper.CATEGORY_NAME, category.getName());

        return database.update(DBHelper.TABLE_CATEGORY, values, DBHelper.CATEGORY_ID + " = " + category.getId(), null);
    }

    public Cursor searchCategoryByName(String name){
        return database.query(DBHelper.TABLE_CATEGORY, null,
                DBHelper.CATEGORY_NAME + " LIKE \"%" + name + "%\"", null, null, null, null);
    }

    public Cursor searchCategoryById(long id){
        return database.query(DBHelper.TABLE_CATEGORY, null,
                DBHelper.SHOE_ID + " = "+ id, null, null, null, null);
    }

    public Cursor getUsers(){
        return  database.query(DBHelper.TABLE_USER, null, null, null, null, null, null);
    }

    public long insertUser(User user){
        ContentValues values = new ContentValues();
        values.put(DBHelper.USER_USERNAME, user.getUsername());
        values.put(DBHelper.USER_PASSWORD, user.getPassword());
        values.put(DBHelper.USER_NAME, user.getName());
        values.put(DBHelper.USER_ADDRESS, user.getAddress());
        values.put(DBHelper.USER_PHONE, user.getPhone());
        values.put(DBHelper.USER_IS_ADMIN, user.getIsAdmin());
        values.put(DBHelper.USER_CREATED_DATE, user.getCreatedDate());

        return database.insert(DBHelper.TABLE_USER, null, values);
    }

    public long deleteUser(long id){
        return database.delete(DBHelper.TABLE_USER, DBHelper.USER_ID + " = " + id, null);
    }

    public long updateUser(User user){
        ContentValues values = new ContentValues();
        values.put(DBHelper.USER_USERNAME, user.getUsername());
        values.put(DBHelper.USER_PASSWORD, user.getPassword());
        values.put(DBHelper.USER_NAME, user.getName());
        values.put(DBHelper.USER_ADDRESS, user.getAddress());
        values.put(DBHelper.USER_PHONE, user.getPhone());
        values.put(DBHelper.USER_IS_ADMIN, user.getIsAdmin());

        return database.update(DBHelper.TABLE_USER, values, DBHelper.USER_ID + " = " + user.getId(), null);
    }

    public Cursor searchUserByUsername(String username){
        return database.query(DBHelper.TABLE_USER, null,
                DBHelper.USER_USERNAME + " = '" + username + "'", null, null, null, null);
    }

    public Cursor searchUserByUsernameAndPassword(String username, String password){
        return database.query(DBHelper.TABLE_USER, null,
                DBHelper.USER_USERNAME + " = '" + username + "'" + " AND " + DBHelper.USER_PASSWORD + " = '" + password + "'",
                null, null, null, null);
    }

    public Cursor getShoes(){
        return database.query(DBHelper.TABLE_SHOE, null, null, null, null, null, null);
    }

    public long insertShoe(Shoe shoe){
        ContentValues  values = new ContentValues();
        values.put(DBHelper.SHOE_NAME, shoe.getName());
        values.put(DBHelper.SHOE_INFORMATION, shoe.getInformation());
        values.put(DBHelper.SHOE_QUANTITY, shoe.getQuantity());
        values.put(DBHelper.SHOE_CATEGORY_ID, shoe.getCategory().getId());
        values.put(DBHelper.SHOE_PRICE, shoe.getPrice());
        values.put(DBHelper.SHOE_IMAGE, shoe.getImage());
        values.put(DBHelper.SHOE_NOTE, shoe.getNote());

        return database.insert(DBHelper.TABLE_SHOE, null, values);
    }

    public long deleteShoe(long id){
        return database.delete(DBHelper.TABLE_SHOE, DBHelper.SHOE_ID + " = " + id, null);
    }

    public long updateShoe(Shoe shoe){
        ContentValues  values = new ContentValues();
        values.put(DBHelper.SHOE_NAME, shoe.getName());
        values.put(DBHelper.SHOE_INFORMATION, shoe.getInformation());
        values.put(DBHelper.SHOE_QUANTITY, shoe.getQuantity());
        values.put(DBHelper.SHOE_CATEGORY_ID, shoe.getCategory().getId());
        values.put(DBHelper.SHOE_PRICE, shoe.getPrice());
        values.put(DBHelper.SHOE_IMAGE, shoe.getImage());
        values.put(DBHelper.SHOE_NOTE, shoe.getNote());

        return database.update(DBHelper.TABLE_SHOE, values, DBHelper.SHOE_ID + " = " + shoe.getId(), null);
    }

    public Cursor getBills(){
        return database.query(DBHelper.TABLE_BILL, null, null, null, null, null, null);
    }

    public long insertBill(Bill bill){
        ContentValues values = new ContentValues();
        values.put(DBHelper.BILL_USER_ID, bill.getUser().getId());
        values.put(DBHelper.BILL_SHOE_ID, bill.getShoe().getId());
        values.put(DBHelper.BILL_QUANTITY, bill.getQuantity());
        values.put(DBHelper.BILL_SIZE, bill.getSize());
        values.put(DBHelper.BILL_CREATED_DATE, bill.getCreatedDate());
        values.put(DBHelper.BILL_PAYMENT_DATE, bill.getPaymentDate());

        return database.insert(DBHelper.TABLE_BILL, null, values);
    }

    public Cursor getRatings(){
        return database.query(DBHelper.TABLE_RATING, null, null, null, null, null, null);
    }

    public long insertRating(Rating rating){
        ContentValues values = new ContentValues();
        values.put(DBHelper.RATING_STAR, rating.getStar());
        values.put(DBHelper.RATING_CONTENT, rating.getContent());
        values.put(DBHelper.RATING_USER_ID, rating.getUser().getId());
        values.put(DBHelper.RATING_SHOE_ID, rating.getShoe().getId());
        values.put(DBHelper.RATING_CREATED_DATE, rating.getCreatedDate());

        return database.insert(DBHelper.TABLE_RATING, null, values);
    }

    public Cursor statsByUser(){
        return database.rawQuery("select user.id, user.username, count(*) from bill inner join user where bill.user_id = user.id group by user.id ", null);
    }

    public Cursor statsByCategory(){
        return database.rawQuery("select c.id, c.name, count(*) from bill b, shoe s, category c where b.shoe_id = s.id and s.category_id = c.id group by c.id", null);
    }
}
