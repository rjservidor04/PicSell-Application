package com.example.picsellapplication;


import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

public class InventoryTableAdapter extends RecyclerView.Adapter<InventoryTableAdapter.ViewHolder> {
    public static Context context;
    private List<InventoryModel> inventoryList;

    public InventoryTableAdapter(){}
    public InventoryTableAdapter(Context context, List<InventoryModel> inventoryList) {
        this.context = context;
        this.inventoryList = inventoryList;
    }

    @NonNull
    @Override
    public InventoryTableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.inventory_table_layout, parent, false);
        InventoryTableAdapter.ViewHolder inventoryViewHolder = new ViewHolder(view);
        return inventoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryTableAdapter.ViewHolder holder, int position) {
        if(inventoryList != null && inventoryList.size() > 0){
            InventoryModel inventoryModel = inventoryList.get(position);
            holder.tvItem.setText(inventoryModel.getItem().getItemName());
            holder.tvQty.setText(inventoryModel.getStockQuantity() + "");

            holder.inventoryModel = inventoryModel;
        }
    }

    @Override
    public int getItemCount() {
        return inventoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        InventoryModel inventoryModel;
        TextView tvItem, tvQty, tvMore;
//        Button btnMore;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvItem = itemView.findViewById(R.id.tvInventoryItem);
            tvQty = itemView.findViewById(R.id.tvInventoryQuantity);
            tvMore = itemView.findViewById(R.id.tvMore);

            tvMore.setOnClickListener(new View.OnClickListener() {
                // when more button is clicked in inventory view

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent (InventoryTableAdapter.context, ViewItemDetails.class);

                    // getting the data of the clicked item.
                    // the inventoryModel is initialized in the onBinderViewHolder above, in this line  "holder.inventoryModel = inventoryModel"
                    Gson gson = new Gson();
                    String json = gson.toJson(inventoryModel);
                    intent.putExtra("InventoryModel", json);
                    context.startActivity(intent);
                }
            });

        }
    }
}
