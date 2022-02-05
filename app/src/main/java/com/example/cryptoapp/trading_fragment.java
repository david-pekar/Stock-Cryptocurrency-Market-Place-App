package com.example.cryptoapp;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;


public class trading_fragment extends Fragment implements  NameRVAdapter.OnRVListener{
    private static final String TAG = "trading_fragment";
    private RecyclerView idRVCurrencyInfoTrading;
    private RecyclerView idRVCurrencyNames;
    private ArrayList<CurrencyRVModel> currencyRVModelArrayList;
    private ArrayList<NameRVModel> nameRVModelArrayList;
    private ArrayList<Integer> imageViewArrayList;
    private CurrencyRVAdapter currencyRVAdapter;
    private NameRVAdapter nameRVAdapter;
    public trading_fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //ImageView array list
        imageViewArrayList = new ArrayList<>();
        imageViewArrayList.add(R.drawable.bitcoin);
        imageViewArrayList.add(R.drawable.eth);
        imageViewArrayList.add(R.drawable.ic_launcher_background);
        imageViewArrayList.add(R.drawable.ic_launcher_background);
        imageViewArrayList.add(R.drawable.ic_launcher_background);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.trading_fragment, container, false);
        //This is for the RecyclerView that is like the one in the main menu
        idRVCurrencyInfoTrading = view.findViewById(R.id.idRVCurrencyInfoTrading);
        currencyRVModelArrayList = new ArrayList<>();
        currencyRVAdapter = new CurrencyRVAdapter(currencyRVModelArrayList, getActivity());
        idRVCurrencyInfoTrading.setLayoutManager(new LinearLayoutManager(getActivity()));
        idRVCurrencyInfoTrading.setAdapter(currencyRVAdapter);
        //This is for the RecyclerView that just houses the names of the Currency
        idRVCurrencyNames = view.findViewById(R.id.idRVCurrencyNames);
        nameRVModelArrayList = new ArrayList<>();
        nameRVAdapter = new NameRVAdapter(nameRVModelArrayList, getActivity(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        idRVCurrencyNames.setLayoutManager(linearLayoutManager);
        idRVCurrencyNames.setAdapter(nameRVAdapter);
        //getCurrencyData();
        //getCurrencyDataForCurrencyInfoTrading(0);
        //this is for the graph
        //Create map with x and y axis data
        TreeMap<Integer, Integer> graphMap = new TreeMap<>();
        graphMap.put(15, 30);
        graphMap.put(16, 25);
        graphMap.put(17, 35);
        graphMap.put(18, 40);
        graphMap.put(19, 45);
        graphMap.put(20, 35);
        graphMap.put(21, 50);
        graphMap.put(22, 75);
        GraphView graph = view.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
        for (Map.Entry<Integer,Integer> entry : graphMap.entrySet()){
            series.appendData(new DataPoint(entry.getKey(),entry.getValue()), true, 90);
        }
        //weightGraph.getGridLabelRenderer().setNumHorizontalLabels(10);
        graph.getGridLabelRenderer().setNumHorizontalLabels(8);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinX(15);
        graph.getViewport().setMaxX(22);
        graph.getViewport().setMinY(20);
        graph.getViewport().setMaxY(100);
        series.setColor(Color.parseColor("#EE6855"));
        series.setDrawBackground(true);
        series.setBackgroundColor(Color.parseColor("#EE6855"));
        graph.addSeries(series);
        //This is tha end
        return view;
    }


    //This is for the idRVCurrencyInfoTrading RecyclerView
    private void getCurrencyData() {
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray dataArray = response.getJSONArray("data");
                    for (int i = 0; i < 5; i++) {
                        JSONObject dataObj = dataArray.getJSONObject(i);
                        String name = dataObj.getString("name");
                        String symbol = dataObj.getString("symbol");
                        nameRVModelArrayList.add(new NameRVModel(symbol));
                        JSONObject quote = dataObj.getJSONObject("quote");
                        JSONObject USD = quote.getJSONObject("USD");
                        double price = USD.getDouble("price");
                        double percent = USD.getDouble("percent_change_24h");
                        //CurrencyRVModel(, double percentChange24, String symbol, double price)
                        //currencyRVModelArrayList.add(new CurrencyRVModel(R.drawable.ic_launcher_background, percent, symbol, price));
                        //currencyRVModelArrayList.add(new CurrencyRVModel(name,symbol,price));
                    }
                    nameRVAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed to extract data", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Failed to get the data", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("X-CMC_PRO_API_KEY", "Val");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    //this is the method to create one listview item
    private void getCurrencyDataForCurrencyInfoTrading(int i) {
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray dataArray = response.getJSONArray("data");
                    JSONObject dataObj = dataArray.getJSONObject(i);
                    String symbol = dataObj.getString("symbol");
                    JSONObject quote = dataObj.getJSONObject("quote");
                    JSONObject USD = quote.getJSONObject("USD");
                    double price = USD.getDouble("price");
                    double percent = USD.getDouble("percent_change_24h");
                    currencyRVModelArrayList.add(new CurrencyRVModel(imageViewArrayList.get(i), percent, symbol, price));
                    if(currencyRVModelArrayList.size() > 1){
                        currencyRVModelArrayList.remove(0);
                    }
                    currencyRVAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed to extract data", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Failed to get the data", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("X-CMC_PRO_API_KEY", "VAL");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);


    }

    @Override
    public void onRVCLick(int position) {
        Log.d(TAG, "onRVCLick:" + position);
        getCurrencyDataForCurrencyInfoTrading(position);
    }
}
