package com.example.picsellapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterController extends AppCompatActivity{
    private final UserModel userModel = new UserModel(RegisterController.this);
    Button register;
    EditText txtStoreName, txtUsername, txtPassword, txtConfirmPassword;
    String storeName, username, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_view);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        register = findViewById(R.id.btnRegister);

        register.setOnClickListener(RegisterController.this::registerButtonClicked);
    }

    public void registerButtonClicked(View view){
        txtStoreName = findViewById(R.id.etStoreName);
        storeName = txtStoreName.getText().toString();
        txtUsername = findViewById(R.id.etUsername);
        username = txtUsername.getText().toString();
        txtPassword = findViewById(R.id.etPassword);
        password = txtPassword.getText().toString();
        txtConfirmPassword = findViewById(R.id.etConfirmPassword);
        confirmPassword = txtConfirmPassword.getText().toString();

        registerUser(storeName, username, password, confirmPassword);
    }

    public void gotoLogin(View view){
        Intent intent = new Intent(RegisterController.this, LoginController.class);
        startActivity(intent);
    }

    private void registerUser(String storeName, String username, String password, String confirmPassword){
        if(storeName.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty())
            Toast.makeText(this, "Missing field information/s, Please fill up all information.",
                    Toast.LENGTH_SHORT).show();

        else if(!userModel.checkDuplicates(storeName, username))
            Toast.makeText(this, "Records containing this credential already exist, Use other names",
                    Toast.LENGTH_SHORT).show();

        else if(!password.equals(confirmPassword))
            Toast.makeText(this, "Password does not match, Try again", Toast.LENGTH_SHORT).show();

        else{
            userModel.setStoreName(storeName);
            userModel.setUsername(username);
            userModel.setPassword(password);
            userModel.createNewUser();
            Toast.makeText(this, "User account registered successfully, Returning to login screen",
                    Toast.LENGTH_SHORT).show();
            Intent i = new Intent(RegisterController.this, LoginController.class);
            startActivity(i);
        }
    }
}

