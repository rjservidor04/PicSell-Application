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

    Button loginButton;
    EditText txtUsername, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);
        loginButton = findViewById(R.id.btnLogin);

//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //controller function here
//            }
//        });
    }

    public void gotoRegister(View view) {
        Intent i = new Intent(LoginView.this, RegisterView.class);
        startActivity(i);
    }

    public void loginAccount(View view) {
        Intent i= new Intent(LoginView.this, RegisterView.class);
        startActivity(i);
    }
}