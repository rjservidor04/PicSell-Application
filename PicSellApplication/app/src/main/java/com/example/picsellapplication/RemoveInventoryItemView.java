package com.example.picsellapplication;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
public class RemoveInventoryItemView extends AppCompatActivity {
    Button select, remove;
    TextView productname;
    PicSellApplicationDatabase dbHandler;
    String productName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.removeinventoryitem_view);

        productname = findViewById(R.id.tvProductName);
        select = findViewById(R.id.btnSelectItem);
        remove = findViewById(R.id.btnRemove);

        dbHandler = new PicSellApplicationDatabase(RemoveInventoryItemView.this);

        productName = getIntent().getStringExtra("productname");

        productname.setText(productName);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RemoveInventoryItemView.this, SelectItem.class);
                i.putExtra("previous", "delete");
                startActivity(i);
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(productname.getText().toString().isEmpty())
                    Toast.makeText(RemoveInventoryItemView.this, "Select Item to Remove", Toast.LENGTH_SHORT).show();
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(RemoveInventoryItemView.this);
                    builder.setCancelable(true);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Do you wish to delete item?");
                    builder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dbHandler.removeInventory(productName);

                                    Toast.makeText(RemoveInventoryItemView.this, "Item Deleted..", Toast.LENGTH_SHORT).show();

                                    Intent i = new Intent(RemoveInventoryItemView.this, InventoryView.class);
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
            }
        });
    }
}
