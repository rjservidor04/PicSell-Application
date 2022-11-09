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
public class UpdateInventoryItemController extends RecyclerView.Adapter<UpdateInventoryItemController.ViewHolder> {
    private ArrayList<InventoryModel> inventoryModelArrayList;
    private Context context;

    // constructor
    public UpdateInventoryItemController(ArrayList<InventoryModel> inventoryModelArrayList, Context context) {
        this.inventoryModelArrayList = inventoryModelArrayList;
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

        // on click listener for recycler view item.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, UpdateInventoryItemView.class);

                i.putExtra("productname", modal.getProductName());
                i.putExtra("price", modal.getPrice());
                i.putExtra("stocks", modal.getStocks());

                context.startActivity(i);
            }
        });

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
