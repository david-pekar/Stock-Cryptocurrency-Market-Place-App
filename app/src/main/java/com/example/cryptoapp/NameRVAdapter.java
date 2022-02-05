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
import java.util.jar.Attributes;

public class NameRVAdapter extends RecyclerView.Adapter<NameRVAdapter.ViewHolder>{
    private ArrayList<NameRVModel> currencySymbolArrayList;
    private Context context;
    private OnRVListener onRVListener;

    public NameRVAdapter(ArrayList<NameRVModel> currencySymbolArrayList, Context context, OnRVListener onRVListener) {
        this.currencySymbolArrayList = currencySymbolArrayList;
        this.context = context;
        this.onRVListener = onRVListener;
    }

    @NonNull
    @Override
    public NameRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.trading_list_view,parent,false);
        return new NameRVAdapter.ViewHolder(view, onRVListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NameRVAdapter.ViewHolder holder, int position) {
        NameRVModel nameRVModel = currencySymbolArrayList.get(position);
        //ImageView might need to fix this
        holder.currencyName.setText(nameRVModel.getCurrencyName() + "/ USD");
    }

    @Override
    public int getItemCount() {
        return currencySymbolArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView currencyName;
        OnRVListener onRVListener;
        public ViewHolder(@NonNull View itemView, OnRVListener onRVListener) {
            super(itemView);
            currencyName = itemView.findViewById(R.id.idRVSymbolTrading);
            this.onRVListener = onRVListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            onRVListener.onRVCLick(getAdapterPosition());
        }
    }

    public interface OnRVListener{
        void onRVCLick(int position);
    }
}
