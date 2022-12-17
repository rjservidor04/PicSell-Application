package com.example.picsellapplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class PicSellApplicationDatabase extends SQLiteOpenHelper{
    // creating a constant variables for database.
    private static final String DB_NAME = "PicSellDatabase";

    private static final int DB_VERSION = 3;

    // table names
    private static final String TABLE_USER = "Users";
    private static final String TABLE_INVENTORY = "Inventory";
    private static final String TABLE_ITEM = "Item";
    private static final String TABLE_SALES = "Sales";

    // columns for Users table
    private static final String USERID_COL = "userId";
    private static final String STORENAME_COL = "storename";
    private static final String USERNAME_COL = "username";
    private static final String PASSWORD_COL = "password";

    // columns for Item table
    private static final String ITEMID_COL = "itemId";
    private static final String ITEMNAME_COL = "itemName";
    private static final String PRICE_COL = "price";
    private static final String COST_COL = "cost";


    // columns for Sales table
    private static final String SALESID_COL = "salesId";
    private static final String QUANTITY_COL = "quantity";
    private static final String DATESOLD_COL = "dateSold";

    // columns for Inventory table

    // inventory table should just have a foreign key that is from Item (please refer to its attributes)
    // also naming convention should be item, not product.
    // will also remove the implementation of category.

    private static final String INVENTORYID_COL = "inventoryId";
    private static final String STOCKQUANTITY_COL = "stockquantity";
    private static final String MINIMUMSTOCKQUANTITY_COL = "minimumStockQuantity";


    // creating constructor for database.
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

        String createInventoryTable = "CREATE TABLE " + TABLE_INVENTORY + " ("
                + INVENTORYID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + STOCKQUANTITY_COL + " INTEGER, "
                + MINIMUMSTOCKQUANTITY_COL + " INTEGER, "
                + ITEMID_COL + " INTEGER, FOREIGN KEY (" + ITEMID_COL + ") REFERENCES "
                + TABLE_ITEM + "(" + ITEMID_COL + ") )";

        String createItemTable = "CREATE TABLE " + TABLE_ITEM + " ("
                + ITEMID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ITEMNAME_COL + " TEXT, "
                + COST_COL + " REAL, "
                + PRICE_COL + " REAL)";

        String createSalesTable = "CREATE TABLE " + TABLE_SALES + " ("
                + SALESID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ITEMNAME_COL + " TEXT, "
                + QUANTITY_COL + " INTEGER, "
                + DATESOLD_COL + " INTEGER, "
                + COST_COL + " REAL, "
                + PRICE_COL + " REAL)";
        // calling exec sql method to execute above sql query

        db.execSQL(query);
        db.execSQL(createInventoryTable);
        db.execSQL(createItemTable);
        db.execSQL(createSalesTable);
    }

    // USER DATABASE QUERIES

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
        Cursor cursor = db.rawQuery("Select * From Users Where username = ? and password = ?",
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

    public void changeUser(String originalUsername, String storeName, String userName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(STORENAME_COL, storeName);
        values.put(USERNAME_COL, userName);

        // update values with condition that UserID must match the provided UserID
        db.update(TABLE_USER, values, "Username = ?", new String[] {originalUsername});
//        db.close();
    }

    public void changePass(String originalUsername, String newPassword){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PASSWORD_COL, newPassword);

        // update values with condition that UserID must match the provided UserID
        db.update(TABLE_USER, values, "Username = ?", new String[] {originalUsername});
//        db.close();
    }

    public UserModel getUser(String uName){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from Users where Username = ?";
//        System.out.println("NANA KO DIRI");
        Cursor cursor = db.rawQuery(sql, new String[]{uName});

        UserModel temp = new UserModel();
        if(cursor.moveToFirst()){
            temp.setStoreName(cursor.getString(1));
            temp.setUsername(cursor.getString(2));
            temp.setPassword(cursor.getString(3));
        }

        cursor.close();
        return temp;
    }

    public void updateUser(String originalUsername, String storeName, String userName, String passWord){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(STORENAME_COL, storeName);
        values.put(USERNAME_COL, userName);
        values.put(PASSWORD_COL, passWord);

        // update values with condition that UserID must match the provided UserID
        db.update(TABLE_USER, values, "username=?", new String[] {originalUsername});
        db.close();
    }

    public void deleteUser(String username){
        SQLiteDatabase db = this.getWritableDatabase();

        //delete records with condition that the record's UserID matched the provided UserID
        db.delete(TABLE_USER, "username", new String[] {username});
        db.close();
    }

    //for changing profile details (username duplicate check)
    public boolean checkUsername(String uName){
        // System.out.println("CHECKING WORKS");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * From Users Where Username = ?",
                new String[]{uName});
        if(cursor.getCount()>0) {
            cursor.close();
            return true;
        }
        else {
            cursor.close();
            return false;
        }
    }

    // ITEM DATABASE QUERIES

    public String addNewItem(ItemModel item) {
        Boolean isUnique = isItemUnique(item.getItemName());

        if(!isUnique)
            return "Item already exists.";

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ITEMNAME_COL, item.getItemName());
        values.put(COST_COL, item.getCost());
        values.put(PRICE_COL, item.getPrice());

        // inset values to the Users table
        long result =  db.insert(TABLE_ITEM, null, values);
        db.close();

        if(result == -1) // error happened during insertion
            return "Error during insertion";

        return "Insertion was a success.";
    }
    public boolean isItemUnique(String itemName){

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_ITEM + " WHERE " + ITEMNAME_COL + " = '" + itemName + "'";
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            cursor.close();
            return false;
        }
        return true;
    }
    public ItemModel getItem(int itemId){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_ITEM + " WHERE " + ITEMID_COL + " = " + itemId;
        Cursor cursor = db.rawQuery(sql, null);
        ItemModel temp = new ItemModel();
        if(cursor.moveToFirst()){
            temp.setItemId(cursor.getInt(0));
            temp.setItemName(cursor.getString(1));
            temp.setCost(cursor.getDouble(2));
            temp.setPrice(cursor.getDouble(3));
        }
        cursor.close();
        return temp;
    }

    public ItemModel getItem(String itemName){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_ITEM + " WHERE " + ITEMNAME_COL + " = '" + itemName + "'";
        Cursor cursor = db.rawQuery(sql, null);
        ItemModel temp = new ItemModel();
        if(cursor.moveToFirst()){
            temp.setItemId(cursor.getInt(0));
            temp.setItemName(cursor.getString(1));
            temp.setCost(cursor.getDouble(2));
            temp.setPrice(cursor.getDouble(3));
        }
        cursor.close();
        return temp;
    }

    public int getItemId(String itemName){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT itemId FROM " + TABLE_ITEM + " WHERE " + ITEMNAME_COL + " = '" + itemName + "'";
        Cursor cursor = db.rawQuery(sql, null);


        if(cursor.moveToFirst()){
            int id = cursor.getInt(0);
            cursor.close();
            return id;
        }
        return -1;
    }

    // INVENTORY DATABASE QUERIES
    public boolean addItemToInventory(InventoryModel inventory){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        long result2 = -1;

        values.put(STOCKQUANTITY_COL, inventory.getStockQuantity());
        values.put(MINIMUMSTOCKQUANTITY_COL, inventory.getMinimumStockQuantity());
        values.put(ITEMID_COL, inventory.getItemId());

        result2 = db.insert(TABLE_INVENTORY, null, values);
        db.close();

        if(result2 == -1)
            return false;

        return true;
    }

    public List<InventoryModel> getInventoryItems(){
        List<InventoryModel> inventoryList = new ArrayList<InventoryModel>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Inventory", null);

        if(cursor.moveToFirst()){
            do{
                ItemModel item = getItem(cursor.getInt(3));
                InventoryModel temp = new InventoryModel();

                temp.setItem(item);
                temp.setStockQuantity(cursor.getInt(1));
                temp.setMinimumStockQuantity(cursor.getInt(2));
                temp.setItemId(cursor.getInt(3));

                inventoryList.add(temp);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return inventoryList;
    }
    public void updateInventory(InventoryModel inventory) {

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT itemId FROM " + TABLE_INVENTORY + " WHERE " + ITEMID_COL + " = '" + inventory.getItemId() + "'";
        ContentValues values = new ContentValues();
        // passing all values along with its key and value pair
        values.put(STOCKQUANTITY_COL, inventory.getStockQuantity());
        values.put(MINIMUMSTOCKQUANTITY_COL, inventory.getMinimumStockQuantity());


        // calling update method to update database and passing values and comparing it with name of product which is stored in originalProductName variable
        db.update(TABLE_INVENTORY, values, "itemId = " + inventory.getItemId(),null);
    }
    public void ReStock(double stock,int id) {
        // calling a method to get writable database.
        SQLiteDatabase read = this.getReadableDatabase();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = read.rawQuery("SELECT stockQuantity FROM Inventory WHERE itemId = "+ id, null);
        double result = 0;
        if (cursor.moveToFirst()) {
            double dbval = cursor.getInt(0);
            result = stock + dbval;
        }
        ContentValues values = new ContentValues();
        // passing all values along with its key and value pair
        values.put(STOCKQUANTITY_COL, result);
        // calling update method to update database and passing values and comparing it with name of product which is stored in originalProductName variable
        db.update(TABLE_INVENTORY, values, "itemId = " + id, null);
    }

    public void updateItem(ItemModel item) {
        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT itemName FROM " + TABLE_ITEM + " WHERE " + ITEMNAME_COL + " = '" + item.getItemName() + "'";
        ContentValues values = new ContentValues();
        // passing all values along with its key and value pair
        values.put(PRICE_COL,item.getPrice());
        values.put(COST_COL,item.getCost());

        // calling update method to update database and passing values and comparing it with name of product which is stored in originalProductName variable
        db.update(TABLE_ITEM, values, "itemName=?", new String[]{item.getItemName()});
    }
    public void removeInventory(InventoryModel inventory) {
        SQLiteDatabase db = this.getWritableDatabase();
        // calling a method to delete a product and comparing it with productNname
        db.delete(TABLE_INVENTORY,"itemId = " + inventory.getItemId(),null);
        //db.close();
    }

    public void removeItem(ItemModel item) {
        SQLiteDatabase db = this.getWritableDatabase();
        // calling a method to delete a product and comparing it with productNname
        db.delete(TABLE_ITEM, "itemName=?", new String[]{item.getItemName()});
        //db.close();
    }

    public boolean checkProduct(String productName){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Inventory where productname=?", new String[]{productName});
        if(cursor.getCount()>0) {
            cursor.close();
            return true;
        }
        else {
            cursor.close();
            return false;
        }
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    // SALES DATABASE QUERIES
    public Cursor getSoldItems(int startDate, int endDate){

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT ItemName, Quantity, Cost, Price FROM Sales WHERE" +
                " DateSold >= " + startDate + " AND" +
                " DateSold <= " + endDate;

        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }

    public String addItemToSales(SalesModel sales){
        SQLiteDatabase read = this.getReadableDatabase();
        SQLiteDatabase write = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        long result = -1;
        String returnMsg = "";

        int thisItemId = getItemId(sales.getItemName());

        // check if stock > than the checkout quantity of the item
        Cursor inventoryCursor = read.rawQuery("SELECT stockQuantity, minimumStockQuantity FROM Inventory WHERE itemId = " + thisItemId, null);
        int stock = -1;

        if(inventoryCursor.moveToFirst())
            stock = inventoryCursor.getInt(0);

        if(stock < sales.getQuantity())
            return "Not enough stock for item " + sales.getItemName() + " Stock " + stock;

        // check if same item is sold within the day, if yes just update its quantity sold
        String sql2 = "SELECT Quantity FROM " + TABLE_SALES + " WHERE "
                + DATESOLD_COL + " = " + sales.getDateSold()
                + " AND ItemName = '" + sales.getItemName() + "'" ;

        Cursor salesCursor = read.rawQuery(sql2, null);

        if(salesCursor.moveToFirst()){
            // get quantity from db and increment it with the user-input quantity
            int dbQuantity = salesCursor.getInt(0);

            // increment db quantity with user-input quantity
            values.put(QUANTITY_COL, (sales.getQuantity() + dbQuantity));
            String dateSoldToString = String.valueOf(sales.getDateSold());

            result = write.update(TABLE_SALES, values, "DateSold = ?", new String[]{ dateSoldToString });
        }
        else{ // first item to be sold today
            // insert new record to sales table
            values.put(ITEMNAME_COL, sales.getItemName());
            values.put(QUANTITY_COL, sales.getQuantity());
            values.put(DATESOLD_COL, sales.getDateSold());
            values.put(COST_COL, sales.getCost());
            values.put(PRICE_COL, sales.getPrice());

            result = write.insert(TABLE_SALES, null, values);
        }
        // update the stock quantity in Sales table
        if(result != -1){
            values.clear();
            values.put(STOCKQUANTITY_COL, (stock - sales.getQuantity()));
            String itemIdtoString = String.valueOf(thisItemId);

            result = write.update(TABLE_INVENTORY, values, "itemId = ?", new String[]{ itemIdtoString });
        }
        if(result == -1) returnMsg = "Error during checkout process.";
        else returnMsg = "Checkout process successful.";

        inventoryCursor.close();
        salesCursor.close();
        write.close();
        read.close();

        return returnMsg;
    }

    public boolean deleteSalesRecords(){
        SQLiteDatabase db = this.getReadableDatabase();
        int result = db.delete(TABLE_SALES,null,null);
        if(result == -1)
            return false;
        else
            return true;
    }
    public boolean deleteItemRecords(){
        SQLiteDatabase db = this.getReadableDatabase();
        int result = db.delete(TABLE_ITEM,null,null);
        if(result == -1)
            return false;
        else
            return true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVENTORY);
        onCreate(db);
    }
}
