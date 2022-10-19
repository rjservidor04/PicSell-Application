package com.example.picsellapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterView extends AppCompatActivity{
    UserModel userModel = new UserModel(RegisterView.this);
    RegisterController registerController = new RegisterController();
    Button register;
    EditText txtStoreName, txtStoreOwnerName, txtUsername, txtPassword, txtConfirmPassword;
    String storeName, storeOwnerName, username, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_view);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        register = findViewById(R.id.btnRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterView.this.registerButtonClicked(view);
            }
        });
    }

    public void gotoLogin(View view){
        Intent intent = new Intent(RegisterView.this, LoginView.class);
        startActivity(intent);
    }

    public void registerButtonClicked(View view){
        txtStoreName = findViewById(R.id.etStoreName);
        storeName = txtStoreName.getText().toString();
        txtStoreOwnerName = findViewById(R.id.etStoreOwnerName);
        storeOwnerName = txtStoreOwnerName.getText().toString();
        txtUsername = findViewById(R.id.etUsername);
        username = txtUsername.getText().toString();
        txtPassword = findViewById(R.id.etPassword);
        password = txtPassword.getText().toString();
        txtConfirmPassword = findViewById(R.id.etConfirmPassword);
        confirmPassword = txtConfirmPassword.getText().toString();

        if(!registerController.fieldNotEmpty(storeName, storeOwnerName, username, password, confirmPassword))
            Toast.makeText(this, "Missing Field Information. Please fill up all information.", Toast.LENGTH_SHORT).show();

        else if(!registerController.checkDuplicate(userModel, storeName, storeOwnerName, username))
            Toast.makeText(this, "Records containing this credential already exist. Use other names", Toast.LENGTH_SHORT).show();

        else if(!registerController.passwordConfirmation(password, confirmPassword))
            Toast.makeText(this, "Password does not match. Try Again", Toast.LENGTH_SHORT).show();

        else{
            registerController.registerAccount(userModel, storeName, storeOwnerName, username, password);
            Toast.makeText(this, "Account Registered Successfully", Toast.LENGTH_SHORT).show();
            //make an intent here
        }

    }
}

