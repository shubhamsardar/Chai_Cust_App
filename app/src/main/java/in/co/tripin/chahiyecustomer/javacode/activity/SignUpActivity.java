package in.co.tripin.chahiyecustomer.javacode.activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import android.widget.Button;
import android.widget.EditText;

import dmax.dialog.SpotsDialog;
import in.co.tripin.chahiyecustomer.Activities.FavouriteTapri;
import in.co.tripin.chahiyecustomer.Managers.PreferenceManager;
import in.co.tripin.chahiyecustomer.Model.CompanyModel;
import in.co.tripin.chahiyecustomer.Model.OfficeDataModel;
import in.co.tripin.chahiyecustomer.Model.Requests.OfficeRequestBody;
import in.co.tripin.chahiyecustomer.R;
import in.co.tripin.chahiyecustomer.Managers.AccountManager;
import in.co.tripin.chahiyecustomer.dataproviders.CommonResponse;
import in.co.tripin.chahiyecustomer.helper.Constants;
import in.co.tripin.chahiyecustomer.helper.Logger;
import in.co.tripin.chahiyecustomer.services.OfficeService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.support.design.widget.TextInputEditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
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
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class SignUpActivity extends AppCompatActivity {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private AccountManager mAccountManager;
    private AwesomeValidation mAwesomeValidation;
    private PreferenceManager preferenceManager;
    private AlertDialog dialog;
    private Spinner spinner ;
    private TextView textViewCorporate;


    private String mRequestBody = "";
    private String mToken = "";
    private RequestQueue queue;
    private String officeId ;

    TextInputEditText mMobile;
    Button mSubmit;
    ArrayList<String >companyList;
    ArrayList<CompanyModel>companyModelList;
    ArrayList<OfficeDataModel> officeDataModelList;
    TextInputEditText mPin;
    TextInputEditText mReenterPin;
    TextInputEditText mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_details);

        mAccountManager = new AccountManager(this);
        queue = Volley.newRequestQueue(this);
        preferenceManager = PreferenceManager.getInstance(this);
        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setCancelable(false)
                .setMessage("Signing Up")
                .build();
        init();
        setListners();

        companyList= new ArrayList<>();
        companyModelList = new ArrayList<>();

        companyList.add("COMPANY 1");
        companyList.add("COMPANY 2");
        companyList.add("COMPANY 3");
        companyList.add("COMPANY 4");

        for (int i = 0;i<companyList.size();i++)
        {
            companyModelList.add(new CompanyModel(companyList.get(i)));
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //officeId = officeDataModelList.get(i).get_id();

               OfficeDataModel officeDataModel = (OfficeDataModel) spinner.getSelectedItem();
                officeId = officeDataModel.get_id();
                Log.d("ID",officeId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }


    public void init() {
        mMobile = findViewById(R.id.mobile);
        mSubmit = findViewById(R.id.btn_signup);

        mPin = findViewById(R.id.pin);
        mName = findViewById(R.id.name);
        mReenterPin = findViewById(R.id.pin_reenter);
        mSubmit = findViewById(R.id.btn_signup);
        textViewCorporate= (TextView)findViewById(R.id.textViewCorporate);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setVisibility(View.GONE);

        mAwesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        setValidations();

    }


    private void setListners() {

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        textViewCorporate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(View.VISIBLE);
//                String office = spinner.getSelectedItem().toString();
//                Log.d("S_Office",office);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://03452e3c.ngrok.io")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                OfficeService officeService = retrofit.create(OfficeService.class);
                Call<OfficeRequestBody> call = officeService.getOffice();
                call.enqueue(new Callback<OfficeRequestBody>() {
                    @Override
                    public void onResponse(Call<OfficeRequestBody> call, retrofit2.Response<OfficeRequestBody> response) {
                        if(response.isSuccessful())
                        {
                            OfficeRequestBody officeRequestBody =  response.body();
                            officeDataModelList = new ArrayList<>();
                            for (int i =0; i<officeRequestBody.getData().size();i++) {
                                String officeName = officeRequestBody.getData().get(i).getName();
                                String officeId = officeRequestBody.getData().get(i).get_id();
                                officeDataModelList.add(new OfficeDataModel(officeId,officeName));
                                Log.d("Office", officeName);
                                Log.d("ID",officeId);

                            }


                            CustomAdapter customAdapter = new CustomAdapter(SignUpActivity.this,android.R.layout.simple_list_item_1,officeDataModelList);
                            spinner.setAdapter(customAdapter);
                        }
                        else {
                            String  err = String.valueOf(response.errorBody());
                            Log.d("ERR",err);
                        }
                    }

                    @Override
                    public void onFailure(Call<OfficeRequestBody> call, Throwable t) {
                        Log.d("Fail",t.getMessage());

                    }
                });

            }
        });
    }

    private void setValidations() {

        mAwesomeValidation.addValidation(this, R.id.mobile, RegexTemplate.TELEPHONE, R.string.err_mobile);
        mAwesomeValidation.addValidation(this, R.id.mobile, RegexTemplate.NOT_EMPTY, R.string.err_mobile);
        mAwesomeValidation.addValidation(this, R.id.name, RegexTemplate.NOT_EMPTY, R.string.err_name);
        mAwesomeValidation.addValidation(this, R.id.pin, RegexTemplate.NOT_EMPTY, R.string.error_invalid_password);
        mAwesomeValidation.addValidation(this, R.id.pin_reenter, R.id.pin, R.string.err_password_confirmation);


    }


    private void signUp() {

        if (mAwesomeValidation.validate()) {


            if (mPin.getText().toString().length() == 4) {
                String mobile = mMobile.getText().toString();
                String pin = mPin.getText().toString();
                String name = mName.getText().toString();
                String reentPin = mReenterPin.getText().toString();


                JSONObject jsonBody = new JSONObject();
                try {
                    if(officeId!=null) {
                        jsonBody.put("name", name);
                        jsonBody.put("mobile", mobile);
                        jsonBody.put("pin", pin);
                        jsonBody.put("fcm", preferenceManager.getFCMId());
                        jsonBody.put("Office",officeId);
                        mRequestBody = jsonBody.toString();
                        Logger.v("Body : " + mRequestBody);
                        HitSignUpAPI();
                    }else
                    {
                        jsonBody.put("name", name);
                        jsonBody.put("mobile", mobile);
                        jsonBody.put("pin", pin);
                        jsonBody.put("fcm", preferenceManager.getFCMId());
                        mRequestBody = jsonBody.toString();
                        Logger.v("Body : " + mRequestBody);
                        HitSignUpAPI();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                mAccountManager.getSignUpResult(new AccountManager.SignUpListener() {
//                    @Override
//                    public void onSuccess(String message) {
//                        Logger.v("SignUp OnSuccess :" + message);
//                        Toast.makeText(SignUpActivity.this, "onSuccess", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFailed(String message) {
//                        Logger.v("SignUp Failed :" + message);
//                        Toast.makeText(SignUpActivity.this, "onFailed ", Toast.LENGTH_SHORT).show();
//                    }
//                }, name, mobile, pin);
            } else {
                Toast.makeText(getApplicationContext(), "Pin Length should be 4!", Toast.LENGTH_LONG).show();
            }


        }

    }

    private void HitSignUpAPI() {

        dialog.show();

        Logger.v("Signing Up");
        final String url = Constants.BASE_URL+ "api/v1/user/signUp";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "OTP Sent!", Toast.LENGTH_LONG).show();
                        Logger.v("Response: " + response.toString());
                        Intent intent = new Intent(SignUpActivity.this, VerifyOTPActivity.class);
                        intent.putExtra("mobile", mMobile.getText().toString().trim());
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        try {
                            String s = new String(error.networkResponse.data, "UTF-8");
                            Logger.v("Error.Response: " + s);
                            JSONObject jsonObject = new JSONObject(s);
                            Double code = jsonObject.getDouble("errorCode");
                            if (code == 200) {
                                // go to log in
                                Toast.makeText(getApplicationContext(), "User Already Exists, Log in!", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                                i.putExtra("mobile", mMobile.getText().toString().trim());
                                startActivity(i);
                                finish();
                            }

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(), "Try Again!", Toast.LENGTH_LONG).show();

                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
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

    public void back(View view) {
        View vieww = this.getCurrentFocus();
        if (vieww != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(vieww.getWindowToken(), 0);
        }
        finish();
    }

    private void getDataFromForm() {


    }

    public class CustomAdapter extends ArrayAdapter<OfficeDataModel>
    {
        Context context;
        ArrayList<OfficeDataModel > officeDataModelList = new ArrayList<>();

        public CustomAdapter(@NonNull Context context, int resource,ArrayList<OfficeDataModel> officeDataModelList) {
            super(context, resource, officeDataModelList);
            this.context = context;
            this.officeDataModelList = officeDataModelList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view =  super.getView(position, convertView, parent);

            TextView textView = (TextView)view.findViewById(android.R.id.text1);
            textView.setTextColor(getResources().getColor(R.color.white));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
            textView.setGravity(Gravity.CENTER);
            textView.setText(officeDataModelList.get(position).getName());
            Typeface typeface = ResourcesCompat.getFont(context, R.font.source_sans_pro_semibold);
            textView.setTypeface(typeface);

            return view;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            convertView = LayoutInflater.from(SignUpActivity.this).inflate(
                    R.layout.custom_company_spinner, parent, false);
            TextView textViewCompany = (TextView) convertView.findViewById(R.id.textViewCompany);
            textViewCompany.setText(officeDataModelList.get(position).getName());


            return convertView;
        }
    }


}

