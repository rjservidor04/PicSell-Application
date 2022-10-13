package com.example.picsellapplication;

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
    private static final String STOREOWNERNAME_COL = "OwnerName";
    private static final String USERNAME_COL = "Username";
    private static final String PASSWORD_COL = "Password";

    //creating constructor for database handler.
    public UserModel(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase userModelDB) {
        //creating an sqlite query and setting column names along with their data types
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

    public boolean checkCredentialsFromDB(String username, String password){
        SQLiteDatabase userModelDB = this.getReadableDatabase();
        Cursor cursor = userModelDB.rawQuery("Select * From Users Where Username = ?", new String[]{username});

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
