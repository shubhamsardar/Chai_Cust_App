package in.co.tripin.chahiyecustomer.javacode.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.chaos.view.PinView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import in.co.tripin.chahiyecustomer.Activities.MainLandingMapActivity;
import in.co.tripin.chahiyecustomer.Managers.PreferenceManager;
import in.co.tripin.chahiyecustomer.R;
import in.co.tripin.chahiyecustomer.helper.Constants;
import in.co.tripin.chahiyecustomer.helper.Logger;

public class OTPForChangePINActivity extends AppCompatActivity {

    private String mRMN = "";
    String mRequestBody = "";
    private AlertDialog dialog;
    private RequestQueue queue;
    private TextView guide;
    private TextView resendOtp;
    private PinView pinView;
    private EditText newpin;
    private EditText reenterpin;
    private AwesomeValidation awesomeValidation;
    private Button submit;
    private PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpfor_change_pin);
        pinView = findViewById(R.id.pinViewOTP);
        guide = findViewById(R.id.textViewGuide);
        resendOtp = findViewById(R.id.resendotp);
        newpin = findViewById(R.id.pin);
        reenterpin = findViewById(R.id.reenter);
        submit = findViewById(R.id.btn_submitotp);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.pin, RegexTemplate.NOT_EMPTY, R.string.error_invalid_password);
        awesomeValidation.addValidation(this, R.id.reenter, R.id.pin, R.string.err_password_confirmation);
        preferenceManager = PreferenceManager.getInstance(this);

        queue = Volley.newRequestQueue(this);

        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setCancelable(false)
                .setMessage("Sending OTP")
                .build();

        if(getIntent().getExtras()==null){
            finish();
        }else {
            if(getIntent().getExtras().getString("mobile")!=null){
                mRMN = getIntent().getExtras().getString("mobile");
                sendOTP();
            }else {
                finish();
            }
        }

        setListners();
    }

    private void setListners() {
        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOTP();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate()){
                    if(pinView.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(),"Enter OTP First!",Toast.LENGTH_LONG).show();
                    }else {
                        if(newpin.getText().toString().length()==4){
                            submitChangePINApi();
                        }else {
                            Toast.makeText(getApplicationContext(),"Set a 4 digit PIN",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }

    private void submitChangePINApi() {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("mobile",mRMN);
            jsonBody.put("otp",pinView.getText().toString());
            jsonBody.put("password",newpin.getText().toString());

            mRequestBody = jsonBody.toString();
            HitChangePinapi();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void HitChangePinapi() {
        dialog.show();
        Logger.v("Changing PIN...");
        final String url = Constants.BASE_URL+"api/v1/user/password/forget";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(),"PIN Changed!, please login",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(OTPForChangePINActivity.this, LoginActivity.class));
                        finish();
                        Logger.v("Response: "+response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        guide.setText("Change PIN Failed, Try Again!");
                        Logger.v("Error.Response: "+ error.toString());
                        Toast.makeText(getApplicationContext(),"Try Again!",Toast.LENGTH_LONG).show();
                        try {
                            String s = new String(error.networkResponse.data, "UTF-8");
                            Logger.v("Error.Response: "+ s);
                            JSONObject jsonObject = new JSONObject(s);
                            Double code  = jsonObject.getDouble("errorCode");
                            if(code==103){
                                // go to log in
                                Toast.makeText(getApplicationContext(),"Mobile not Registered, SignUp!",Toast.LENGTH_LONG).show();
                                Intent i = new Intent(OTPForChangePINActivity.this,SignUpActivity.class);
                                startActivity(i);
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

        };
        queue.add(getRequest);
    }

    private void sendOTP() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("mobile",mRMN);
            jsonBody.put("type","forget_password_otp");
            mRequestBody = jsonBody.toString();
            HitResendOTPapi();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void HitResendOTPapi() {
        dialog.show();
        Logger.v("Resending OTP...");
        guide.setText("Sending OTP to "+mRMN);
        final String url = Constants.BASE_URL+"api/v1/otp/send";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(),"OTP sent!",Toast.LENGTH_LONG).show();
                        Logger.v("Response: "+response.toString());
                        guide.setText("An OTP is sent to "+mRMN);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        guide.setText("An OTP sending Failed, Resend!");
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

    public void back(View view) {
        finish();
    }

    public void resend(View view) {
    }
}
