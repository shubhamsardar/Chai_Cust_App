package in.co.tripin.chahiyecustomer.javacode.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.renderscript.Double3;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import in.co.tripin.chahiyecustomer.Adapters.ItemsListAdapter;
import in.co.tripin.chahiyecustomer.Managers.PreferenceManager;
import in.co.tripin.chahiyecustomer.Model.OrderSummeryPOJO;
import in.co.tripin.chahiyecustomer.Model.responce.TapriMenuResponce;
import in.co.tripin.chahiyecustomer.Model.responce.UserAddress;
import in.co.tripin.chahiyecustomer.R;
import in.co.tripin.chahiyecustomer.helper.Logger;

public class TapriDetailsActivity extends AppCompatActivity {

    private PreferenceManager preferenceManager;

    private String tapriId = "";
    private String tapriName = "";
    private UserAddress.Data address;
    private RequestQueue queue;
    private TapriMenuResponce tapriMenuResponce;
    private Gson gson;

    private RecyclerView mSnacksList;
    private RecyclerView mBeveragesList;
    private RecyclerView mExtrasList;
    private RecyclerView mChaihiyehList;
    private View mAddressInclude;
    private ImageView mAddressCancel;

    private TextView mAddressNick;
    private TextView mAddressFull;
    private TextView mProceedToPay;
    private RadioGroup mPaymentType;


    private ItemsListAdapter mSnacksAdapter;
    private ItemsListAdapter mBeveragesAdapter;
    private ItemsListAdapter mExtrasAdapter;
    private ItemsListAdapter mChahiyehAdapter;

    //private TapriMenuResponce.Data.Item[] mSnacks,mBeverages,mExtras,mChaihiyeh;

    private LinearLayout mItemsToggleHeader;
    private LinearLayout mAddresseHeader;

