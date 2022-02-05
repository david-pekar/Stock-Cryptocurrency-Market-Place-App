package com.example.cryptoapp;

public class CurrencyRVModel {
    private String symbol;
    private double price;
    private double percentChange24;
    private int imageView;

    public CurrencyRVModel(int imageView, double percentChange24, String symbol, double price) {
        this.imageView = imageView;
        this.percentChange24 = percentChange24;
        this.symbol = symbol;
        this.price = price;
    }



    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPercentChange24() {
        return percentChange24;
    }

    public void setPercentChange24(double percentChange24) {
        this.percentChange24 = percentChange24;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }





}
