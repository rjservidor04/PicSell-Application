package com.example.picsellapplication;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class InventoryModel implements Parcelable {
    PicSellApplicationDatabase dbHandler;
    private ItemModel item;
    private int inventoryId;
    private int minimumStockQuantity;
    private int stockQuantity;
    private int itemId;


    // constructor
    public InventoryModel(){}
    public InventoryModel(Context context){
        dbHandler = new PicSellApplicationDatabase(context);
    }

    public InventoryModel(ItemModel item, int minQuan, int stockQuantity){
        this.item = item;
        this.minimumStockQuantity = minQuan;
        this.stockQuantity = stockQuantity;
    }
    public InventoryModel(int id, ItemModel item, int minQuan, int stockQuantity){
        this.inventoryId = id;
        this.item = item;
        this.minimumStockQuantity = minQuan;
        this.stockQuantity = stockQuantity;
    }

    protected InventoryModel(Parcel in) {
        inventoryId = in.readInt();
        minimumStockQuantity = in.readInt();
        stockQuantity = in.readInt();
        itemId = in.readInt();
    }

    public static final Creator<InventoryModel> CREATOR = new Creator<InventoryModel>() {
        @Override
        public InventoryModel createFromParcel(Parcel in) {
            return new InventoryModel(in);
        }

        @Override
        public InventoryModel[] newArray(int size) {
            return new InventoryModel[size];
        }
    };

    public boolean addItemToInventory(InventoryModel inventory){
        String returnMsg = dbHandler.addNewItem(inventory.getItem());
        int id = dbHandler.getItemId(inventory.getItem().getItemName());
        inventory.setItemId(id);

        if(returnMsg == "Insertion was a success.")
            return dbHandler.addItemToInventory(inventory);
        else
            return false;
    }

    public void UpdateInventoryItem(InventoryModel inventory){
        int id = dbHandler.getItemId(inventory.getItem().getItemName());
        inventory.setItemId(id);
        dbHandler.updateInventory(inventory);
        dbHandler.updateItem(inventory.getItem());
        return;
    }

    public void RemoveInventoryItem(InventoryModel inventory){
        int id = dbHandler.getItemId(inventory.getItem().getItemName());
        inventory.setItemId(id);
        dbHandler.removeInventory(inventory);
        dbHandler.removeItem(inventory.getItem());
        return;
    }

    public List<InventoryModel> getInventoryItems(){
        return dbHandler.getInventoryItems();
    }
    public boolean isItemUnique(String itemName){
        return dbHandler.isItemUnique(itemName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ArrayList<String> getItemNames() {
        List<InventoryModel> list = dbHandler.getInventoryItems();
        ArrayList<String> nameList = new ArrayList<String>();

       for(int i = 0; i < list.size(); i++){
           InventoryModel temp = list.get(i);
           nameList.add(temp.getItem().getItemName());
       }

       return nameList;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(inventoryId);
        parcel.writeInt(minimumStockQuantity);
        parcel.writeInt(stockQuantity);
        parcel.writeInt(itemId);
    }

    public int getStockQuantityFromItemName(String itemName) {
        int id = dbHandler.getItemId(itemName);
        List<InventoryModel> list = dbHandler.getInventoryItems();

        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getItemId() == id ) {
                return list.get(i).getStockQuantity();

            }
        }
        return 0;
    }
    public int getMinimumStockQuantityFromItemName(String itemName) {
        int id = dbHandler.getItemId(itemName);
        List<InventoryModel> list = dbHandler.getInventoryItems();

        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getItemId() == id) {
                return list.get(i).getMinimumStockQuantity();
            }
        }

        return 0;
    }

    public ItemModel getItem() {
        return item;
    }

    public void setItem(ItemModel item) {
        this.item = item;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getMinimumStockQuantity() {
        return minimumStockQuantity;
    }

    public void setMinimumStockQuantity(int minimumStockQuantity) {
        this.minimumStockQuantity = minimumStockQuantity;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

}
