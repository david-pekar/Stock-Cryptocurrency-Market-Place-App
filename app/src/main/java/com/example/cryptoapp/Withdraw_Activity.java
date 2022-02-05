package com.example.cryptoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Withdraw_Activity extends AppCompatActivity {

    private Button btnCancel;
    private Button btnConfirm;
    private TextView txtCurrBal;
    private EditText editTextWithdrawAmount;
    private TextView txtNewBal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        btnCancel = (Button) findViewById(R.id.buttonCanc);
        btnConfirm = (Button) findViewById(R.id.buttonConfir);
        txtCurrBal = (TextView) findViewById(R.id.txtCurrBal);
        txtNewBal = (TextView) findViewById(R.id.txtNewBalance);
        txtNewBal.setText(txtCurrBal.getText());
        editTextWithdrawAmount = (EditText) findViewById(R.id.editTextWithdrawAmount);
        // OnClick Listeners
        editTextWithdrawAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextWithdrawAmount.getText().toString().length()!=0) {
                    double amount = Double.parseDouble(txtCurrBal.getText().toString().substring(1));
                    double total = amount - Double.parseDouble(editTextWithdrawAmount.getText().toString());
                    if (amount >= 0.0){
                        txtNewBal.setText("$" + String.valueOf(total));
                    }else{
                        Toast.makeText(getApplicationContext(),"Enter value where balance is greater than zero", Toast.LENGTH_LONG).show();
                    }
                }else{
                    txtNewBal.setText(txtCurrBal.getText());
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtNewBal.setText(txtCurrBal.getText());
                editTextWithdrawAmount.getText().clear();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Double.parseDouble(txtNewBal.getText().toString().substring(1)) >= 0){
                    Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                    startActivity(myIntent);
                }else{
                    Toast.makeText(getApplicationContext(),"Balance is invalid", Toast.LENGTH_LONG);
                }
            }
        });

    }
}