    private LinearLayout mChaihiyehll, mBeveragesll, mSnacksll, mExtrasll;
    private TextView mItemsToggleText;
    private LinearLayout mItemsList;
    private AlertDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tapri_details);
        setTitle("Tapri Details");
        queue = Volley.newRequestQueue(this);
        gson = new Gson();

        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setCancelable(false)
                .setMessage("Fetching Menu")
                .build();

        init();
        setUpView();
        setListners();

        //set title and id from intent
        if (getIntent().getExtras() != null) {
            tapriName = getIntent().getExtras().getString("tapri_name");
            setTitle(tapriName);
            tapriId = getIntent().getExtras().getString("tapri_id");
            if (tapriId.isEmpty()) {
                finish();
            } else {
                //call tapri items api
                hitTapriItemsListAPI();
            }
        }



    }

    private void setListners() {
        mItemsToggleHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mItemsList.getVisibility() != View.GONE) {
                    mItemsList.setVisibility(View.GONE);
                    mItemsToggleText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_down_black_24dp, 0, 0, 0);
                } else {
                    mItemsList.setVisibility(View.VISIBLE);
                    mItemsToggleText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_up_black_24dp, 0, 0, 0);

                }

            }
        });

        mAddresseHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(TapriDetailsActivity.this, SelectAddressActivity.class), 1);
            }
        });

        mAddressInclude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(TapriDetailsActivity.this, SelectAddressActivity.class), 1);
            }
        });

        mProceedToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<TapriMenuResponce.Data.Item> mItems = new ArrayList<>();
                double cost = 0;



                for(int i=0;i<mChahiyehAdapter.data.length;i++){

                    if(mChahiyehAdapter.data[i].getQuantity()!=0){

                        mItems.add(mChahiyehAdapter.data[i]);
                        Double rate = 0.0;
                        try {
                            rate = Double.parseDouble(mChahiyehAdapter.data[i].getRate());
                        } catch (NumberFormatException e) {
                            Logger.v("Rate Invalid: cant convert to double");
                        }
                        cost = cost + (mChahiyehAdapter.data[i].getQuantity() * rate);
                    }

                }
                for(int i=0;i<mExtrasAdapter.data.length;i++){

                    if(mExtrasAdapter.data[i].getQuantity()!=0){

                        mItems.add(mExtrasAdapter.data[i]);
                        Double rate = 0.0;
                        try {
                            rate = Double.parseDouble(mExtrasAdapter.data[i].getRate());
                        } catch (NumberFormatException e) {
                            Logger.v("Rate Invalid: cant convert to double");
                        }
                        cost = cost + (mExtrasAdapter.data[i].getQuantity() * rate);
                    }
                }
                for(int i=0;i<mSnacksAdapter.data.length;i++){

                    if(mSnacksAdapter.data[i].getQuantity()!=0){

                        mItems.add(mSnacksAdapter.data[i]);
                        Double rate = 0.0;
                        try {
                            rate = Double.parseDouble(mSnacksAdapter.data[i].getRate());
                        } catch (NumberFormatException e) {
                            Logger.v("Rate Invalid: cant convert to double");
                        }
                        cost = cost + (mSnacksAdapter.data[i].getQuantity() * rate);
                    }
                }
                for(int i=0;i<mBeveragesAdapter.data.length;i++){

                    if(mBeveragesAdapter.data[i].getQuantity()!=0){

                        mItems.add(mBeveragesAdapter.data[i]);
                        Double rate = 0.0;
                        try {
                            rate = Double.parseDouble(mBeveragesAdapter.data[i].getRate());
                        } catch (NumberFormatException e) {
                            Logger.v("Rate Invalid: cant convert to double");
                        }
                        cost = cost + (mBeveragesAdapter.data[i].getQuantity() * rate);
                    }
                }


                if(address==null){
                    Toast.makeText(getApplicationContext(),"Address Required!",Toast.LENGTH_LONG).show();
                }else {

                    if(mPaymentType.getCheckedRadioButtonId() == R.id.radiocod){

                    }
                    OrderSummeryPOJO orderSummeryPOJO = new OrderSummeryPOJO(tapriId,tapriName,Double.toString(cost),address,"COD",mItems);

                    if(orderSummeryPOJO.getmItems().size()!=0){
                        Intent i = new Intent(TapriDetailsActivity.this,OrderSummeryActivity.class);
                        i.putExtra("ordersummery",orderSummeryPOJO);
                        startActivity(i);
                    }else {
                        Toast.makeText(getApplicationContext(),"Select Some Items!",Toast.LENGTH_LONG).show();

                    }

                }



            }
        });
    }


    private void init() {
        Logger.v("setting UI..");

        mBeveragesList = findViewById(R.id.beverages);
        mSnacksList = findViewById(R.id.snacks);
        mExtrasList = findViewById(R.id.extras);
        mChaihiyehList = findViewById(R.id.chaihiyeh);

        mAddressNick = findViewById(R.id.addressnick);
        mAddressNick.setText("Your Default Address");
        mAddressFull = findViewById(R.id.fulladdress);


        mBeveragesll = findViewById(R.id.beveragesll);
        mSnacksll = findViewById(R.id.snacksll);
        mChaihiyehll = findViewById(R.id.chaihiyehll);
        mExtrasll = findViewById(R.id.extrasll);

        mItemsToggleHeader = findViewById(R.id.itrms_header);
        mAddresseHeader = findViewById(R.id.address_header);

        mItemsList = findViewById(R.id.items_lists);
        mItemsToggleText = findViewById(R.id.items_title);
        mAddressInclude = findViewById(R.id.address_include);
        mAddressCancel = findViewById(R.id.remove);

        mProceedToPay = findViewById(R.id.proceedtopay);
        mPaymentType = findViewById(R.id.payment_type);




    }

    private void setUpView() {

//        mSnacks = new TapriMenuResponce.Data.Item[]{};
//        mBeverages = new TapriMenuResponce.Data.Item[]{};
//        mExtras = new TapriMenuResponce.Data.Item[]{};


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mSnacksList.setLayoutManager(mLayoutManager);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        mBeveragesList.setLayoutManager(mLayoutManager1);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        mExtrasList.setLayoutManager(mLayoutManager2);
        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(getApplicationContext());
        mChaihiyehList.setLayoutManager(mLayoutManager3);

        preferenceManager = PreferenceManager.getInstance(this);

        mAddressCancel.setVisibility(View.GONE);
        if(preferenceManager.getDefaultAddress() == null){
            mAddressInclude.setVisibility(View.GONE);
        }else {
            mAddressInclude.setVisibility(View.VISIBLE);
            address = gson.fromJson(preferenceManager.getDefaultAddress(),UserAddress.Data.class);
            mAddressFull.setText(address.getFullAddressString());

        }

    }

    private void hitTapriItemsListAPI() {

        Logger.v("getting menu...");
        dialog.show();
        final String url = "http://139.59.70.142:3055/api/v1/tapri/" + tapriId + "/items";

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Toast.makeText(getApplicationContext(), "List Fetched!", Toast.LENGTH_SHORT).show();
                        Logger.v("Response: " + response.toString());
                        tapriMenuResponce = new Gson().fromJson(response.toString(), TapriMenuResponce.class);
                        if (tapriMenuResponce != null) {
                            setItems(tapriMenuResponce.getData());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Logger.v("Error.Response: "+ error.toString());
                        Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();

                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("token", preferenceManager.getAccessToken());
                return params;
            }


            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }


        };
        queue.add(getRequest);
    }

    private void setItems(TapriMenuResponce.Data data) {

        setListVisiblity(data);


        mBeveragesAdapter = new ItemsListAdapter(this,data.getBeverages());
        mExtrasAdapter = new ItemsListAdapter(this,data.getExtra());
        mSnacksAdapter = new ItemsListAdapter(this,data.getSnacks());
        mChahiyehAdapter = new ItemsListAdapter(this,data.getChaihiyeh());

        //Logger.v("in Extras: "+mExtras[0].toString());

        mBeveragesList.setAdapter(mBeveragesAdapter);
        mSnacksList.setAdapter(mSnacksAdapter);
        mExtrasList.setAdapter(mExtrasAdapter);
        mChaihiyehList.setAdapter(mChahiyehAdapter);


        //        mSnacksAdapter.notifyDataSetChanged();
//        mBeveragesAdapter.notifyDataSetChanged();
//        mExtrasAdapter.notifyDataSetChanged();


        dialog.dismiss();



    }

    private void setListVisiblity(TapriMenuResponce.Data data) {

        mBeveragesll.setVisibility(View.VISIBLE);
        mChaihiyehll.setVisibility(View.VISIBLE);
        mSnacksll.setVisibility(View.VISIBLE);
        mExtrasll.setVisibility(View.VISIBLE);

        if (data.getBeverages().length == 0) {
            mBeveragesll.setVisibility(View.GONE);
        }
        if (data.getChaihiyeh().length == 0) {
            mChaihiyehll.setVisibility(View.GONE);
        }
        if (data.getExtra().length == 0) {
            mExtrasll.setVisibility(View.GONE);
        }
        if (data.getSnacks().length == 0) {
            mSnacksll.setVisibility(View.GONE);
        }
    }

    // Call Back method  to get the Message form other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        Logger.v("On Activity Result : result code: " + resultCode + " request code:" + requestCode);

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 1){
                Toast.makeText(getApplicationContext(),"Address Selected!",Toast.LENGTH_SHORT).show();

                address = (UserAddress.Data) intent.getSerializableExtra("address");
                String fulladdressstring = address.getLandmark()+", "
                        +address.getFlatSociety()+", "
                        +address.getAddressLine1()+", "
                        +address.getAddressLine2()+", "
                        +address.getCity()+", "
                        +address.getCountry();

                mAddressFull.setText(fulladdressstring);
                mAddressNick.setText(address.getNickname());
                preferenceManager.setDefaultAddress(gson.toJson(address));
                mAddressInclude.setVisibility(View.VISIBLE);
                mAddressInclude.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.color.colorHighlight));
                Logger.v("selected address is: " + fulladdressstring);
            }
        }else {
            Toast.makeText(getApplicationContext(),"Address not selected!",Toast.LENGTH_SHORT).show();
        }




    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_taprimenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_info) {
        }

        return super.onOptionsItemSelected(item);
    }
}
