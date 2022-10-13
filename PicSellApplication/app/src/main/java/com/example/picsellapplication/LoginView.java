package com.example.picsellapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginView extends AppCompatActivity {

    LoginController controller;
    Button loginButton;
    String username, password;
    EditText txtUsername, txtPassword;
    UserModel userModel = new UserModel(LoginView.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);
    }

    //when register is clicked
    public void gotoRegister(View view) {
        Intent i = new Intent(LoginView.this, RegisterView.class);
        startActivity(i);
    }

    //when login button is clicked
    public void loginAccount(View view) {
        loginButton = findViewById(R.id.btnLogin);
        txtUsername = findViewById(R.id.etUsername);
        username = txtUsername.getText().toString();
        txtPassword = findViewById(R.id.etPassword);
        password = txtPassword.getText().toString();

        //check if login credentials are correct
        if(controller.verifyCredentials(userModel, username, password)){
            Intent i= new Intent(LoginView.this, RegisterView.class);
            startActivity(i);
        }

        else{
            Toast.makeText(this, "Login Failed. Try Again", Toast.LENGTH_SHORT).show();
        }
    }
}