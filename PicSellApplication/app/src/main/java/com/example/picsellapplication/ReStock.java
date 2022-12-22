package com.example.picsellapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class ReStock extends AppCompatActivity {
    Button btnrestock;
    EditText etItemName, etStocks;
    InventoryModel dbModel;
    TextView tvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restocks_view);
        String name = getIntent().getStringExtra("itemname");
        etItemName = findViewById(R.id.etItem);
        etStocks = findViewById(R.id.etQuantityv);
        btnrestock = findViewById(R.id.btnRestock);
        tvBack = findViewById(R.id.tvBack2);

        etItemName.setText(name);
        dbModel = new InventoryModel(ReStock.this);
        btnrestock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = "";
                String restockval =  etStocks.getText().toString();
                double stockval = Double.parseDouble(restockval);
                if(stockval == 0)
                    msg = "Invalid quantity value";
                else{
                    msg = "Item restocked..";
                    dbModel.ReStockItem(name,stockval);
                    Intent i = new Intent(ReStock.this, MainFragmentActivity.class);
                    startActivity(i);
                }
                Toast.makeText(ReStock.this, msg, Toast.LENGTH_SHORT).show();

            }
        });

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReStock.this, ViewItemDetails.class);
                startActivity(intent);
            }
        });

    }
}
