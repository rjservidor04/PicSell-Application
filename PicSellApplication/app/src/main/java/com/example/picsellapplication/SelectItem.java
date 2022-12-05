package com.example.picsellapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SelectItem extends AppCompatActivity {

    private ArrayList<InventoryModel> inventoryModelArrayList;
    private PicSellApplicationDatabase dbHandler;
    private UpdateInventoryItemController updateinventoryitemcont;
    private RemoveInventoryItemController removeinventoryitemcont;
    private InventoryController  inventorycont;
    private RecyclerView updateRV, removeRV, CatRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_item);

        String previous = getIntent().getStringExtra("previous");

        inventoryModelArrayList = new ArrayList<>();
        dbHandler = new PicSellApplicationDatabase(SelectItem.this);

        inventoryModelArrayList = dbHandler.readInventory();

        if(previous.equals("update")){
            // passing array list to adapter class.
            updateinventoryitemcont = new UpdateInventoryItemController(inventoryModelArrayList, SelectItem.this);
            updateRV = findViewById(R.id.idRVSelect);

            // setting layout manager for recycler view.
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SelectItem.this, RecyclerView.VERTICAL, false);
            updateRV.setLayoutManager(linearLayoutManager);

            // setting adapter to recycler view.
            updateRV.setAdapter(updateinventoryitemcont);

        }
        if(previous.equals("delete")){
            // passing array list to adapter class.
            removeinventoryitemcont = new RemoveInventoryItemController(inventoryModelArrayList, SelectItem.this);
            removeRV = findViewById(R.id.idRVSelect);

            // setting layout manager for recycler view.
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SelectItem.this, RecyclerView.VERTICAL, false);
            removeRV.setLayoutManager(linearLayoutManager);

            // setting adapter to recycler view.
            removeRV.setAdapter(removeinventoryitemcont);
        }
        if(previous.equals("Candies")){
            inventoryModelArrayList = dbHandler.readCat1();
            // passing array list to adapter class.
            inventorycont = new InventoryController(inventoryModelArrayList, SelectItem.this);
            CatRV = findViewById(R.id.idRVSelect);

            // setting layout manager for recycler view.
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SelectItem.this, RecyclerView.VERTICAL, false);
            CatRV.setLayoutManager(linearLayoutManager);

            // setting adapter to recycler view.
            CatRV.setAdapter(inventorycont);
        }
        if(previous.equals("Junk Foods")){
            inventoryModelArrayList = dbHandler.readCat2();
            // passing array list to adapter class.
            inventorycont = new InventoryController(inventoryModelArrayList, SelectItem.this);
            CatRV = findViewById(R.id.idRVSelect);

            // setting layout manager for recycler view.
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SelectItem.this, RecyclerView.VERTICAL, false);
            CatRV.setLayoutManager(linearLayoutManager);

            // setting adapter to recycler view.
            CatRV.setAdapter(inventorycont);
        }
        if(previous.equals("Beverages")){
            inventoryModelArrayList = dbHandler.readCat3();
            // passing array list to adapter class.
            inventorycont = new InventoryController(inventoryModelArrayList, SelectItem.this);
            CatRV = findViewById(R.id.idRVSelect);

            // setting layout manager for recycler view.
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SelectItem.this, RecyclerView.VERTICAL, false);
            CatRV.setLayoutManager(linearLayoutManager);

            // setting adapter to recycler view.
            CatRV.setAdapter(inventorycont);
        }
    }
}
