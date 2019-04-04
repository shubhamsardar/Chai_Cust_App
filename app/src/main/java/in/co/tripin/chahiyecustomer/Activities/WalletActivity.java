package in.co.tripin.chahiyecustomer.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.common.api.Api;
import com.google.gson.Gson;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dmax.dialog.SpotsDialog;
import in.co.tripin.chahiyecustomer.Managers.PreferenceManager;
import in.co.tripin.chahiyecustomer.Model.responce.InitiatePaymentResponce;
import in.co.tripin.chahiyecustomer.Model.responce.TransactionsResponce;
import in.co.tripin.chahiyecustomer.Model.responce.UserAddress;
import in.co.tripin.chahiyecustomer.R;
import in.co.tripin.chahiyecustomer.helper.Constants;
import in.co.tripin.chahiyecustomer.helper.DateFormatHelper;
import in.co.tripin.chahiyecustomer.helper.Logger;
import in.co.tripin.chahiyecustomer.paytm.Checksum;
import in.co.tripin.chahiyecustomer.paytm.Paytm;
import in.co.tripin.chahiyecustomer.retrofit.APIService;
import in.co.tripin.chahiyecustomer.services.WalletService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WalletActivity extends AppCompatActivity implements PaytmPaymentTransactionCallback {

    TextView mBalance;
    EditText mAmount;
    Button mAddMoney;
    ListView transactionListView;
    FloatingActionButton mRefresh;
    AwesomeValidation awesomeValidation;
    private PreferenceManager preferenceManager;
    private AlertDialog dialog;
    private RequestQueue queue;
    private String mRequestBody = "";
    private String mValidateRequestBody = "";
    private String mCancelRequestBody = "";
    private String mTxnId = "";
    private ArrayList<TransactionsResponce.Data> transactionList;
    private Gson gson;
    CustomAdapter customAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        setTitle("Your Wallet");
        init();
        setListners();
        FetchCurrentBalance();
        fetchTransactions();
        if(getIntent().getExtras()!=null){
            if(getIntent().getExtras().getString("money")!=null){
                mAmount.setText(getIntent().getExtras().getString("money"));
                mAmount.setSelection(mAmount.getText().length());

            }
        }
    }



    private void init() {
        mBalance = findViewById(R.id.balance);
        mAmount = findViewById(R.id.amount);
        mAddMoney = findViewById(R.id.add);
        mRefresh = findViewById(R.id.refresh);
        transactionListView = findViewById(R.id.transactionList);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.amount, RegexTemplate.NOT_EMPTY, R.string.err_amount);
        gson = new Gson();
        preferenceManager = PreferenceManager.getInstance(this);
        queue = Volley.newRequestQueue(this);
        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setCancelable(false)
                .setMessage("Loading")
                .build();
    }

    private void setListners() {
        mAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()>0){
                    mAddMoney.setText("Add ₹"+s.toString().trim());
                }else {
                    mAddMoney.setText("Add Money");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(awesomeValidation.validate()){
                    //Start Paytm Service
                    Toast.makeText(getApplicationContext(),"Fetch Checksum Hash",Toast.LENGTH_LONG).show();
//                    generateCheckSum();
                    generatePaymentBody();

                    FetchCheckSum();
                }
            }
        });

        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchCurrentBalance();
            }
        });
    }

    private void generatePaymentBody() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("type", "walletrecharge");
            jsonBody.put("totalAmount", mAmount.getText().toString().trim());
            jsonBody.put("email", "admin@chaiapp.in");
            jsonBody.put("referer", "chaiapp");
            mRequestBody = jsonBody.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void FetchCurrentBalance() {
        Logger.v("Fetching Balance..");
        dialog.show();
        final String url = Constants.BASE_URL+"api/v2/users/wallet/balance";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        dialog.dismiss();
                        Logger.v("Response: "+ response.toString());
                        try {
                            JSONObject data = response.getJSONObject("data");
                            String balance = data.getString("balance");
                            mBalance.setText("₹"+balance);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Logger.v("Error.Response: "+ error.toString());
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

    private void FetchCheckSum() {
        Logger.v("Fetching Checksum..");
        dialog.show();
        final String url = Constants.BASE_URL+"api/v2/initiatePayment";
//        final String url = "http://01c917b9.ngrok.io/"+"api/v2/initiatePayment";

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        dialog.dismiss();
                        Logger.v("Response: "+ response.toString());
                        InitiatePaymentResponce responcePojo = gson.fromJson(response.toString(),InitiatePaymentResponce.class);
                        mTxnId = responcePojo.getData().getTxnId();
                        Logger.v("TxnId:: "+mTxnId);

                        final Paytm paytm = new Paytm(
                                responcePojo.getData().getHashObject().getMID(),
                                responcePojo.getData().getHashObject().getCHANNELID(),
                                responcePojo.getData().getHashObject().getORDERID(),
                                responcePojo.getData().getHashObject().getCUSTID(),
                                responcePojo.getData().getHashObject().getTXNAMOUNT(),
                                responcePojo.getData().getHashObject().getWEBSITE(),
                                responcePojo.getData().getHashObject().getCALLBACKURL(),
                                responcePojo.getData().getHashObject().getINDUSTRYTYPEID(),
                                responcePojo.getData().getHashObject().getMOBILENO(),
                                responcePojo.getData().getHashObject().getEMAIL()
                        );
                        Logger.v("-- mid : "+paytm.getmId());
                        Logger.v("-- Order Id : "+paytm.getOrderId());
                        Logger.v("-- Cust Id : "+paytm.getCustId());
                        Logger.v("-- industry type id : "+paytm.getIndustryTypeId());
                        Logger.v("-- Channel Id : "+paytm.getChannelId());
                        Logger.v("-- txn_amount : "+paytm.getTxnAmount());
                        Logger.v("-- website : "+paytm.getWebsite());
                        Logger.v("-- callback url : "+paytm.getCallBackUrl());
                        Logger.v("-- CheckSum Hash : "+responcePojo.getData().getHash());
                        initializePaytmPayment(responcePojo.getData().getHash(), paytm);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Logger.v("Error.Response: "+ error.toString());
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

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    return null;
                }
            }
        };
        queue.add(getRequest);

    }

    private void ValidateTransaction() {
        Logger.v("Validating Trnasaction..");
        dialog.show();
        final String url = Constants.BASE_URL+"api/v1/validateResponse";
//        final String url = "http://9613004a.ngrok.io/"+"api/v1/validateResponse";


        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        dialog.dismiss();
                        Logger.v("Response: "+ response.toString());
                        FetchCurrentBalance();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Logger.v("Error.Response: "+ error.getMessage());
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

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return mValidateRequestBody == null ? null : mValidateRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mValidateRequestBody, "utf-8");
                    return null;
                }
            }
        };
        queue.add(getRequest);

    }

    private void CancelTransaction() {
        Logger.v("Cancel Trnasaction..");
        dialog.show();
        final String url = Constants.BASE_URL+"api/v1/cancelPayment";
//        final String url = "http://9613004a.ngrok.io/"+"api/v1/cancelPayment";


        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        dialog.dismiss();
                        Logger.v("Response: "+ response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Logger.v("Error.Response: "+ error.getMessage());
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

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return mValidateRequestBody == null ? null : mValidateRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mValidateRequestBody, "utf-8");
                    return null;
                }
            }
        };
        queue.add(getRequest);

    }

    private void initializePaytmPayment(String checksumHash, Paytm paytm) {

        //getting paytm service
        PaytmPGService Service = PaytmPGService.getStagingService();

        //use this when using for production
        //PaytmPGService Service = PaytmPGService.getProductionService();

        //creating a hashmap and adding all the values required
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("MID", paytm.getmId());
        paramMap.put("ORDER_ID", paytm.getOrderId());
        paramMap.put("CUST_ID", paytm.getCustId());
        paramMap.put("INDUSTRY_TYPE_ID", paytm.getIndustryTypeId());
        paramMap.put("CHANNEL_ID", paytm.getChannelId());
        paramMap.put("TXN_AMOUNT", paytm.getTxnAmount());
        paramMap.put("WEBSITE", paytm.getWebsite());
        paramMap.put("CALLBACK_URL", paytm.getCallBackUrl());
        paramMap.put("CHECKSUMHASH", checksumHash);

        //creating a paytm order object using the hashmap
        PaytmOrder order = new PaytmOrder(paramMap);

        //intializing the paytm service
        Service.initialize(order, null);

        //finally starting the payment transaction
        Service.startPaymentTransaction(this, true, true, this);

    }

    //all these overriden method is to detect the payment result accordingly
    @Override
    public void onTransactionResponse(Bundle bundle) {

        Toast.makeText(this, "Transaction Success", Toast.LENGTH_SHORT).show();
        Logger.v("onTransactionResponse: "+bundle.toString());
//        Logger.v("onTransactionResponse: "+bundle.toString().substring(8,bundle.toString().length()-2));
        JSONObject json = new JSONObject();
        Set<String> keys = bundle.keySet();
        for (String key : keys) {
            try {
                // json.put(key, bundle.get(key)); see edit below
                json.put(key, JSONObject.wrap(bundle.get(key)));
            } catch(JSONException e) {
                //Handle exception here
            }
        }
        Logger.v("onTransactionResponse Json: "+json.toString());

        PrepareValidateTransactionBody(json.toString());

    }

    private void PrepareValidateTransactionBody(String details) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("txnId", mTxnId);
            jsonBody.put("details", details);
            mValidateRequestBody = jsonBody.toString();
            ValidateTransaction();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void PrepareCancelTransactionBody() {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("txnId", mTxnId);
            jsonBody.put("status", 2);
            mCancelRequestBody = jsonBody.toString();
            CancelTransaction();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void networkNotAvailable() {
        Toast.makeText(this, "Network error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void clientAuthenticationFailed(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        Logger.v("clientAuthenticationFailed: "+s);

    }

    @Override
    public void someUIErrorOccurred(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        Logger.v("someUIErrorOccurred: "+s);

    }

    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        Logger.v("onErrorLoadingWebPage: "+s);
    }

    @Override
    public void onBackPressedCancelTransaction() {
        Toast.makeText(this, "Back Pressed", Toast.LENGTH_LONG).show();
//        PrepareCancelTransactionBody();
    }

    @Override
    public void onTransactionCancel(String s, Bundle bundle) {
        Toast.makeText(this, s + bundle.toString(), Toast.LENGTH_LONG).show();
        Logger.v("onTransactionCancel: "+s);
//        PrepareCancelTransactionBody();

    }

    public void fetchTransactions()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WalletService  walletService = retrofit.create(WalletService.class);
        Call<TransactionsResponce> call = walletService.getTransactions(preferenceManager.getAccessToken());
        call.enqueue(new Callback<TransactionsResponce>() {
            @Override
            public void onResponse(Call<TransactionsResponce> call, retrofit2.Response<TransactionsResponce> response) {
                if(response.isSuccessful())
                {
                    TransactionsResponce transactionsResponce = response.body();
                    transactionList = (ArrayList<TransactionsResponce.Data>) transactionsResponce.getData();

                    customAdapter = new CustomAdapter(WalletActivity.this, android.R.layout.simple_list_item_1, transactionList);
                    transactionListView.setAdapter(customAdapter);

                }
            }

            @Override
            public void onFailure(Call<TransactionsResponce> call, Throwable t) {

            }
        });

    }

    public class  CustomAdapter extends ArrayAdapter<TransactionsResponce.Data> {

        Context context;
        ArrayList<TransactionsResponce.Data> transactionList;
        View view;

        public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TransactionsResponce.Data> transactionList) {
            super(context, resource, transactionList);
            this.context = context;
            this.transactionList = transactionList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            view = getLayoutInflater().inflate(R.layout.custom_transactions, null);

            TextView tvAmount = (TextView) view.findViewById(R.id.tvAmount);
            TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
            TextView tvType = (TextView) view.findViewById(R.id.tvType);

                tvAmount.setText("₹ " + transactionList.get(position).getAmount());

            tvDate.setText(DateFormatHelper.getDisplayableDate(transactionList.get(position).getCreatedAt()));
            tvType.setText(transactionList.get(position).getType());


         return view;
        }
    }
}
