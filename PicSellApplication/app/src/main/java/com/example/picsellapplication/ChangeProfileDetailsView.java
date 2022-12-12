package com.example.picsellapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ChangeProfileDetailsView extends AppCompatActivity {
    private final UserModel userModel = new UserModel(ChangeProfileDetailsView.this);

    Button updateProfileDetails, cancel;
    ImageButton back;
    EditText storename, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.changeprofiledetails_view);

        updateProfileDetails = findViewById(R.id.btnUpdateProfileDetails);
        cancel = findViewById(R.id.btnCancelChangeProfileDetails);
        back = findViewById(R.id.btnBack);
        storename = findViewById(R.id.tvStoreName);
        username = findViewById(R.id.etChangeUsername);

        String storeName = userModel.getStoreName();
        String userName = userModel.getUsername();

        storename.setText(storeName);
        username.setText(userName);

        updateProfileDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sn = storename.getText().toString();
                String un = username.getText().toString();

                if(sn.isEmpty() || un.isEmpty()){
                    Toast.makeText(ChangeProfileDetailsView.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                }else{
                    if(!userModel.checkUsername(un)){
                        userModel.setStoreName(sn);
                        userModel.setUsername(un);
                        userModel.changeUser(userName);
                        Toast.makeText(ChangeProfileDetailsView.this, "User account updated successfully, Returning to Profile View",
                                Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ChangeProfileDetailsView.this, UserProfileView.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(ChangeProfileDetailsView.this, "Username already exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChangeProfileDetailsView.this, UserProfileView.class);
                startActivity(i);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChangeProfileDetailsView.this, UserProfileView.class);
                startActivity(i);
            }
        });
    }
}