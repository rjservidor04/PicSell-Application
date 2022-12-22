package com.example.picsellapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class ViewItemDetails extends AppCompatActivity {
    Button btnUpdate, btnDelete,btnRestock;
    SharedPreferences account;
    EditText etItemName, etPrice, etStock, etMinimum, etCost,etconpass;
    InventoryModel dbModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_view_item_details);

        TextView tvBack = (TextView) findViewById(R.id.tvBack);
        etPrice = findViewById(R.id.etPrice_Details);
        etStock = findViewById(R.id.etStocks_Details);
        etMinimum = findViewById(R.id.etMinimum_Details);
        etItemName = findViewById(R.id.etItemName_Details);
        etCost = findViewById(R.id.etCost_Details);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnRestock = findViewById(R.id.btnReStock2);


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

                if(cost >= price)
                    Toast.makeText(ViewItemDetails.this, "Price should be greater than cost to have profit", Toast.LENGTH_SHORT).show();
                else if(cost == 0 || price == 0 || min==0)
                    Toast.makeText(ViewItemDetails.this, "Invalid zero value", Toast.LENGTH_SHORT).show();
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewItemDetails.this);
                    builder.setCancelable(true);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Do you wish to Update the item?");
                    builder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(ViewItemDetails.this);
                                    final View confirmationpop_up = getLayoutInflater().inflate(R.layout.confirmationpop_up,null);
                                    etconpass = confirmationpop_up.findViewById(R.id.etpass);
                                    builder2.setView(confirmationpop_up);
                                    builder2.setTitle("Confirm Password");
                                    builder2.setPositiveButton("Enter",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    String pass = etconpass.getText().toString();
                                                    account = getSharedPreferences("MYPREFS", Activity.MODE_PRIVATE);
                                                    String uName = account.getString("username", "N/A");
                                                    if(dbModel.VerifyConfirm(uName,pass)){
                                                        ItemModel item = new ItemModel(itemName, cost, price);
                                                        InventoryModel inventory = new InventoryModel(item, min, stock);
                                                        dbModel.UpdateInventoryItem(inventory);
                                                        Toast.makeText(ViewItemDetails.this, "Item Updated..", Toast.LENGTH_SHORT).show();
                                                        Intent i = new Intent(ViewItemDetails.this, MainFragmentActivity.class);
                                                        startActivity(i);
                                                    }
                                                    else{
                                                        Toast.makeText(ViewItemDetails.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            });
                                    builder2.setNegativeButton("Cancel",   new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });

                                    AlertDialog dialog2 = builder2.create();
                                    dialog2.show();
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
                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(ViewItemDetails.this);
                                    final View confirmationpop_up = getLayoutInflater().inflate(R.layout.confirmationpop_up,null);
                                    etconpass = confirmationpop_up.findViewById(R.id.etpass);
                                    builder2.setView(confirmationpop_up);
                                    builder2.setTitle("Confirm Password");
                                    builder2.setPositiveButton("Enter",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    String pass = etconpass.getText().toString();
                                                    account = getSharedPreferences("MYPREFS", Activity.MODE_PRIVATE);
                                                    String uName = account.getString("username", "N/A");
                                                    if(dbModel.VerifyConfirm(uName,pass)){
                                                        dbModel.RemoveInventoryItem(inventoryModel);
                                                        Toast.makeText(ViewItemDetails.this, "Item Deleted..", Toast.LENGTH_SHORT).show();
                                                        Intent i = new Intent(ViewItemDetails.this, MainFragmentActivity.class);
                                                        startActivity(i);
                                                    }
                                                    else{
                                                        Toast.makeText(ViewItemDetails.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            });
                                    builder2.setNegativeButton("Cancel",   new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });

                                    AlertDialog dialog2 = builder2.create();
                                    dialog2.show();
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
        btnRestock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewItemDetails.this, ReStock.class);
                intent.putExtra("itemname",inventoryModel.getItem().getItemName());
                startActivity(intent);
            }

        });
    }
}