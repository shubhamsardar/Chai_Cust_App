package in.co.tripin.chahiyecustomer.javacode.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import in.co.tripin.chahiyecustomer.Activities.FavouriteTapri;
import in.co.tripin.chahiyecustomer.Activities.MainLandingMapActivity;
import in.co.tripin.chahiyecustomer.Managers.AccountManager;
import in.co.tripin.chahiyecustomer.Managers.PreferenceManager;
import in.co.tripin.chahiyecustomer.Model.responce.LogInResponce;
import in.co.tripin.chahiyecustomer.R;
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
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private AccountManager  mAccountManager;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private String mobile;
    TextInputEditText mMobile;
    TextInputEditText mPin;
    Button mSubmit;
    private RequestQueue queue;
    private AlertDialog dialog;
    private String mRequestBody = "";
    private String mToken = "";
    private PreferenceManager preferenceManager;
    AwesomeValidation awesomeValidation;
    Gson gson;


    PreferenceManager mPreferenceManger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_details);
        mAccountManager = new AccountManager(this);
        gson = new Gson();
        mPreferenceManger = PreferenceManager.getInstance(this);
        queue = Volley.newRequestQueue(this);
        preferenceManager = PreferenceManager.getInstance(this);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.mobile, RegexTemplate.NOT_EMPTY, R.string.err_mobile);
        awesomeValidation.addValidation(this, R.id.pin, RegexTemplate.NOT_EMPTY, R.string.error_invalid_password);
        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setCancelable(false)
                .setMessage("Signing In")
                .build();


        mMobile = findViewById(R.id.mobile);
        mPin = findViewById(R.id.pin);
        mSubmit = findViewById(R.id.btn_login);

        if(getIntent().getExtras()!=null){
            if(getIntent().getExtras().getString("mobile")!=null){
                mobile = getIntent().getExtras().getString("mobile");
                mMobile.setText(mobile);
            }else {
                //to mobile
            }
        }else {
            //no bundle
        }

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate()){
                    singIn();
                }
            }
        });
    }

    public void singIn() {
        String mobile = mMobile.getText().toString();
        String pin = mPin.getText().toString();
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("mobile", mobile);
            jsonBody.put("pin", pin);
            jsonBody.put("regToken", preferenceManager.getFCMId());
            mRequestBody = jsonBody.toString();
            HitSignInAPI();

        } catch (JSONException e) {
            e.printStackTrace();
        }


//        mAccountManager.getSignInResult(new AccountManager.SignInListener() {
//            @Override
//            public void onSuccess(String message) {
//                mPreferenceManger.setUserId("1234");
//                startMainLandingMapActivity();
//                Toast.makeText(LoginActivity.this, "Success",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailed(String message) {
//                Toast.makeText(LoginActivity.this, "Mobile or pin wrong",Toast.LENGTH_SHORT).show();
//            }
//        }, mobile, pin);
    }

    private void startMainLandingMapActivity() {
        startActivity(new Intent(LoginActivity.this, MainLandingMapActivity.class));
    }

    private void HitSignInAPI() {

        dialog.show();

        Logger.v("Signing In..");
        final String url = Constants.BASE_URL+"api/v1/user/signIn";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        dialog.dismiss();
                        Logger.v("Response: "+response.toString());
                        try {
                            String s = response.getString("status");
                            if(s.equals("Verification Pending")){
                                HitResendOTPapi();

                            }else {

                                LogInResponce logInResponce = gson.fromJson(response.toString(),LogInResponce.class);

                                Boolean isRole = false;
                                if( logInResponce.getData().getRoles()!=null){
                                    for(LogInResponce.Data.Role role : logInResponce.getData().getRoles()){
                                        if(role.getRoleType().equals("10012")){
                                            isRole = true;
                                        }
                                    }
                                }


                                if(isRole){
                                    Toast.makeText(getApplicationContext(),"Logged In!",Toast.LENGTH_LONG).show();
                                    String mobile = response.getJSONObject("data").getString("mobile");
                                    preferenceManager.setMobileNo(mobile);
                                    String favoriteTapriId = response.getJSONObject("data").getJSONObject("favouriteTapri").getString("_id");
                                    String favoriteTapriName=response.getJSONObject("data").getJSONObject("favouriteTapri").getString("name");
                                    preferenceManager.setFavTapriId(favoriteTapriId);
                                    preferenceManager.setFavTapriName(favoriteTapriName);
                                    if(favoriteTapriId!=null) {
                                        Intent intent = new Intent(LoginActivity.this, FavouriteTapri.class);
                                        intent.putExtra(FavouriteTapri.FAV_TAPRI_ID,favoriteTapriId);
                                        intent.putExtra(FavouriteTapri.FAV_TAPRI_NAME,favoriteTapriName);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else
                                    {
                                        Intent intent = new Intent(LoginActivity.this, MainLandingMapActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }else {
                                    Toast.makeText(getApplicationContext(),"Not a User! Sign In",Toast.LENGTH_LONG).show();
                                    preferenceManager.setAccessToken(null);
                                }

                            }

                        } catch (JSONException e) {
                            Intent intent = new Intent(LoginActivity.this, MainLandingMapActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Logger.v("Error.Response: "+ error.toString());
                        Toast.makeText(getApplicationContext(),"Try Again!",Toast.LENGTH_LONG).show();
                        try {
                            if(error.networkResponse!=null){
                                String s = new String(error.networkResponse.data, "UTF-8");
                                Logger.v("Error.Response: "+ s);
                                JSONObject jsonObject = new JSONObject(s);
                                Double code  = jsonObject.getDouble("errorCode");
                            }

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                mToken = response.headers.get("token");
                Logger.v("Access Token... :"+response.headers.get("token"));
                preferenceManager.setAccessToken(mToken);
                return super.parseNetworkResponse(response);
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

    private void HitResendOTPapi() {
        dialog.show();

        Logger.v("Resending OTP...");
        final String url = Constants.BASE_URL+"api/v1/otp/send";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Verification is Pending!",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this,VerifyOTPActivity.class);
                        intent.putExtra("mobile",mMobile.getText().toString().trim());
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Logger.v("Error.Response: "+ error.toString());
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

    public void gotoforgotpinscreen(View view) {

        startActivity(new Intent(LoginActivity.this,ForgotPinActivity.class));
    }
}
