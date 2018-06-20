package in.co.tripin.chahiyecustomer.javacode.activity;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import in.co.tripin.chahiyecustomer.Adapters.OrderHistoryRecyclerAdapter;
import in.co.tripin.chahiyecustomer.Adapters.OrderStatusToggleCallback;
import in.co.tripin.chahiyecustomer.Managers.PreferenceManager;
import in.co.tripin.chahiyecustomer.Model.OrderSummeryPOJO;
import in.co.tripin.chahiyecustomer.Model.responce.OrderHistoryResponce;
import in.co.tripin.chahiyecustomer.Model.responce.TapriMenuResponce;
import in.co.tripin.chahiyecustomer.Model.responce.UserAddress;
import in.co.tripin.chahiyecustomer.R;
import in.co.tripin.chahiyecustomer.helper.Logger;

public class OrderHistoryActivity extends AppCompatActivity {

    private OrderHistoryRecyclerAdapter orderHistoryRecyclerAdapter;
    private AlertDialog dialog;
    private OrderHistoryResponce orderHistoryResponce;
    private Gson gson;
    private RequestQueue queue;
    private PreferenceManager preferenceManager;
    private OrderStatusToggleCallback orderStatusToggleCallback;


    private RecyclerView mOderHistoryList;
    private TextView mLoadingMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        setTitle("Order History");
        gson = new Gson();
        queue = Volley.newRequestQueue(this);
        preferenceManager = PreferenceManager.getInstance(this);

        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setCancelable(false)
                .setMessage("Loading")
                .build();

        mOderHistoryList = findViewById(R.id.orderhistorylist);
        mLoadingMsg = findViewById(R.id.loadingtv);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mOderHistoryList.setLayoutManager(mLayoutManager);

        callOrderHistoryAPI();

    }

    private void callOrderHistoryAPI() {

        Logger.v("fetch List of Order History..");
        dialog.show();
        final String url = "http://139.59.70.142:3055/api/v2/users/orders";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        orderHistoryResponce = gson.fromJson(response.toString(), OrderHistoryResponce.class);
                        if (orderHistoryResponce != null) {
                            setAdapter(orderHistoryResponce.getData());
                            dialog.dismiss();
                            if (orderHistoryResponce.getData().length > 0) {
                                mLoadingMsg.setVisibility(View.GONE);
                            } else {
                                mLoadingMsg.setVisibility(View.VISIBLE);
                                mLoadingMsg.setText("Empty Order History, Place a new one Now!");
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Logger.d("Error.Response: "+ error.toString());
                        Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", preferenceManager.getAccessToken());
                return params;
            }
        };
        queue.add(getRequest);


    }

    private void setAdapter(OrderHistoryResponce.Data[] data) {

        Logger.v("adapter set");
        orderHistoryRecyclerAdapter = new OrderHistoryRecyclerAdapter(this, data, new OrderStatusToggleCallback() {
            @Override
            public void OnOrderMakedRecived(String mOrderId) {
                
                callEditOrderAPI(mOrderId);

            }
        });
        mOderHistoryList.setAdapter(orderHistoryRecyclerAdapter);
    }

    private void callEditOrderAPI(String mOrderId) {

        Logger.v("Marking Order Recived");
        dialog.show();
        final String url = "http://139.59.70.142:3055/api/v2/order/"+mOrderId+"/recieved";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Logger.v("Response :"+ response.toString());
                        Toast.makeText(getApplicationContext(),"Marked Received",Toast.LENGTH_SHORT).show();
                        callOrderHistoryAPI();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Logger.d("Error.Response: "+ error.toString());
                        Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", preferenceManager.getAccessToken());
                return params;
            }
        };
        queue.add(getRequest);
    }

    @Override
    public void onBackPressed() {
    }
}
