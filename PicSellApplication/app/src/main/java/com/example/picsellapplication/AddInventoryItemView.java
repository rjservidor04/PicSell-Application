package com.example.picsellapplication;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class AddInventoryItemView extends AppCompatActivity {
    EditText itemnameEdt, priceEdt, stockEdt;
    Button addItem;
    DataBase dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addinventoryitem_view);

        addItem = findViewById(R.id.btnAdd);
        itemnameEdt = findViewById(R.id.etItemName);
        priceEdt = findViewById(R.id.etPrice);
        stockEdt = findViewById(R.id.etStocks);

        dbHandler = new DataBase(AddInventoryItemView.this);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemname = itemnameEdt.getText().toString();
                String price = priceEdt.getText().toString();
                String stock = stockEdt.getText().toString();

                if(itemname.isEmpty() || price.isEmpty() || stock.isEmpty())
                    Toast.makeText(AddInventoryItemView.this, "Please input all fields", Toast.LENGTH_SHORT).show();
                else{
                    if(!dbHandler.checkproduct(itemname)){
                        dbHandler.addNewProduct(itemname, Double.parseDouble(price), Integer.parseInt(stock));

                        Intent i = new Intent(AddInventoryItemView.this, InventoryView.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(AddInventoryItemView.this, "Product already exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}
