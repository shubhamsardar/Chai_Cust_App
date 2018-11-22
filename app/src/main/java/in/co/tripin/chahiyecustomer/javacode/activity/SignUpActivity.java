package in.co.tripin.chahiyecustomer.javacode.activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;

import android.widget.Button;
import android.widget.EditText;

import dmax.dialog.SpotsDialog;
import in.co.tripin.chahiyecustomer.Managers.PreferenceManager;
import in.co.tripin.chahiyecustomer.R;
import in.co.tripin.chahiyecustomer.Managers.AccountManager;
import in.co.tripin.chahiyecustomer.helper.Constants;
import in.co.tripin.chahiyecustomer.helper.Logger;

import android.support.design.widget.TextInputEditText;
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
import java.util.HashMap;
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


    private String mRequestBody = "";
    private String mToken = "";
    private RequestQueue queue;


    TextInputEditText mMobile;
    Button mSubmit;

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
    }


    public void init() {
        mMobile = findViewById(R.id.mobile);
        mSubmit = findViewById(R.id.btn_signup);

        mPin = findViewById(R.id.pin);
        mName = findViewById(R.id.name);
        mReenterPin = findViewById(R.id.pin_reenter);
        mSubmit = findViewById(R.id.btn_signup);

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
    }

    private void setValidations() {

        mAwesomeValidation.addValidation(this, R.id.mobile, RegexTemplate.TELEPHONE, R.string.err_mobile);
        mAwesomeValidation.addValidation(this, R.id.mobile, RegexTemplate.NOT_EMPTY, R.string.err_mobile);
        mAwesomeValidation.addValidation(this, R.id.name, RegexTemplate.NOT_EMPTY, R.string.err_name);
        mAwesomeValidation.addValidation(this, R.id.pin, RegexTemplate.NOT_EMPTY, R.string.error_invalid_password);
        mAwesomeValidation.addValidation(this, R.id.pin_reenter, R.id.pin, R.string.err_password_confirmation);


    }


    private void signUp() {

        if(mAwesomeValidation.validate()){




            if(mPin.getText().toString().length()==4){
                String mobile = mMobile.getText().toString();
                String pin = mPin.getText().toString();
                String name = mName.getText().toString();
                String reentPin = mReenterPin.getText().toString();

                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("name", name);
                    jsonBody.put("mobile", mobile);
                    jsonBody.put("pin", pin);
                    jsonBody.put("fcm", preferenceManager.getFCMId());
                    mRequestBody = jsonBody.toString();
                    Logger.v("Body : "+mRequestBody);
                    HitSignUpAPI();

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
            }else {
                Toast.makeText(getApplicationContext(),"Pin Length should be 4!",Toast.LENGTH_LONG).show();
            }


        }

    }

    private void HitSignUpAPI() {

        dialog.show();

        Logger.v("Signing Up");
        final String url = Constants.BASE_URL+"api/v1/user/signUp";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(),"OTP Sent!",Toast.LENGTH_LONG).show();
                        Logger.v("Response: "+response.toString());
                        Intent intent = new Intent(SignUpActivity.this,VerifyOTPActivity.class);
                        intent.putExtra("mobile",mMobile.getText().toString().trim());
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        try {
                            String s = new String(error.networkResponse.data, "UTF-8");
                            Logger.v("Error.Response: "+ s);
                            JSONObject jsonObject = new JSONObject(s);
                            Double code  = jsonObject.getDouble("errorCode");
                            if(code==200){
                                // go to log in
                                Toast.makeText(getApplicationContext(),"User Already Exists, Log in!",Toast.LENGTH_LONG).show();
                                Intent i = new Intent(SignUpActivity.this,LoginActivity.class);
                                i.putExtra("mobile",mMobile.getText().toString().trim());
                                startActivity(i);
                                finish();
                            }

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(),"Try Again!",Toast.LENGTH_LONG).show();

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
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(vieww.getWindowToken(), 0);
        }
        finish();
    }

    private void getDataFromForm() {



    }

}

