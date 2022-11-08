package com.example.picsellapplication;

public class InventoryModel {
    // variables for our inventory,
    // product name, stocks, id.
    private String productName;
    private int stocks;
    private double price;
    private int id;

    // creating getter and setter methods
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getStocks() {
        return stocks;
    }

    public void setStocks(int stocks) {
        this.stocks = stocks;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // constructor
    public InventoryModel(int id, String productName, double price, int stocks) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.stocks = stocks;
    }
}
