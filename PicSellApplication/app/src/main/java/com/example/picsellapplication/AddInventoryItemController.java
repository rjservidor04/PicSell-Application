package com.example.picsellapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class AddInventoryItemController extends AppCompatActivity {
    Button btnAdd;
    EditText etItemName, etPrice, etStock, etMinimum, etCost;
    TextView tvBack;
    InventoryModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addinventoryitem_view);
        etItemName = (EditText) findViewById(R.id.etItemName);
        etCost = (EditText) findViewById(R.id.etCost);
        etPrice =(EditText) findViewById(R.id.etPrice);
        etStock = (EditText) findViewById(R.id.etStocks);
        etMinimum = (EditText) findViewById(R.id.etMinimum);
        tvBack = (TextView) findViewById(R.id.tvBack);

        btnAdd = (Button) findViewById(R.id.btnAdd);

        model = new InventoryModel(AddInventoryItemController.this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = "";
                String itemName = etItemName.getText().toString();
                String sPrice = etPrice.getText().toString();
                String sStock = etStock.getText().toString();
                String sMin = etMinimum.getText().toString();
                String sCost = etCost.getText().toString();

                if(itemName.isEmpty() || sPrice.isEmpty() || sStock.isEmpty() || sMin.isEmpty()|| sCost.isEmpty())
                    msg = "Please input all fields";
                else if(Double.parseDouble(sCost) >= Double.parseDouble(sPrice)){
                    msg = "Cost is either greater than or equal to price";
                }
                else{
                    try {
                        double cost = Double.parseDouble(sCost);
                        double price = Double.parseDouble(sPrice);
                        int min = Integer.parseInt(sMin);
                        int stock = Integer.parseInt(sStock);

                        Item item = new Item(itemName, cost, price);
                        InventoryModel inventory = new InventoryModel(item, min, stock);

                        if(model.isItemUnique(itemName)){
                            if(model.addItemToInventory(inventory)){ // this method inserts data to Item table first, then to Inventory table
                                msg = "Insertion is successful";
                                Intent intent = new Intent(AddInventoryItemController.this, MainFragmentActivity.class);
                                startActivity(intent);
                            }
                            else
                                msg = "Insertion failed";
                        }else
                            msg = "Product already exists";
                    }
                    catch (Exception e){
                        msg = "Error " + e.getMessage();
                    }
                }
                Toast.makeText(AddInventoryItemController.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddInventoryItemController.this, MainFragmentActivity.class);
                startActivity(intent);
            }
        });
    }
}

