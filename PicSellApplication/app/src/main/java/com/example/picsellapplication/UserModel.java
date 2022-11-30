package com.example.picsellapplication;

import android.content.Context;
import java.util.ArrayList;

public class UserModel {
    private PicSellApplicationDatabase db;
    private String storeName;
    private String username;
    private String password;

    public UserModel(Context context){
        db = new PicSellApplicationDatabase(context);
    }

    public UserModel(String storeName, String username, String password){
        this.storeName = storeName;
        this.username = username;
        this.password = password;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void createNewUser(){
        db.addNewUser(this.storeName, this.username, this.password);
    }

    public ArrayList<UserModel> readUsers(){
        return db.readUsers();
    }

    public boolean verify(String username, String password){
        return db.verifyCredentials(username, password);
    }

    public boolean checkDuplicates(String storeName, String username){
        return db.checkForDuplicates(storeName, username);
    }
}
