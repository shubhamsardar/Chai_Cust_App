package in.co.tripin.chahiyecustomer.javacode.activity;

import android.content.Intent;
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
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.co.tripin.chahiyecustomer.Adapters.AddressListRecyclerAdapter;
import in.co.tripin.chahiyecustomer.Adapters.AddresslistInteractionCallback;
import in.co.tripin.chahiyecustomer.Managers.UserAddressManager;
import in.co.tripin.chahiyecustomer.Model.responce.Tapri;
import in.co.tripin.chahiyecustomer.Model.responce.UserAddress;
import in.co.tripin.chahiyecustomer.R;
import in.co.tripin.chahiyecustomer.helper.Logger;

public class SelectAddressActivity extends AppCompatActivity {

    private UserAddressManager mUserAddressManager;
    private RequestQueue queue;
    private UserAddress userAddress;
    private AddressListRecyclerAdapter addressListRecyclerAdapter;
    private Gson gson;

    private RecyclerView mAddressList;
    private TextView mLoadingMsg;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        setTitle("Select Address");
        mUserAddressManager = new UserAddressManager(this);
        queue =Volley.newRequestQueue(this);
        gson = new Gson();
        init();


    }

    private void init() {
        mAddressList = findViewById(R.id.addresslist);
        mLoadingMsg = findViewById(R.id.addressload);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchListOfAddress();

    }

    public void addAddress(View view) {
        startActivity(new Intent(SelectAddressActivity.this, AddAddressActivity.class));
    }

    private void fetchListOfAddress() {

        Logger.v("fetch List of address..");
        final String url = "http://139.59.70.142:3055/api/v1/users/address?userAddress=ALL";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        userAddress = gson.fromJson(response.toString(),UserAddress.class);
                        if(userAddress!=null){
                                setAdapter(userAddress.getData());
                                if(userAddress.getData().length>0){
                                    mLoadingMsg.setVisibility(View.GONE);
                                }else {
                                    mLoadingMsg.setVisibility(View.VISIBLE);
                                    mLoadingMsg.setText("Empty Addresses, Add a new one Now!");
                                }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwZXJzb25JZCI6IjViMjM3MjllYjc3ZDBkMDAxNTU0NWU1OSIsInJvbGVJZCI6IjViMjM3MjllYjc3ZDBkMDAxNTU0NWU1YSIsImV4cGlyZXMiOjE1MjkxMzYxNTg1NTB9.QJLI7T-qkAhJyiHXDjffCClMZVTn8G8TV_SF2MN50Yg");
                return params;              }
        };
        queue.add(getRequest);



    }

    private void setAdapter(final UserAddress.Data[] data) {

        addressListRecyclerAdapter = new AddressListRecyclerAdapter(data, new AddresslistInteractionCallback() {
            @Override
            public void onAddressSelected(UserAddress.Data address) {
                Toast.makeText(getApplicationContext(),"Address Selected",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAddressRemoved(UserAddress.Data address) {
                removeAddressAPI(address.get_id());
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mAddressList.setLayoutManager(mLayoutManager);

        mAddressList.setAdapter(addressListRecyclerAdapter);






    }

    private void removeAddressAPI(String id) {

        String url ="http://139.59.70.142:3055/api/v1/user/address/"+id;

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.DELETE, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        fetchListOfAddress();
                        Toast.makeText(getApplicationContext(),"Address Removed",Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwZXJzb25JZCI6IjViMjM3MjllYjc3ZDBkMDAxNTU0NWU1OSIsInJvbGVJZCI6IjViMjM3MjllYjc3ZDBkMDAxNTU0NWU1YSIsImV4cGlyZXMiOjE1MjkxMzYxNTg1NTB9.QJLI7T-qkAhJyiHXDjffCClMZVTn8G8TV_SF2MN50Yg");
                return params;              }
        };
        queue.add(getRequest);
    }
}
