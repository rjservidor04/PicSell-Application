package com.example.picsellapplication;

public class Item {
    private int itemId;
    private String itemName;
    private double cost;
    private double price;

    public Item (){};
    public Item (String itemName, double cost, double price){
        this.itemName = itemName;
        this.cost = cost;
        this.price = price;
    };

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}


