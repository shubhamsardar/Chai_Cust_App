package in.co.tripin.chahiyecustomer.javacode.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import in.co.tripin.chahiyecustomer.Adapters.SelectedItemsRecyclerAdapter;
import in.co.tripin.chahiyecustomer.Managers.PreferenceManager;
import in.co.tripin.chahiyecustomer.Model.OrderSummeryPOJO;
import in.co.tripin.chahiyecustomer.Model.responce.TapriMenuResponce;
import in.co.tripin.chahiyecustomer.Model.responce.UserAddress;
import in.co.tripin.chahiyecustomer.R;
import in.co.tripin.chahiyecustomer.helper.Constants;
import in.co.tripin.chahiyecustomer.helper.Logger;

public class OrderSummeryActivity extends AppCompatActivity {

    private OrderSummeryPOJO orderSummeryPOJO;
    private SelectedItemsRecyclerAdapter selectedItemsRecyclerAdapter;
    private PreferenceManager preferenceManager;
    private Gson gson;
    private RequestQueue queue;


    //UI Elements
    private TextView mTapriName;
    private TextView mTotalCost;
    private TextView mAddress;
    private TextView mPaymentMethod;
    private RecyclerView mSelctedItemsList;
    private TextView proceedtopay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summery);
        setTitle("Order Summery");
        preferenceManager = PreferenceManager.getInstance(this);
        gson = new Gson();
        queue = Volley.newRequestQueue(this);


        if (getIntent().getExtras() == null) {
            finish();
        } else {
            orderSummeryPOJO = (OrderSummeryPOJO) getIntent().getSerializableExtra("ordersummery");
        }

        init();
        setListners();

        setScreenUI();


    }

    private void setScreenUI() {

        mTapriName.setText(orderSummeryPOJO.getmTapriName().toUpperCase());
        mTotalCost.setText("₹" + orderSummeryPOJO.getmTotalCost());
        mAddress.setText(orderSummeryPOJO.getmAddress().getNickname().toUpperCase() + "\n" + orderSummeryPOJO.getFullAddressString());
        mPaymentMethod.setText(orderSummeryPOJO.getmPaymentMethod());
        selectedItemsRecyclerAdapter = new SelectedItemsRecyclerAdapter(orderSummeryPOJO.getmItems());
        mSelctedItemsList.setAdapter(selectedItemsRecyclerAdapter);
        if (orderSummeryPOJO.getmPaymentMethod().equals("COD")) {
            proceedtopay.setText("PLACE ORDER NOW");
        }


    }

    private void setListners() {
        proceedtopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hit the Place order API
                HitPlaceOrderRequestAPI();

            }
        });
    }


    private void init() {

        mAddress = findViewById(R.id.addressall);
        mPaymentMethod = findViewById(R.id.paymentmethod);
        mTapriName = findViewById(R.id.tv_header);
        mTotalCost = findViewById(R.id.totalcost);
        mSelctedItemsList = findViewById(R.id.selected_items_list);
        proceedtopay = findViewById(R.id.proceedtopay);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mSelctedItemsList.setLayoutManager(mLayoutManager);


    }

    private void HitPlaceOrderRequestAPI() {


        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tapriId",orderSummeryPOJO.getmTapriId());
            jsonObject.put("paymentType",orderSummeryPOJO.getmPaymentMethod());
            jsonObject.put("totalAmount",Double.parseDouble(orderSummeryPOJO.getmTotalCost()));
            JSONArray jsonArray = new JSONArray();
            for(int i=0;i<orderSummeryPOJO.getmItems().size();i++){
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("itemName",orderSummeryPOJO.getmItems().get(i).getName());
                jsonObject1.put("amount",Double.parseDouble(orderSummeryPOJO.getmItems().get(i).getRate()));
                jsonObject1.put("itemId",orderSummeryPOJO.getmItems().get(i).get_id());
                jsonObject1.put("quantity",orderSummeryPOJO.getmItems().get(i).getQuantity());
                jsonArray.put(jsonObject1);
            }
            jsonObject.put("details",jsonArray);
            jsonObject.put("addressId",orderSummeryPOJO.getmAddress().get_id());

            Logger.v("Body :"+jsonObject.toString());
            apiCall(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
            Logger.v("Request Body Parsing Error");
        }



    }

    private void apiCall(final JSONObject jsonObject) {


        final String url = Constants.BASE_URL+"api/v2/initiateOrder";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Logger.v("Response: "+ response.toString());
                        Toast.makeText(getApplicationContext(),"Order Placed Successfully!",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(OrderSummeryActivity.this,OrderHistoryActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logger.v("Error.Response :"+ error.toString());
                        Toast.makeText(getApplicationContext(),"Sever Error!",Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", preferenceManager.getAccessToken());
                return params;
            }


            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return jsonObject.toString() == null ? null : jsonObject.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", jsonObject.toString(), "utf-8");
                    return null;
                }
            }
        };

        queue.add(getRequest);
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_order_summery, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_cancel) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
