package com.example.cryptoapp;

public class NameRVModel {
    private String currencyName;

    public NameRVModel(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }
}
