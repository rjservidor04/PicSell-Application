package com.example.rd_picsell;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);

    //    AppBarConfiguration appBarConfig = new AppBarConfiguration.Builder(R.id.inventoryView,
     //           R.id.salesView2,R.id.checkoutView,R.id.profileView).build();

    //    NavigationUI.setupActionBarWithNavController(this,navController, appBarConfig);

        NavigationUI.setupWithNavController(bottomNav, navController);

    }
}