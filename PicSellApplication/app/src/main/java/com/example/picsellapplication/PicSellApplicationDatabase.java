package com.example.picsellapplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
public class PicSellApplicationDatabase extends SQLiteOpenHelper{
    // creating a constant variables for database.
    private static final String DB_NAME = "picselldb";

    private static final int DB_VERSION = 3;

    // table names
    private static final String TABLE_USER = "users";
    private static final String TABLE_INVENTORY = "inventory";

    // columns for Users table
    private static final String USERID_COL = "userID";
    private static final String STORENAME_COL = "storename";
    private static final String USERNAME_COL = "username";
    private static final String PASSWORD_COL = "password";



    // columns for Inventory table
    private static final String PRODUCTID_COL = "productID";
    private static final String PRODUCTNAME_COL = "productname";
    private static final String PRODUCTPRICE_COL = "productprice";
    private static final String STOCKQUANTITY_COL = "stockquantity";
    private static final String CATEGORY_COL = "category";
    private static final String MINIMUMSTOCKQUANTITY_COL = "MinimumStockQuantity";

    // creating constructor for database.
    public PicSellApplicationDatabase(SelectItem context) {super(context, DB_NAME, null, DB_VERSION);}
    public PicSellApplicationDatabase(InventoryView inventoryView) {super(inventoryView, DB_NAME, null, DB_VERSION);}
    public PicSellApplicationDatabase(AddInventoryItemView addInventoryItemView) {super(addInventoryItemView, DB_NAME, null, DB_VERSION);}
    public PicSellApplicationDatabase(RemoveInventoryItemView removeInventoryItemView) {super(removeInventoryItemView,DB_NAME, null, DB_VERSION);}
    public PicSellApplicationDatabase(UpdateInventoryItemView updateInventoryItemView) {super(updateInventoryItemView,DB_NAME, null, DB_VERSION);}
    public PicSellApplicationDatabase(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    // creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating an sqlite query and setting column names along with their data types
        String query = "CREATE TABLE " + TABLE_USER + " ("
                + USERID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + STORENAME_COL + " TEXT,"
                + USERNAME_COL + " TEXT,"
                + PASSWORD_COL + " TEXT)";

        String query1 = "CREATE TABLE " + TABLE_INVENTORY + " ("
                + PRODUCTID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PRODUCTNAME_COL + " TEXT, "
                + PRODUCTPRICE_COL + " REAL, "
                + STOCKQUANTITY_COL + " INTEGER, "
                + CATEGORY_COL + " TEXT, "
                + MINIMUMSTOCKQUANTITY_COL + " INTEGER); ";
        // calling exec sql method to execute above sql query
        db.execSQL(query);
        db.execSQL(query1);
    }

    public void addNewUser(String storeName, String userName, String passWord) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // passing all values along with its key and value pair
        values.put(STORENAME_COL, storeName);
        values.put(USERNAME_COL, userName);
        values.put(PASSWORD_COL, passWord);

