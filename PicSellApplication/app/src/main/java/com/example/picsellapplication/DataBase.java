package com.example.picsellapplication;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
public class DataBase extends SQLiteOpenHelper{
    // creating a constant variables for database.
    private static final String DB_NAME = "picselldb";

    private static final int DB_VERSION = 1;

    // table names
    private static final String TABLE_USER = "users";
    private static final String TABLE_INVENTORY = "inventory";

    // columns
    private static final String ID_COL = "id";

    private static final String STORENAME_COL = "storename";

    private static final String OWNERNAME_COL = "ownername";

    private static final String USERNAME_COL = "username";

    private static final String PASSWORD_COL = "password";

    private static final String PRODUCTNAME_COL = "productname";

    private static final String PRODUCTPRICE_COL = "productprice";

    private static final String STOCK_COL = "stock";

    // creating constructor for database.
    public DataBase(SelectItem context) {super(context, DB_NAME, null, DB_VERSION);}
    public DataBase(InventoryView inventoryView) {super(inventoryView, DB_NAME, null, DB_VERSION);}
    public DataBase(AddInventoryItemView addInventoryItemView) {super(addInventoryItemView, DB_NAME, null, DB_VERSION);}
    public DataBase(RemoveInventoryItemView removeInventoryItemView) {super(removeInventoryItemView,DB_NAME, null, DB_VERSION);}
    public DataBase(UpdateInventoryItemView updateInventoryItemView) {super(updateInventoryItemView,DB_NAME, null, DB_VERSION);}


    // creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating an sqlite query and setting column names along with their data types
        String query = "CREATE TABLE " + TABLE_USER + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + STORENAME_COL + " TEXT,"
                + OWNERNAME_COL + " TEXT,"
                + USERNAME_COL + " TEXT,"
                + PASSWORD_COL + " TEXT)";

        String query1 = "CREATE TABLE " + TABLE_INVENTORY + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PRODUCTNAME_COL + " TEXT,"
                + PRODUCTPRICE_COL + " REAL,"
                + STOCK_COL + " INTEGER)";
        // calling exec sql method to execute above sql query
        db.execSQL(query);
        db.execSQL(query1);
    }

    public void addNewUser(String storeName, String ownerName, String userName, String passWord) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // passing all values along with its key and value pair
        values.put(STORENAME_COL, storeName);
        values.put(OWNERNAME_COL, ownerName);
        values.put(USERNAME_COL, userName);
        values.put(PASSWORD_COL, passWord);

        // after adding all values pass content values to table
        db.insert(TABLE_USER, null, values);
    }

    public void addNewProduct(String productName, double price, int stocks){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(PRODUCTNAME_COL, productName);
        values.put(PRODUCTPRICE_COL, price);
        values.put(STOCK_COL, stocks);

        db.insert(TABLE_INVENTORY, null, values);
    }

    public void updateInventory(String originalProductName, String productName, double productPrice, int productStock) {

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // passing all values along with its key and value pair
        values.put(PRODUCTNAME_COL, productName);
        values.put(PRODUCTPRICE_COL, productPrice);
        values.put(STOCK_COL, productStock);

        // calling update method to update database and passing values and comparing it with name of product which is stored in originalProductName variable
        db.update(TABLE_INVENTORY, values, "productname=?", new String[]{originalProductName});
    }

    public void removeInventory(String productName) {
        SQLiteDatabase db = this.getWritableDatabase();

        // calling a method to delete a product and comparing it with productNname
        db.delete(TABLE_INVENTORY, "productname=?", new String[]{productName});
//        db.close();
    }


    public boolean checkusername(String userName){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username = ?", new String[]{userName});
        if(cursor.getCount()>0) {
            cursor.close();
            return true;
        }
        else {
            cursor.close();
            return false;
        }
    }

    public boolean checkusernamePassword(String userName, String passWord){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=? and password=?", new String[]{userName,passWord});
        if(cursor.getCount()>0) {
            cursor.close();
            return true;
        }
        else {
            cursor.close();
            return false;
        }
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
                        cursorInventory.getInt(3)));
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
