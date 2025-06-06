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

    public long addCategory(Category category){
        ContentValues values = new ContentValues();
        values.put(DBHelper.CATEGORY_NAME, category.getName());

        return database.insert(DBHelper.TABLE_CATEGORY, null, values);
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

    public Cursor getUserById (long userId){
        String selection = DBHelper.USER_ID + " =?";
        String[] selectionArgs = {String.valueOf(userId)};

        return database.query(DBHelper.TABLE_USER, null, selection, selectionArgs, null, null, null);
    }

    //method: get List Shoe by Category
    public Cursor getListShoeByCategory(Category category, String shoeName) {
        String selection = DBHelper.SHOE_CATEGORY_ID + " = ? AND " + DBHelper.SHOE_NAME + " LIKE ?";
        String[] selectionArgs = {String.valueOf(category.getId()), "%"+shoeName+"%"};

        return database.query(DBHelper.TABLE_SHOE, null, selection, selectionArgs, null, null, null);
    }

    //get Shoe by id
    public Cursor getShoeById(long id) {
        String selection = DBHelper.SHOE_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        return database.query(DBHelper.TABLE_SHOE, null, selection, selectionArgs, null, null, null);
    }

    //get Shoe newest shoe
    public Cursor getTopNewShoe (){
        return database.query(DBHelper.TABLE_SHOE, null, null, null, null, null, DBHelper.SHOE_ID + " DESC");
    }

    //get top seller shoe
    public Cursor getBestSellerShoe (){

        String sql = "SELECT " + DBHelper.BILL_SHOE_ID + ", SUM(" + DBHelper.BILL_QUANTITY + ") as total_quantity  "
                + "FROM " + DBHelper.TABLE_BILL + " "
                + "WHERE " + DBHelper.BILL_PAYMENT_DATE + " != 'pending' "
                + "GROUP BY " + DBHelper.BILL_SHOE_ID + " "
                + "ORDER BY total_quantity DESC";

        return database.rawQuery(sql, null);
    }

    //get Cate by id
    public Cursor getCategoryById(long id) {
        String selection = DBHelper.CATEGORY_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        return database.query(DBHelper.TABLE_CATEGORY, null, selection, selectionArgs, null, null, null);
    }


    //get bill by user_id order by created date
    public Cursor getBillByUserId(long userId) {
        String selection = DBHelper.BILL_USER_ID + " = ? and " + DBHelper.BILL_PAYMENT_DATE + " != ?";
        String[] selectionArgs = {String.valueOf(userId), "pending"};

        return database.query(DBHelper.TABLE_BILL, null, selection, selectionArgs, null, null, DBHelper.BILL_CREATED_DATE);
    }

    //get cart item (bill.paymentDate = "pending") by userid
    //Lấy các Bill chưa thanh toán được đặt trong giỏ hàng của người dùng
    public Cursor getCartItem(long userId) {
        String selection = DBHelper.BILL_USER_ID + " = ? and " + DBHelper.BILL_PAYMENT_DATE + " = ?";
        String[] selectionArgs = {String.valueOf(userId), "pending"};

        return database.query(DBHelper.TABLE_BILL, null, selection, selectionArgs, null, null, DBHelper.BILL_CREATED_DATE);
    }

    //phương thức tạo Bill (User thêm vào giỏ hàng set PaymentDate là pending)
    public long userAddToCart(Bill bill) {

        ContentValues values = new ContentValues();
        values.put(DBHelper.BILL_USER_ID, bill.getUser().getId());
        values.put(DBHelper.BILL_SHOE_ID, bill.getShoe().getId());
        values.put(DBHelper.BILL_QUANTITY, bill.getQuantity());
        values.put(DBHelper.BILL_SIZE, bill.getSize());
        values.put(DBHelper.BILL_CREATED_DATE, bill.getCreatedDate());
        values.put(DBHelper.BILL_PAYMENT_DATE, bill.getPaymentDate());

        return database.insert(DBHelper.TABLE_BILL, null, values);
    }

    //phương thức xử lý thanh toán
    public long userPaymentProcess (Bill bill){
        ContentValues values = new ContentValues();
        values.put(DBHelper.BILL_USER_ID, bill.getUser().getId());
        values.put(DBHelper.BILL_SHOE_ID, bill.getShoe().getId());
        values.put(DBHelper.BILL_QUANTITY, bill.getQuantity());
        values.put(DBHelper.BILL_SIZE, bill.getSize());
        values.put(DBHelper.BILL_CREATED_DATE, bill.getCreatedDate());
        values.put(DBHelper.BILL_PAYMENT_DATE, bill.getPaymentDate());

        String selection = DBHelper.BILL_ID + " =?";
        String[] selectionArgs = {String.valueOf(bill.getId())};

        return database.update(DBHelper.TABLE_BILL, values, selection, selectionArgs);
    }

    //phương thức user xóa item khỏi cart
    public long userDeleteCartItem (Bill bill){

        String selection = DBHelper.BILL_ID + " =?";
        String[] selectionArgs = {String.valueOf(bill.getId())};

        return database.delete(DBHelper.TABLE_BILL, selection, selectionArgs);
    }

    //phương thức lấy Rating theo ShoeId và giảm dần id
    public Cursor getRatingByShoeId (long shoeId){
        String selection = DBHelper.RATING_SHOE_ID + " = ?";
        String[] selectionArgs = {String.valueOf(shoeId)};

        return database.query(DBHelper.TABLE_RATING, null, selection, selectionArgs, null, null, DBHelper.RATING_ID + " DESC");
    }

    //phương thức khách hàng bình luận dưới sản phẩm
    public long userCreateRating (Rating rating){
        ContentValues values = new ContentValues();
        values.put(DBHelper.RATING_STAR, rating.getStar());
        values.put(DBHelper.RATING_CONTENT, rating.getContent());
        values.put(DBHelper.RATING_SHOE_ID, rating.getShoe().getId());
        values.put(DBHelper.RATING_USER_ID, rating.getUser().getId());
        values.put(DBHelper.RATING_CREATED_DATE, rating.getCreatedDate());

        return database.insert(DBHelper.TABLE_RATING, null, values);
    }


}
