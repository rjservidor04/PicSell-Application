package com.example.picsellapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ChangePasswordController extends AppCompatActivity {
    private final UserModel userModel = new UserModel(ChangePasswordController.this);

    EditText currentpassword, newpassword, retypepassword;
    Button updatePassword, cancel;
    SharedPreferences account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_view);

        currentpassword = findViewById(R.id.etCurrentPassword);
        newpassword = findViewById(R.id.etChangeNewPassword);
        retypepassword = findViewById(R.id.etRetypeNewPassword);
        updatePassword = findViewById(R.id.btnUpdatePassword);
        cancel = findViewById(R.id.btnCancelChangePassword);

        account = getSharedPreferences("MYPREFS", Activity.MODE_PRIVATE);
        String userName = account.getString("username", "N/A");


        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passWord = currentpassword.getText().toString();
                String newPassword = newpassword.getText().toString();
                String retypePassword = retypepassword.getText().toString();

                if(passWord.isEmpty() || newPassword.isEmpty() || retypePassword.isEmpty()){
                    Toast.makeText(ChangePasswordController.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                }else if(passWord.equals(newPassword)){
                    Toast.makeText(ChangePasswordController.this, "Enter a different new password.", Toast.LENGTH_SHORT).show();
                }else if(!newPassword.equals(retypePassword)){
                    Toast.makeText(ChangePasswordController.this, "New passwords do not match.", Toast.LENGTH_SHORT).show();
                }else if(userModel.verify(userName, passWord)) {
                    userModel.setPassword(newPassword);
                    userModel.changePass(userName);
                    Toast.makeText(ChangePasswordController.this, "User password updated successfully, Returning to Profile View", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ChangePasswordController.this, MainFragmentActivity.class);
                    startActivity(i);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangePasswordController.this, MainFragmentActivity.class);
                startActivity(intent);
            }
        });
    }
}