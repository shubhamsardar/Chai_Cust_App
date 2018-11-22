package in.co.tripin.chahiyecustomer.javacode.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import com.chaos.view.PinView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import in.co.tripin.chahiyecustomer.R;
import in.co.tripin.chahiyecustomer.helper.Constants;
import in.co.tripin.chahiyecustomer.helper.Logger;

public class VerifyOTPActivity extends AppCompatActivity {

    private android.app.AlertDialog dialog;
    private String mRequestBody = "";
    private String mMobile = "";
    private RequestQueue queue;
    private TextView guide;
    private PinView pinView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_otp);
        queue = Volley.newRequestQueue(this);
        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setCancelable(false)
                .setMessage("Verifying")
                .build();

        init();


        if(getIntent().getExtras()!=null){
            if(getIntent().getExtras().getString("mobile")!=null){
                mMobile = getIntent().getExtras().getString("mobile");
                guide.setText("Please Enter the OTP set to "+mMobile);

            }else {
                finish();
            }
        }else {
            finish();
        }



    }

    private void init() {
        pinView = findViewById(R.id.pinViewOTP);
        guide = findViewById(R.id.textViewGuide);
    }

    public void back(View view) {
        finish();
    }

    public void verifyOTP(View view) {
        if(pinView.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"EMPTY OTP!",Toast.LENGTH_LONG).show();
        }else {


            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("mobile",mMobile);
                jsonBody.put("otp",  pinView.getText().toString());
                mRequestBody = jsonBody.toString();
                HitVerifyOTPapi();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void HitVerifyOTPapi() {
        dialog.show();

        Logger.v("Verifying OTP...");
        final String url = Constants.BASE_URL+"api/v1/otp/verify";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Verified!",Toast.LENGTH_LONG).show();
                        Logger.v("Response: "+response.toString());
                        Intent intent = new Intent(VerifyOTPActivity.this,LoginActivity.class);
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

    public void resend(View view) {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("mobile",mMobile);
            jsonBody.put("type","signup_otp");
            mRequestBody = jsonBody.toString();
            HitResendOTPapi();

        } catch (JSONException e) {
            e.printStackTrace();
        }

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
                        Toast.makeText(getApplicationContext(),"OTP sent!",Toast.LENGTH_LONG).show();
                        Logger.v("Response: "+response.toString());
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
}
