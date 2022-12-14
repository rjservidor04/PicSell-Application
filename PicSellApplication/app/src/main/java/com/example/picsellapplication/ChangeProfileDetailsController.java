package com.example.picsellapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ChangeProfileDetailsController extends AppCompatActivity {
    private final UserModel userModel = new UserModel(ChangeProfileDetailsController.this);

    SharedPreferences account;
    Button updateProfileDetails, cancel;
    EditText storename, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_profile_details_view);

        updateProfileDetails = findViewById(R.id.btnUpdateProfileDetails);
        cancel = findViewById(R.id.btnCancelChangeProfileDetails);
        storename = findViewById(R.id.tvStoreName);
        username = findViewById(R.id.etChangeUsername);

        account = getSharedPreferences("MYPREFS", Activity.MODE_PRIVATE);
        String uName = account.getString("username", "N/A");
        System.out.println(uName);

        Intent i = getIntent();
        String sn = i.getStringExtra("storename");
        String un = i.getStringExtra("username");

        storename.setText(sn);
        username.setText(un);

        updateProfileDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(storename.getText().toString().isEmpty() || username.getText().toString().isEmpty()){
                    Toast.makeText(ChangeProfileDetailsController.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                }else if(userModel.checkUsername(username.getText().toString()) == false || un.equals(username.getText().toString())){
                    userModel.setStoreName(storename.getText().toString());
                    userModel.setUsername(username.getText().toString());
                    userModel.changeUser(un);
                    Toast.makeText(ChangeProfileDetailsController.this, "User account updated successfully, Returning to Profile View",
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ChangeProfileDetailsController.this, MainFragmentActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(ChangeProfileDetailsController.this, "Username already exists", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeProfileDetailsController.this, MainFragmentActivity.class);
                startActivity(intent);
            }
        });
    }
}