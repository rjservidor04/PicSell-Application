package com.example.picsellapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class InventoryView extends AppCompatActivity {
    private ArrayList<InventoryModel> inventoryModalArrayList;
    private DataBase dbHandler;
    private InventoryController inventoryController;
    private RecyclerView inventoryRV;
    Button addItem, updateItem, removeItem, home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_view);
        addItem = findViewById(R.id.btnAddItem);
        updateItem = findViewById(R.id.btnUpdateItem);
        removeItem = findViewById(R.id.btnRemoveItem);
        home = findViewById(R.id.btnHome);

        inventoryModalArrayList = new ArrayList<>();
        dbHandler = new DataBase(InventoryView.this);

        // getting inventory array list from dbHandler class
        inventoryModalArrayList = dbHandler.readInventory();

        // passing array list to adapter class.
        inventoryController = new InventoryController(inventoryModalArrayList, InventoryView.this);
        inventoryRV = findViewById(R.id.idRVInventory);

        // setting layout manager for recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(InventoryView.this, RecyclerView.VERTICAL, false);
        inventoryRV.setLayoutManager(linearLayoutManager);

        // setting adapter to recycler view.
        inventoryRV.setAdapter(inventoryController);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(InventoryView.this, AddInventoryItemView.class);
                startActivity(i);
            }
        });

        updateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(InventoryView.this, UpdateInventoryItemView.class);
                startActivity(i);
            }
        });

        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(InventoryView.this, RemoveInventoryItemView.class);
                startActivity(i);
            }
        });
        /*
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(InventoryView.this, Account.class);
                startActivity(i);
            }
        });

         */
    }
}
