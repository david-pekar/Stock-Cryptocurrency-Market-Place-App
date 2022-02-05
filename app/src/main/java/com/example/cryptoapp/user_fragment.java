package com.example.cryptoapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class user_fragment extends Fragment {
    private Button resetButton;

    public user_fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        resetButton = (Button) view.findViewById(R.id.buttonCancel);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Balance Successfully Reset", Toast.LENGTH_LONG).show();
            }
        });

        Button depositButton = (Button) view.findViewById(R.id.buttonUserDeposit);
        depositButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Deposit_Activity.class);
                startActivity(intent);
            }
        });
        Button withdrawButton = (Button) view.findViewById(R.id.buttonUserWithdraw);
        withdrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Withdraw_Activity.class);
                startActivity(intent);
            }
        });
        Button cancelButton = (Button) view.findViewById(R.id.buttonUserCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Task Successfully Canceled", Toast.LENGTH_LONG).show();
            }
        });
        Button confirmButton = (Button) view.findViewById(R.id.buttonUserConfirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Task Confirmed", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
