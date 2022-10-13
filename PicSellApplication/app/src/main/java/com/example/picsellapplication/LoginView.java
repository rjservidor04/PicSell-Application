package com.example.picsellapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginView extends AppCompatActivity {

    UserModel userModel = new UserModel(LoginView.this);
    LoginController controller;
    EditText txtUsername, txtPassword;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);
    }

    //when register is clicked
    public void gotoRegister(View view) {
        Intent intent = new Intent(LoginView.this, RegisterView.class);
        startActivity(intent);
    }

    //when login button is clicked
    public void loginButtonClicked(View view) {
        txtUsername = findViewById(R.id.etUsername);
        username = txtUsername.getText().toString();
        txtPassword = findViewById(R.id.etPassword);
        password = txtPassword.getText().toString();

        //check if login credentials are correct
        if(!controller.credentialsNotEmpty(username, password)){
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
        }

        else if(controller.verifyCredentials(userModel, username, password)){
            Intent intent= new Intent(LoginView.this, RegisterView.class);
            startActivity(intent);
        }

        else{
            Toast.makeText(this, "Invalid Credentials. Have you already registered?", Toast.LENGTH_SHORT).show();
        }
    }
}