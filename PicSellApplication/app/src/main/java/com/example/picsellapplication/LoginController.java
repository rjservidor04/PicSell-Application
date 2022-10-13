package com.example.picsellapplication;

public class LoginController {
    boolean loginStatus;

    //check if fields are not empty
    public boolean credentialsNotEmpty(String username, String password){
       loginStatus = !username.isEmpty() && !password.isEmpty();

        return loginStatus;
    }

    //verify if inputted credentials exists and correct
    public boolean verifyCredentials(UserModel userModel, String username, String password){
        loginStatus = userModel.checkCredentialsFromDB(username, password);

        return loginStatus;
    }
}
