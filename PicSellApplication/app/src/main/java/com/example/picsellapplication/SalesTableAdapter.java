package com.example.picsellapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SalesTableAdapter extends RecyclerView.Adapter<SalesTableAdapter.ViewHolder> {
    Context context;
    List<SalesModel> salesList;
    TextView tvResult;
    public SalesTableAdapter(){}
    public SalesTableAdapter(Context context, List<SalesModel> listSales) {

        this.context = context;
        this.salesList = listSales;
    }

    @NonNull
    @Override
    public SalesTableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sales_table_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalesTableAdapter.ViewHolder holder, int position) {
        if(salesList != null && salesList.size() > 0){
            SalesModel model = salesList.get(position);
            holder.tvItem.setText(model.getItemName());
            holder.tvQty.setText(model.getQuantity() + "");
            holder.tvPrice.setText(model.getPrice() + "");
            double total = model.getQuantity() * model.getPrice();
            holder.tvTotal.setText(total + "");
        }
    }

    @Override
    public int getItemCount() {
        return salesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvItem, tvQty, tvPrice, tvTotal;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvItem = itemView.findViewById(R.id.tvItem);
            tvQty = itemView.findViewById(R.id.tvQty);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvTotal = itemView.findViewById(R.id.tvTotal);
        }
    }

}
