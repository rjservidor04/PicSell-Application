package com.example.picsellapplication;

public class InventoryModel {
    // variables for our inventory,
    // product name, stocks, id.
    private String productName,Category;
    private int stocks,minQuan;
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
    public String getCategory()
    {return Category;
    }

    public int getMinQuan(){
        return minQuan;
    }
    public void setMinQuantity(int min){this.minQuan=min;
    }
    public void setCategory(String category){ this.Category = category;}

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
    public InventoryModel(int id, String productName, double price, int stocks, String category,int MinQuan) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.stocks = stocks;
        this.Category = category;
        this.minQuan = MinQuan;
    }
}
