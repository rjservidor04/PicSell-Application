package com.example.picsellapplication;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class UpdateInventoryItemView extends AppCompatActivity {
    EditText productNameEdt, priceEdt, stocksEdt;
    Button select, update;
    DataBase dbHandler;
    String productName;
    Double price;
    int stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateinventoryitem_view);

        productNameEdt = findViewById(R.id.etItemname);
        priceEdt = findViewById(R.id.etItemprice);
        stocksEdt = findViewById(R.id.etItemstocks);

        select = findViewById(R.id.btnSelect);
        update = findViewById(R.id.btnUpdate);

        dbHandler = new DataBase(UpdateInventoryItemView.this);

        productName = getIntent().getStringExtra("productname");
        price = getIntent().getDoubleExtra("price", 0.00);
        stock = getIntent().getIntExtra("stocks", 0);

        productNameEdt.setText(productName);
        priceEdt.setText(String.valueOf(price));
        stocksEdt.setText(String.valueOf(stock));

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UpdateInventoryItemView.this, SelectItem.class);
                i.putExtra("previous", "update");
                startActivity(i);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(productNameEdt.getText().toString().isEmpty())
                    Toast.makeText(UpdateInventoryItemView.this, "Select an Item to Update", Toast.LENGTH_SHORT).show();
                else{
                    // calling an updateInventory method and passing all edit text values
                    dbHandler.updateInventory(productName, productNameEdt.getText().toString(), Double.parseDouble(priceEdt.getText().toString()), Integer.parseInt(stocksEdt.getText().toString()));

                    Toast.makeText(UpdateInventoryItemView.this, "Item Updated..", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(UpdateInventoryItemView.this, InventoryView.class);
                    startActivity(i);
                }
            }
        });
    }
}
