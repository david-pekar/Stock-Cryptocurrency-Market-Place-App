package com.example.cryptoapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class home_fragment extends Fragment {
    private RecyclerView currenciesRV;
    private ArrayList<CurrencyRVModel> currencyRVModelArrayList;
    private CurrencyRVAdapter currencyRVAdapter;
    private ArrayList<Integer> imageArrayList;
    private TextView accountBalanceTxt;
    private static double accountBalance = 0.00;
    private Button btnDepositHome;
    private Button btnWithdrawHome;
    private TextView txtSeeAll;
    public home_fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //for the images
        imageArrayList = new ArrayList<>();
        //imageArrayList.add(R.drawable.bitcoinlogo);
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        currenciesRV = view.findViewById(R.id.recyclerView);
        currencyRVModelArrayList = new ArrayList<>();
        currencyRVAdapter = new CurrencyRVAdapter(currencyRVModelArrayList,getActivity());
        currenciesRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        currenciesRV.setAdapter(currencyRVAdapter);
        //getCurrencyData();
        // For the buttons
        btnDepositHome = (Button) view.findViewById(R.id.btnDepositHome);
        btnDepositHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Deposit_Activity.class);
                startActivity(intent);
            }
        });
        btnWithdrawHome = (Button) view.findViewById(R.id.btnWithdrawHome);
        btnWithdrawHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Withdraw_Activity.class);
                startActivity(intent);
            }
        });
        txtSeeAll = (TextView) view.findViewById(R.id.txtSeeAll);
        txtSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), view_all.class);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return view;

    }
    private void getCurrencyData(){
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray dataArray = response.getJSONArray("data");
                    for(int i=0;i<2;i++){
                        JSONObject dataObj = dataArray.getJSONObject(i);
                        //String name = dataObj.getString("name");
                        String symbol = dataObj.getString("symbol");
                        JSONObject quote = dataObj.getJSONObject("quote");
                        JSONObject USD = quote.getJSONObject("USD");
                        double price = USD.getDouble("price");
                        double percent = USD.getDouble("percent_change_24h");
                        //CurrencyRVModel(ImageView imageView, double percentChange24, String symbol, double price)
                        if(i == 0){
                            currencyRVModelArrayList.add(new CurrencyRVModel(R.drawable.bitcoin, percent, symbol, price));
                        }else{
                            currencyRVModelArrayList.add(new CurrencyRVModel(R.drawable.eth, percent, symbol, price));
                        }
                    }
                    currencyRVAdapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getActivity(),"Failed to extract data", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Failed to get the data", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("X-CMC_PRO_API_KEY","91a9ca08-61cc-4b01-99c1-e1cff7841965");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}