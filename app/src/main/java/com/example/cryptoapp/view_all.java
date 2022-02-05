package com.example.cryptoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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

public class view_all extends AppCompatActivity {
    private RecyclerView rvViewAll;
    private ArrayList<CurrencyRVModel> currencyRVModelArrayList;
    private CurrencyRVAdapter currencyRVAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        rvViewAll = (RecyclerView) findViewById(R.id.recyclerView);
        currencyRVModelArrayList = new ArrayList<>();
        currencyRVAdapter = new CurrencyRVAdapter(currencyRVModelArrayList, this);
        rvViewAll.setLayoutManager(new LinearLayoutManager(this));
        rvViewAll.setAdapter(currencyRVAdapter);
        getCurrencyData();
    }
    private void getCurrencyData(){
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray dataArray = response.getJSONArray("data");
                    for(int i=0;i<10;i++){
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
                        }else if(i == 1){
                            currencyRVModelArrayList.add(new CurrencyRVModel(R.drawable.eth, percent, symbol, price));
                        }else{
                            currencyRVModelArrayList.add(new CurrencyRVModel(R.drawable.ic_launcher_background, percent, symbol, price));
                        }
                    }
                    currencyRVAdapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Failed to extract data", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Failed to get the data", Toast.LENGTH_SHORT).show();
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