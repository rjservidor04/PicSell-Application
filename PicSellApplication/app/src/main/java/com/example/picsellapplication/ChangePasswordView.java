package com.example.picsellapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChangePasswordView extends AppCompatActivity {
    private final UserModel userModel = new UserModel(ChangePasswordView.this);

    EditText currentpassword, newpassword, retypepassword;
    Button updatePassword, cancel;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_view);

        currentpassword = findViewById(R.id.etCurrentPassword);
        newpassword = findViewById(R.id.etChangeNewPassword);
        retypepassword = findViewById(R.id.etRetypeNewPassword);
        updatePassword = findViewById(R.id.btnUpdatePassword);
        cancel = findViewById(R.id.btnCancelChangePassword);
        back = findViewById(R.id.btnBack);

        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = userModel.getUsername();
                String passWord = currentpassword.toString();
                String newPassword = newpassword.toString();
                String retypePassword = retypepassword.toString();

                if(passWord.isEmpty() || newPassword.isEmpty() || retypePassword.isEmpty()){
                    Toast.makeText(ChangePasswordView.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                }else if(passWord == newPassword){
                    Toast.makeText(ChangePasswordView.this, "Enter a different new password.", Toast.LENGTH_SHORT).show();
                }else if(newPassword != retypePassword){
                    Toast.makeText(ChangePasswordView.this, "New passwords do not match.", Toast.LENGTH_SHORT).show();
                }else if(userModel.verify(userName, passWord)) {
                    userModel.setPassword(passWord);
                    userModel.changePass(userName);
                    Toast.makeText(ChangePasswordView.this, "User password updated successfully, Returning to Profile View", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ChangePasswordView.this, ProfileView.class);
                        startActivity(i);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChangePasswordView.this, ProfileView.class);
                startActivity(i);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChangePasswordView.this, ProfileView.class);
                startActivity(i);
            }
        });
    }
}