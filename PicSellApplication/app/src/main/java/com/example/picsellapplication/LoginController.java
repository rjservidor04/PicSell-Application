package com.example.picsellapplication;

import android.content.Intent;
import android.view.View;
import android.content.Intent;

public class LoginController {
    Boolean loginStatus;

    //verify if inputted credentials exists and correct
    public boolean verifyCredentials(UserModel userModel, String username, String password){
        if(username.isEmpty() || password.isEmpty()) loginStatus = false;

        else if(!userModel.checkCredentialsFromDB(username, password)) loginStatus = false;

        else loginStatus = true;

        return loginStatus;
    }
}
