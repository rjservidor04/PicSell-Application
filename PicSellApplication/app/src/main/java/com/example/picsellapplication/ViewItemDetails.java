package com.example.picsellapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sPrice = etPrice.getText().toString();
                String sStock = etStock.getText().toString();
                String sMin = etMinimum.getText().toString();
                String sCost = etCost.getText().toString();
                String itemName = etItemName.getText().toString();
                double cost = Double.parseDouble(sCost);
                double price = Double.parseDouble(sPrice);
                int min = Integer.parseInt(sMin);
                int stock = Integer.parseInt(sStock);
                if(cost >= price){
                    Toast.makeText(ViewItemDetails.this, "Cost is either greater or equal to price", Toast.LENGTH_SHORT).show();
                }else{
                    Item item = new Item(itemName, cost, price);
                    InventoryModel inventory = new InventoryModel(item, min, stock);
                    dbModel.UpdateInventoryItem(inventory);
                    Toast.makeText(ViewItemDetails.this, "Item Updated..", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ViewItemDetails.this, MainFragmentActivity.class);
                    startActivity(i);
                }


            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewItemDetails.this);
                    builder.setCancelable(true);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Do you wish to delete item?");
                    builder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                dbModel.RemoveInventoryItem(inventoryModel);
                                    Toast.makeText(ViewItemDetails.this, "Item Deleted..", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(ViewItemDetails.this, MainFragmentActivity.class);
                                    startActivity(i);
                                }
                            });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
            }
        });
    }
}