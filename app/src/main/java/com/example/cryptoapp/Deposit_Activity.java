package com.example.cryptoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Deposit_Activity extends AppCompatActivity {

    private Button btnCancel;
    private Button btnConfirm;
    private TextView txtFinalBalance;
    private TextView txtCurrentBalance;
    private EditText editTextNumberSigned;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        btnCancel = (Button) findViewById(R.id.buttonCancel);
        btnConfirm = (Button) findViewById(R.id.buttonConfirm);
        txtCurrentBalance = (TextView) findViewById(R.id.txtCurrentBalance);
        txtFinalBalance = (TextView) findViewById(R.id.txtFinalBalance);
        txtFinalBalance.setText(txtCurrentBalance.getText());
        editTextNumberSigned = (EditText) findViewById(R.id.editTextNumberSigned);
        //set onClick Listeners
        editTextNumberSigned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextNumberSigned.getText().toString().length()!=0){
                    double amount = Double.parseDouble(txtCurrentBalance.getText().toString().substring(1));
                    double total = amount + Double.parseDouble(editTextNumberSigned.getText().toString());
                    txtFinalBalance.setText("$" + String.valueOf(total));
                }else{
                    txtFinalBalance.setText(txtCurrentBalance.getText());
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtFinalBalance.setText(txtCurrentBalance.getText());
                editTextNumberSigned.getText().clear();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                startActivity(myIntent);
            }
        });
    }
}