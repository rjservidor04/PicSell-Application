package com.example.picsellapplication;

public class RegisterController {
    boolean registerStatus;

    public boolean checkDuplicate(UserModel userModel, String storeName, String storeOwnerName, String username){
        registerStatus = !userModel.checkStoreDuplicateFromDB(storeName, storeOwnerName) || !userModel.checkUsernameDuplicateFromDB(username);

        return registerStatus;
    }

    public boolean passwordConfirmation(String password, String confirmPassword){
        registerStatus = password.equals(confirmPassword);

        return registerStatus;
    }

    public void registerAccount(UserModel userModel, String storeName, String storeOwnerName, String username, String password){
        userModel.createAccount(storeName, storeOwnerName, username, password);
    }
}
