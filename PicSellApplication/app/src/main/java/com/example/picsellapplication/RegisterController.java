package com.example.picsellapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;

public class RegisterController extends AppCompatActivity{
    private final UserModel userModel = new UserModel(this);
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

    private boolean checkFieldsNotEmpty(String storeName, String username, String password, String confirmPassword){
        return !storeName.isEmpty() && !username.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty();
    }

    private boolean checkNoDuplicate(String storeName, String username){
        ArrayList<UserModel> list = userModel.readUsers();
        UserModel temp;

        for(int i = 0; i < list.size(); i++){
            temp = list.get(i);

            if(storeName.equals(temp.getStoreName()) || username.equals(temp.getUsername())) return false;
        }
        return true;
    }

    private boolean confirmPassword(String password, String confirmPassword){
        return password.equals(confirmPassword);
    }

    private void registerUser(String storeName, String username, String password, String confirmPassword){
        if(checkFieldsNotEmpty(storeName, username, password, confirmPassword))
            Toast.makeText(this, "Missing field information/s, Please fill up all information.",
                    Toast.LENGTH_SHORT).show();

        else if(checkNoDuplicate(storeName, username))
            Toast.makeText(this, "Records containing this credential already exist, Use other names",
                    Toast.LENGTH_SHORT).show();

        else if(confirmPassword(password, confirmPassword))
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

