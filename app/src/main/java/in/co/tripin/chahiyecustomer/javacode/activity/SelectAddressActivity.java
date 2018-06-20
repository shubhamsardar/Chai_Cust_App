package in.co.tripin.chahiyecustomer.javacode.activity;

import android.app.Activity;
import android.app.AlertDialog;
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

import dmax.dialog.SpotsDialog;
import in.co.tripin.chahiyecustomer.Adapters.AddressListRecyclerAdapter;
import in.co.tripin.chahiyecustomer.Adapters.AddresslistInteractionCallback;
import in.co.tripin.chahiyecustomer.Managers.PreferenceManager;
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
    private PreferenceManager preferenceManager;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        setTitle("Select Address");
        mUserAddressManager = new UserAddressManager(this);
        queue = Volley.newRequestQueue(this);
        gson = new Gson();
        init();
        preferenceManager = PreferenceManager.getInstance(this);
        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setCancelable(false)
                .setMessage("Loading")
                .build();


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
        dialog.show();
        final String url = "http://139.59.70.142:3055/api/v1/users/address?userAddress=ALL";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        userAddress = gson.fromJson(response.toString(), UserAddress.class);
                        if (userAddress != null) {
                            setAdapter(userAddress.getData());
                            dialog.dismiss();
                            if (userAddress.getData().length > 0) {
                                mLoadingMsg.setVisibility(View.GONE);
                            } else {
                                mLoadingMsg.setVisibility(View.VISIBLE);
                                mLoadingMsg.setText("Empty Addresses, Add a new one Now!");
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Log.d("Error.Response", error.toString());
                        Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_LONG).show();
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

    private void setAdapter(final UserAddress.Data[] data) {

        addressListRecyclerAdapter = new AddressListRecyclerAdapter(data, new AddresslistInteractionCallback() {
            @Override
            public void onAddressSelected(UserAddress.Data data) {
                Intent intent = new Intent();
                if(data!=null){
                    intent.putExtra("address",data);
                    setResult(Activity.RESULT_OK, intent);
                    finish();//finishing activity
                }else {
                    intent.putExtra("address",data);
                    setResult(Activity.RESULT_CANCELED, intent);
                    finish();//finishing activity
                }


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

        String url = "http://139.59.70.142:3055/api/v1/user/address/" + id;
        dialog.show();

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.DELETE, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        dialog.dismiss();
                        Log.d("Response", response.toString());
                        fetchListOfAddress();
                        Toast.makeText(getApplicationContext(), "Address Removed", Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Log.d("Error.Response", error.toString());
                        Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();

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
}
