package com.example.picsellapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginController extends AppCompatActivity {
    private final UserModel userModel = new UserModel(LoginController.this);

    SharedPreferences account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        account = getSharedPreferences("MYPREFS", Activity.MODE_PRIVATE);

        Button login = findViewById(R.id.btnLogin);

        login.setOnClickListener(LoginController.this::loginButtonClicked);
    }

    //when login button is clicked
    public void loginButtonClicked(View view){
        EditText txtUsername = findViewById(R.id.etUsername);
        String username = txtUsername.getText().toString();
        EditText txtPassword = findViewById(R.id.etPassword);
        String password = txtPassword.getText().toString();

        loginUser(username, password);
    }

    //when register is clicked
    public void gotoRegister(View view) {
        Intent intent = new Intent(LoginController.this, RegisterController.class);
        startActivity(intent);
    }

    private void loginUser(String username, String password){
        //check if login credentials are correct
        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
        }

        else if(userModel.verify(username, password)){
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

            SharedPreferences.Editor editor = account.edit();
            editor.putString("username", username);
            editor.commit();

            Intent i = new Intent(LoginController.this, MainFragmentActivity.class);
            startActivity(i);
        }

        else{
            Toast.makeText(this, "Invalid credentials, Have you already registered?", Toast.LENGTH_SHORT).show();
        }
    }
}