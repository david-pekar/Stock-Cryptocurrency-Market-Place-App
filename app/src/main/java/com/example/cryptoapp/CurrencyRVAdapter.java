package com.example.cryptoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CurrencyRVAdapter extends RecyclerView.Adapter<CurrencyRVAdapter.ViewHolder> {
    private ArrayList<CurrencyRVModel> currencyRVModelArrayList;
    private Context context;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private static DecimalFormat df3 = new DecimalFormat("#.###");

    public CurrencyRVAdapter(ArrayList<CurrencyRVModel> currencyRVModelArrayList, Context context) {
        this.currencyRVModelArrayList = currencyRVModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CurrencyRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.currency_rv_item,parent,false);
        return new CurrencyRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyRVAdapter.ViewHolder holder, int position) {
        CurrencyRVModel currencyRVModel = currencyRVModelArrayList.get(position);
        //ImageView might need to fix this
        holder.imageView.setImageResource(currencyRVModel.getImageView());
        //symbol
        holder.symbolTV.setText(currencyRVModel.getSymbol());
        //rate
        holder.rateTV.setText("$" + df2.format(currencyRVModel.getPrice()));
        //Percentage
        holder.percentChange24.setText(df3.format(currencyRVModel.getPercentChange24()) + "%");


    }

    @Override
    public int getItemCount() {
        return currencyRVModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView percentChange24, symbolTV, rateTV;
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            percentChange24 = itemView.findViewById(R.id.idRVPercentChange);
            imageView = itemView.findViewById(R.id.RVImageView);
            symbolTV = itemView.findViewById(R.id.idTVSymbol);
            rateTV = itemView.findViewById(R.id.idTVCurrencyRate);
        }
    }
}
