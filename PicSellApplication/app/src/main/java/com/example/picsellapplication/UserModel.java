package com.example.picsellapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserModel extends SQLiteOpenHelper {
    //initialize database
    private static final String DB_NAME = "PicSell_UsersDB";
    private static final int DB_VERSION = 1;

    //table name
    private static final String TABLENAME = "Users";

    //columns
    private static final String ID_COL = "ID";
    private static final String STORENAME_COL = "StoreName";
    private static final String STOREOWNERNAME_COL = "StoreOwnerName";
    private static final String USERNAME_COL = "Username";
    private static final String PASSWORD_COL = "Password";

    //creating constructor for database handler.
    public UserModel(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase userModelDB) {
        //SQLite query to create a table with column names and their data type
        String query = "CREATE TABLE " + TABLENAME + " (" +
                ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                STORENAME_COL + " TEXT, " +
                STOREOWNERNAME_COL + " TEXT, " +
                USERNAME_COL + " TEXT, " +
                PASSWORD_COL + " TEXT)";

        userModelDB.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase userModelDB, int i, int i1) {
        userModelDB.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(userModelDB);
    }

    //creates a new account
    public void createAccount(String storeName, String storeOwnerName, String username, String password){
        SQLiteDatabase userModelDB = this.getWritableDatabase();
        ContentValues recordValues = new ContentValues();

        //passing all values along with its key
        recordValues.put(STORENAME_COL, storeName);
        recordValues.put(STOREOWNERNAME_COL, storeOwnerName);
        recordValues.put(USERNAME_COL, username);
        recordValues.put(PASSWORD_COL, password);

        //values are then added to the DB
        userModelDB.insert(TABLENAME, null, recordValues);
    }

    //check if the credentials matches from records stored in DB
    public boolean checkCredentialsFromDB(String username, String password){
        SQLiteDatabase userModelDB = this.getReadableDatabase();
        Cursor cursor = userModelDB.rawQuery(
                "Select * From Users Where Username = ? and Password = ?",
                new String[]{username, password});

        if(cursor.getCount() > 0){
            cursor.close();
            return true;
        }

        else{
            cursor.close();
            return  false;
        }
    }

    //checks for StoreName and StoreOwnerName Duplicates
    public boolean checkStoreDuplicateFromDB(String storeName, String storeOwnerName){
        SQLiteDatabase userModelDB = this.getReadableDatabase();
        Cursor cursor = userModelDB.rawQuery(
                "Select * From Users Where StoreName = ? and StoreOwnerName = ?",
                new String[]{storeName, storeOwnerName});

        if(cursor.getCount() > 0){
            cursor.close();
            return true;
        }

        else{
            cursor.close();
            return  false;
        }
    }

    //checks for Username Duplicates
    public boolean checkUsernameDuplicateFromDB(String username){
        SQLiteDatabase userModelDB = this.getReadableDatabase();
        Cursor cursor = userModelDB.rawQuery(
                "Select * From Users Where Username = ?",
                new String[]{username});

        if(cursor.getCount() > 0){
            cursor.close();
            return true;
        }

        else{
            cursor.close();
            return  false;
        }
    }
}
