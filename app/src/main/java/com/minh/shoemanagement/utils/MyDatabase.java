package com.minh.shoemanagement.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.minh.shoemanagement.entities.Category;
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

    public long addCategory(Category category){
        ContentValues values = new ContentValues();
        values.put(DBHelper.CATEGORY_NAME, category.getName());

        return database.insert(DBHelper.TABLE_CATEGORY, null, values);
    }

    public long deleteCategory(Category category){
        return database.delete(DBHelper.TABLE_CATEGORY, DBHelper.CATEGORY_ID + " = " + category.getId(), null);
    }

    public long changeCategory(Category category){
        ContentValues values = new ContentValues();
        values.put(DBHelper.CATEGORY_NAME, category.getName());

        return database.update(DBHelper.TABLE_CATEGORY, values, DBHelper.CATEGORY_ID + " = " + category.getId(), null);
    }

    public Cursor searchCategoryByName(String name){
        return database.query(DBHelper.TABLE_CATEGORY, null,
                DBHelper.CATEGORY_NAME + " LIKE \"%" + name + "%\"", null, null, null, null);
    }

    public Cursor getUsers(){
        return  database.query(DBHelper.TABLE_USER, null, null, null, null, null, null);
    }

    public long addUser(User user){
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

    public long deleteUser(User user){
        return database.delete(DBHelper.TABLE_USER, DBHelper.USER_ID + " = " + user.getId(), null);
    }

    public long changeUser(User user){
        ContentValues values = new ContentValues();
        values.put(DBHelper.USER_USERNAME, user.getUsername());
        values.put(DBHelper.USER_PASSWORD, user.getPassword());
        values.put(DBHelper.USER_NAME, user.getName());
        values.put(DBHelper.USER_ADDRESS, user.getAddress());
        values.put(DBHelper.USER_PHONE, user.getPhone());
        values.put(DBHelper.USER_IS_ADMIN, user.getIsAdmin());
        values.put(DBHelper.USER_CREATED_DATE, user.getCreatedDate());

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
}
