package com.example.picsellapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class PicSellApplicationDatabase extends SQLiteOpenHelper{
    // creating a constant variables for database.
    private static final String DB_NAME = "PicSellApplicationDatabase";
    private static final int DB_VERSION = 1;

    // table names
    private static final String TABLE_USERS = "Users";
    private static final String TABLE_INVENTORY = "Inventory";

    // columns for Users table
    private static final String USERID_COL = "UserID";
    private static final String STORENAME_COL = "StoreName";
    private static final String USERNAME_COL = "Username";
    private static final String PASSWORD_COL = "Password";

    // columns for Inventory table
    private static final String PRODUCTID_COL = "ProductID";
    private static final String PRODUCTNAME_COL = "ProductName";
    private static final String PRODUCTPRICE_COL = "ProductPrice";
    private static final String STOCKQUANTITY_COL = "StockQuantity";
    private static final String MINIMUMSTOCKQUANTITY = "MinimumStockQuantity";
    private static final String CATEGORY_COL = "Category";

    // constructor for database
    public PicSellApplicationDatabase(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating an sqlite query for creating tables and setting column names along with their data types
        String createUserTable = "CREATE TABLE " + TABLE_USERS + " ("
                + USERID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + STORENAME_COL + " TEXT,"
                + USERNAME_COL + " TEXT,"
                + PASSWORD_COL + " TEXT)";

        String createProductTable = "CREATE TABLE " + TABLE_INVENTORY + " ("
                + PRODUCTID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PRODUCTNAME_COL + " TEXT,"
                + PRODUCTPRICE_COL + " REAL,"
                + STOCKQUANTITY_COL + " INTEGER,"
                + MINIMUMSTOCKQUANTITY + "INTEGER,"
                + CATEGORY_COL + " TEXT)";

        // calling exec sql method to execute above sql query
        db.execSQL(createUserTable);
        db.execSQL(createProductTable);
    }

    public void addNewUser(String storeName, String username, String passWord) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(STORENAME_COL, storeName);
        values.put(USERNAME_COL, username);
        values.put(PASSWORD_COL, passWord);

        // inset values to the Users table
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public ArrayList<UserModel> readUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorUser = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
        ArrayList<UserModel> userModels = new ArrayList<>();

        if (cursorUser.moveToFirst()) {
            do {
                // adding the data from cursor to array list.
                userModels.add(new UserModel(
                        cursorUser.getString(0),
                        cursorUser.getString(1),
                        cursorUser.getString(2)));
            } while (cursorUser.moveToNext());
        }

        cursorUser.close();
        return userModels;
    }

    // for login, check credentials
    public boolean verifyCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * From Users Where Username = ? and Password = ?",
                new String[]{username, password});

        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }

        else {
            cursor.close();
            return false;
        }
    }

    //for registration, check duplicates
    public boolean checkForDuplicates(String storeName, String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * From Users Where StoreName = ? and Username = ?",
                new String[]{storeName, username});

        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }

        else {
            cursor.close();
            return false;
        }
    }

    public void updateUser(String originalUsername, String storeName, String userName, String passWord){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(STORENAME_COL, storeName);
        values.put(USERNAME_COL, userName);
        values.put(PASSWORD_COL, passWord);

        // update values with condition that UserID must match the provided UserID
        db.update(TABLE_USERS, values, "Username?", new String[] {originalUsername});
        db.close();
    }

    public void deleteUser(String username){
        SQLiteDatabase db = this.getWritableDatabase();

        //delete records with condition that the record's UserID matched the provided UserID
        db.delete(TABLE_USERS, "Username", new String[] {username});
        db.close();
    }

    public void addNewProduct(String productName, double price, int stockQuantity, int minimumStock, String category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PRODUCTNAME_COL, productName);
        values.put(PRODUCTPRICE_COL, price);
        values.put(STOCKQUANTITY_COL, stockQuantity);
        values.put(MINIMUMSTOCKQUANTITY, minimumStock);
        values.put(CATEGORY_COL, category);

        // insert values to the Inventory table
        db.insert(TABLE_INVENTORY, null, values);
        db.close();
    }

    public void updateInventory(String originalProductName, String productName, double productPrice, int stockQuantity, int minimumStock, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PRODUCTNAME_COL, productName);
        values.put(PRODUCTPRICE_COL, productPrice);
        values.put(STOCKQUANTITY_COL, stockQuantity);
        values.put(MINIMUMSTOCKQUANTITY, minimumStock);
        values.put(CATEGORY_COL, category);

        // update values and comparing it with name of product which is stored in originalProductName variable
        db.update(TABLE_INVENTORY, values, "ProductName=?", new String[]{originalProductName});
    }

    public void removeInventory(String productName) {
        SQLiteDatabase db = this.getWritableDatabase();

        // calling a method to delete a product and comparing it with product name
        db.delete(TABLE_INVENTORY, "ProductName=?", new String[]{productName});
        db.close();
    }


//    public ArrayList<InventoryModel> readInventory() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursorInventory = db.rawQuery("SELECT * FROM " + TABLE_INVENTORY, null);
//        ArrayList<InventoryModel> inventoryModalArrayList = new ArrayList<>();
//
//        // read the cursor and add them to the array list
//        if (cursorInventory.moveToFirst()) {
//            do {
//                inventoryModalArrayList.add(new InventoryModel(cursorInventory.getString(0),
//                        cursorInventory.getDouble(1),
//                        cursorInventory.getInt(2),
//                        cursorInventory.getInt(3),
//                        cursorInventory.getString(4)));
//            } while (cursorInventory.moveToNext());
//        }
//
//        cursorInventory.close();
//        return inventoryModalArrayList;
//    }
//
//    public ArrayList<InventoryModel> readInventoryByCategory(String category) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursorInventory = db.rawQuery("Select * from inventory where Category = ?" , new String[] {category});
//        ArrayList<InventoryModel> inventoryModalArrayList = new ArrayList<>();
//
//        if (cursorInventory.moveToFirst()) {
//            do {
//                inventoryModalArrayList.add(new InventoryModel(cursorInventory.getString(0),
//                        cursorInventory.getDouble(1),
//                        cursorInventory.getInt(2),
//                        cursorInventory.getInt(3),
//                        cursorInventory.getString(4)));
//            } while (cursorInventory.moveToNext());
//        }
//
//        cursorInventory.close();
//        return inventoryModalArrayList;
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVENTORY);
        onCreate(db);
    }
}