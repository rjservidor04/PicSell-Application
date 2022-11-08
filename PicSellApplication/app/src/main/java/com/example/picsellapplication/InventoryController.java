package com.example.picsellapplication;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class InventoryController extends RecyclerView.Adapter<InventoryController.ViewHolder> {
    private ArrayList<InventoryModel> inventoryModelArrayList;
    private Context context;

    // constructor
    public InventoryController(ArrayList<InventoryModel> inventoryModalArrayList, Context context) {
        this.inventoryModelArrayList = inventoryModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating layout file for recycler view items
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // setting data to views of recycler view item
        InventoryModel modal = inventoryModelArrayList.get(position);
        holder.productIdTV.setText(String.valueOf(modal.getId()));
        holder.productNameTV.setText(modal.getProductName());
        holder.productPriceTV.setText(String.valueOf(modal.getPrice()));
        holder.productStocksTV.setText(String.valueOf(modal.getStocks()));
    }

    @Override
    public int getItemCount() {
        // returning the size of array list
        return inventoryModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView productIdTV, productNameTV, productPriceTV, productStocksTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productIdTV = itemView.findViewById(R.id.idTVProductID);
            productNameTV = itemView.findViewById(R.id.idTVProductName);
            productPriceTV = itemView.findViewById(R.id.idTVProductPrice);
            productStocksTV = itemView.findViewById(R.id.idTVProductStock);
        }
    }
}
