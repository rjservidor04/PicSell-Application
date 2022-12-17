package com.example.picsellapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class ReStock extends AppCompatActivity {
    Button btnrestock;
    EditText etItemName, etStocks;
    InventoryModel dbModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restocks_view);
        String name = getIntent().getStringExtra("itemname");
        etItemName = findViewById(R.id.etItem);
        etStocks = findViewById(R.id.etQuantityv);
        btnrestock = findViewById(R.id.btnRestock);

        etItemName.setText(name);
        dbModel = new InventoryModel(ReStock.this);
        btnrestock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String restockval =  etStocks.getText().toString();
                double stockval = Double.parseDouble(restockval);
                dbModel.ReStockItem(name,stockval);
                Toast.makeText(ReStock.this, "Item Restocked..", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ReStock.this, MainFragmentActivity.class);
                startActivity(i);
            }
        });

    }
}