        // after adding all values pass content values to table
        db.insert(TABLE_USER, null, values);
    }
    public ArrayList<UserModel> readUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorUser = db.rawQuery("SELECT * FROM " + TABLE_USER, null);
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
        db.update(TABLE_USER, values, "Username?", new String[] {originalUsername});
        db.close();
    }

    public void deleteUser(String username){
        SQLiteDatabase db = this.getWritableDatabase();

        //delete records with condition that the record's UserID matched the provided UserID
        db.delete(TABLE_USER, "Username", new String[] {username});
        db.close();
    }

    public void addNewProduct(String productName, double price, int stocks,int minimumStock, String category){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(PRODUCTNAME_COL, productName);
        values.put(PRODUCTPRICE_COL, price);
        values.put(STOCKQUANTITY_COL, stocks);
        values.put(MINIMUMSTOCKQUANTITY_COL, minimumStock);
        values.put(CATEGORY_COL, category);

        db.insert(TABLE_INVENTORY, null, values);
    }

    public void updateInventory(String originalProductName, String productName, double productPrice, int productStock) {

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // passing all values along with its key and value pair
        values.put(PRODUCTNAME_COL, productName);
        values.put(PRODUCTPRICE_COL, productPrice);
        values.put(STOCKQUANTITY_COL, productStock);

        // calling update method to update database and passing values and comparing it with name of product which is stored in originalProductName variable
        db.update(TABLE_INVENTORY, values, "productname=?", new String[]{originalProductName});
    }

    public void removeInventory(String productName) {
        SQLiteDatabase db = this.getWritableDatabase();

        // calling a method to delete a product and comparing it with productNname
        db.delete(TABLE_INVENTORY, "productname=?", new String[]{productName});
//        db.close();
    }


    public boolean checkproduct(String productName){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from inventory where productname=?", new String[]{productName});
        if(cursor.getCount()>0) {
            cursor.close();
            return true;
        }
        else {
            cursor.close();
            return false;
        }
    }

    public ArrayList<InventoryModel> readInventory() {
        SQLiteDatabase db = this.getReadableDatabase();

        // creating a cursor with query to read data from database.
        Cursor cursorInventory = db.rawQuery("SELECT * FROM " + TABLE_INVENTORY, null);

        // creating a new array list.
        ArrayList<InventoryModel> inventoryModalArrayList = new ArrayList<>();

        // moving cursor to first position.
        if (cursorInventory.moveToFirst()) {
            do {
                // adding the data from cursor to array list.
                inventoryModalArrayList.add(new InventoryModel(cursorInventory.getInt(0),
                        cursorInventory.getString(1),
                        cursorInventory.getDouble(2),
                        cursorInventory.getInt(3),
                        cursorInventory.getString(4),
                        cursorInventory.getInt(5)));
            } while (cursorInventory.moveToNext());
            // moving cursor to next.
        }
        // at last closing cursor and returning array list
        cursorInventory.close();
        return inventoryModalArrayList;
    }

    public ArrayList<InventoryModel> readCat1() {
        SQLiteDatabase db = this.getReadableDatabase();

        // creating a cursor with query to read data from database.
        Cursor cursorInventory = db.rawQuery("Select * from inventory where category = ?" , new String[]{"Candies"});

        // creating a new array list.
        ArrayList<InventoryModel> inventoryModalArrayList = new ArrayList<>();

        // moving cursor to first position.
        if (cursorInventory.moveToFirst()) {
            do {
                // adding the data from cursor to array list.
                inventoryModalArrayList.add(new InventoryModel(cursorInventory.getInt(0),
                        cursorInventory.getString(1),
                        cursorInventory.getDouble(2),
                        cursorInventory.getInt(3),
                        cursorInventory.getString(4),
                        cursorInventory.getInt(5)));
            } while (cursorInventory.moveToNext());
            // moving cursor to next.
        }
        // at last closing cursor and returning array list
        cursorInventory.close();
        return inventoryModalArrayList;
    }
    public ArrayList<InventoryModel> readCat2() {
        SQLiteDatabase db = this.getReadableDatabase();

        // creating a cursor with query to read data from database.
        Cursor cursorInventory = db.rawQuery("select * from inventory where category=?" , new String[]{"Junk Foods"});

        // creating a new array list.
        ArrayList<InventoryModel> inventoryModalArrayList = new ArrayList<>();

        // moving cursor to first position.
        if (cursorInventory.moveToFirst()) {
            do {
                // adding the data from cursor to array list.
                inventoryModalArrayList.add(new InventoryModel(cursorInventory.getInt(0),
                        cursorInventory.getString(1),
                        cursorInventory.getDouble(2),
                        cursorInventory.getInt(3),
                        cursorInventory.getString(4),
                        cursorInventory.getInt(5)));
            } while (cursorInventory.moveToNext());
            // moving cursor to next.
        }
        // at last closing cursor and returning array list
        cursorInventory.close();
        return inventoryModalArrayList;
    }
    public ArrayList<InventoryModel> readCat3() {
        SQLiteDatabase db = this.getReadableDatabase();

        // creating a cursor with query to read data from database.
        Cursor cursorInventory = db.rawQuery("select * from inventory where category=?" , new String[]{"Beverages"});

        // creating a new array list.
        ArrayList<InventoryModel> inventoryModalArrayList = new ArrayList<>();

        // moving cursor to first position.
        if (cursorInventory.moveToFirst()) {
            do {
                // adding the data from cursor to array list.
                inventoryModalArrayList.add(new InventoryModel(cursorInventory.getInt(0),
                        cursorInventory.getString(1),
                        cursorInventory.getDouble(2),
                        cursorInventory.getInt(3),
                        cursorInventory.getString(4),
                        cursorInventory.getInt(5)));
            } while (cursorInventory.moveToNext());
            // moving cursor to next.
        }
        // at last closing cursor and returning array list
        cursorInventory.close();
        return inventoryModalArrayList;
    }
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVENTORY);
        onCreate(db);
    }
}
