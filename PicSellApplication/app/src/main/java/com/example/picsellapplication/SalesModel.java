package com.example.picsellapplication;

import android.content.Context;
import android.database.Cursor;

public class SalesModel {
    private String itemName;
    private int quantity;
    private int dateSold;
    private double cost;
    private double price;

    private PicSellApplicationDatabase db;

    public SalesModel(){}
    public SalesModel(Context context){
        db = new PicSellApplicationDatabase(context);
    }
    public SalesModel(String itemName, int qty, double cost, double price) {
        this.itemName = itemName;
        quantity = qty;
        this.cost = cost;
        this.price = price;
    }

    public String addItemToSales(SalesModel sales){
        Boolean isUnique = db.isItemUnique(sales.getItemName());
        if(isUnique)
            return "Item does not exists";

        ItemModel item = db.getItem(sales.getItemName());

        sales.setCost(item.getCost());
        sales.setPrice(item.getPrice());
        return db.addItemToSales(sales);
    }

    public Cursor getSoldItems(int startDate, int endDate){
        return  db.getSoldItems(startDate, endDate);
    }


//    public boolean deleteSalesRecords(){
//        return db.deleteSalesRecords();
//    }
//    public boolean deleteItemRecords(){
//        return db.deleteItemRecords();
//    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String item) {
        this.itemName = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDateSold() {
        return dateSold;
    }

    public void setDateSold(int dateSold_Int) {
        dateSold = dateSold_Int;
    }

    public double getPrice(){ return price;}

    public void setPrice(double price) { this.price = price;}

    public double getCost(){ return cost;}

    public void setCost(double cost) { this.cost = cost;}
}
