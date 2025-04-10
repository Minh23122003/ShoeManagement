package com.minh.shoemanagement.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String NAME_DATABASE = "ShoeDatabase";
    public static final String TABLE_USER = "user";
    public static final String USER_ID = "id";
    public static final String USER_USERNAME = "username";
    public static final String USER_PASSWORD = "password";
    public static final String USER_NAME = "name";
    public static final String USER_ADDRESS = "address";
    public static final String USER_PHONE = "phone";
    public static final String USER_IS_ADMIN = "is_admin";
    public static final String USER_CREATED_DATE = "created_date";
    private static final String CREATE_TABLE_USER = ""
            + "create table " + TABLE_USER + " ( "
            + USER_ID + " integer primary key autoincrement, "
            + USER_USERNAME + " text not null, "
            + USER_PASSWORD + " text not null, "
            + USER_NAME + " text not null, "
            + USER_ADDRESS + " text not null, "
            + USER_PHONE + " text not null, "
            + USER_IS_ADMIN + " integer not null, "
            + USER_CREATED_DATE + " text not null ); ";

    public static final String TABLE_CATEGORY = "category";
    public static final String CATEGORY_ID = "id";
    public static final String CATEGORY_NAME = "name";
    private static final String CREATE_TABLE_CATEGORY = ""
            + "create table " + TABLE_CATEGORY + " ( "
            + CATEGORY_ID + " integer primary key autoincrement, "
            + CATEGORY_NAME + " text not null ); ";

    public static final String TABLE_SHOE = "shoe";
    public static final String SHOE_ID = "id";
    public static final String SHOE_NAME = "name";
    public static final String SHOE_INFORMATION = "information";
    public static final String SHOE_QUANTITY = "quantity";
    public static final String SHOE_CATEGORY_ID = "category_id";
    public static final String SHOE_PRICE = "price";
    public static final String SHOE_IMAGE = "image";
    public static final String SHOE_NOTE = "note";
    private static final String CREATE_TABLE_SHOE = ""
            + "create table " + TABLE_SHOE + " ( "
            + SHOE_ID + " integer primary key autoincrement, "
            + SHOE_NAME + " text not null, "
            + SHOE_INFORMATION + " text not null, "
            + SHOE_QUANTITY + " integer not null, "
            + SHOE_CATEGORY_ID + " integer not null, "
            + SHOE_PRICE + " integer not null, "
            + SHOE_IMAGE + " text not null, "
            + SHOE_NOTE + " text not null, "
            + "FOREIGN KEY(category_id) REFERENCES category(id) ); ";

    public static final String TABLE_BILL = "bill";
    public static final String BILL_ID = "id";
    public static final String BILL_USER_ID = "user_id";
    public static final String BILL_SHOE_ID = "shoe_id";
    public static final String BILL_QUANTITY = "quantity";
    public static final String BILL_SIZE = "size";
    public static final String BILL_CREATED_DATE = "created_date";
    public static final String BILL_PAYMENT_DATE = "payment_date";
    private static final String CREATE_TABLE_BILL = ""
            + "create table " + TABLE_BILL + " ( "
            + BILL_ID + " integer primary key autoincrement, "
            + BILL_USER_ID + " integer not null, "
            + BILL_SHOE_ID + " integer not null, "
            + BILL_QUANTITY + " integer not null, "
            + BILL_SIZE + " integer not null, "
            + BILL_CREATED_DATE + " text not null, "
            + BILL_PAYMENT_DATE + " text not null, "
            + "FOREIGN KEY(user_id) REFERENCES user(id), "
            + "FOREIGN KEY(shoe_id) REFERENCES shoe(id) ); ";

    public static final String TABLE_RATING = "rating";
    public static final String RATING_ID = "id";
    public static final String RATING_STAR = "star";
    public static final String RATING_CONTENT = "content";
    public static final String RATING_USER_ID = "user_id";
    public static final String RATING_SHOE_ID = "shoe_id";
    public static final String RATING_CREATED_DATE = "created_date";
    private static final String CREATE_TABLE_RATING = ""
            + "create table " + TABLE_RATING + " ( "
            + RATING_ID + " integer primary key autoincrement, "
            + RATING_STAR + " integer not null, "
            + RATING_CONTENT + " text not null, "
            + RATING_USER_ID + " integer not null, "
            + RATING_SHOE_ID + " integer not null,"
            + RATING_CREATED_DATE + " text not null, "
            + "FOREIGN KEY(user_id) REFERENCES user(id), "
            + "FOREIGN KEY(shoe_id) REFERENCES shoe(id) ); ";


    public DBHelper(Context context){
        super(context, NAME_DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_SHOE);
        db.execSQL(CREATE_TABLE_BILL);
        db.execSQL(CREATE_TABLE_RATING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
