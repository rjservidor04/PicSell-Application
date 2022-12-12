package com.example.picsellapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class UserProfileView extends AppCompatActivity {
    private final UserModel userModel = new UserModel(UserProfileView.this);

    TextView storename, username, password;
    Button editProfile, changePassword;
    ImageButton back;
    TextView logOut;

//    public UserProfileView(UserProfileController userProfileController) {
//    }
//    PicSellApplicationDatabase dbHandler;
//    Button changeProfileDetails, logout;
//    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofile_view);

        storename = findViewById(R.id.tvStoreName);
        username = findViewById(R.id.tvUsername);
        password = findViewById(R.id.tvPassword);

        String storeName = userModel.getStoreName();
        String userName = userModel.getUsername();
        String passWord = userModel.getPassword();

        storename.setText(storeName);
        username.setText(userName);
        password.setText(passWord);

//        changeProfileDetails = findViewById(R.id.btnChangeProfileDetails);
//        logout = findViewById(R.id.btnLogout);
//        back = findViewById(R.id.btnBack);

//        dbHandler = new PicSellApplicationDatabase(UserProfileView.this);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserProfileView.this, ChangeProfileDetailsView.class);
                startActivity(i);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserProfileView.this, ChangePasswordView.class);
                startActivity(i);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //tbi
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dbHandler.closeDB();
                Intent i = new Intent(UserProfileView.this, LoginController.class);
                startActivity(i);
            }
        });

    }
}