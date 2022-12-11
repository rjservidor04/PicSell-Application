package com.example.picsellapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

public class ViewItemDetails extends AppCompatActivity {
    Button btnUpdate, btnDelete;
    EditText etItemName, etPrice, etStock, etMinimum, etCost;
    InventoryModel dbModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_item_details);

        TextView tvBack = (TextView) findViewById(R.id.tvBack);
        etPrice = findViewById(R.id.etPrice_Details);
        etStock = findViewById(R.id.etStocks_Details);
        etMinimum = findViewById(R.id.etMinimum_Details);
        etItemName = findViewById(R.id.etItemName_Details);
        etCost = findViewById(R.id.etCost_Details);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        // getting the data from the inventory view through the use of Gson or basically JSON
        // where we convert Object to String and vice versa.
        Gson gson = new Gson();
        Intent intent = getIntent();
        String json = intent.getStringExtra("InventoryModel");
        InventoryModel inventoryModel = gson.fromJson(json, InventoryModel.class);

        etItemName.setText(inventoryModel.getItem().getItemName());
        etCost.setText(inventoryModel.getItem().getCost() +"");
        etPrice.setText(inventoryModel.getItem().getPrice() +"");
        etStock.setText(inventoryModel.getStockQuantity() +"");
        etMinimum.setText(inventoryModel.getMinimumStockQuantity() +"");

        dbModel = new InventoryModel(ViewItemDetails.this);

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewItemDetails.this, MainFragmentActivity.class);
                startActivity(intent);
            }
        });

    }
}