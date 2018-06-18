package in.co.tripin.chahiyecustomer.javacode.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import in.co.tripin.chahiyecustomer.Model.responce.UserAddress;
import in.co.tripin.chahiyecustomer.R;
import in.co.tripin.chahiyecustomer.helper.Logger;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class AddAddressActivity extends AppCompatActivity {

    private RequestQueue queue;
    AwesomeValidation mAwesomeValidation;


    private String mNickname = "";
    private String mFlatSociety = "";
    private String mAddLine1 = "";
    private String mAddLine2 = "";
    private String mLandMark = "";
    private String mCity = "";
    private String mState = "";
    private String mCountry = "";
    private EditText nick, flat, line1, line2, landmark, state, country, city;
    private String mRequestBody = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        setTitle("Add Address");
        queue = Volley.newRequestQueue(this);
        init();
        mAwesomeValidation = new AwesomeValidation(BASIC);
        addValidations();

    }

    private void addValidations() {
        mAwesomeValidation.addValidation(this, R.id.nickname, RegexTemplate.NOT_EMPTY, R.string.err_nick_address);
        mAwesomeValidation.addValidation(this, R.id.flatsocity, RegexTemplate.NOT_EMPTY, R.string.err_flat);
        mAwesomeValidation.addValidation(this, R.id.landmark, RegexTemplate.NOT_EMPTY, R.string.err_landmark);

    }

    private void init() {
        nick = findViewById(R.id.nickname);
        flat = findViewById(R.id.flatsocity);
        line1 = findViewById(R.id.addressline1);
        line2 = findViewById(R.id.addressline2);
        landmark = findViewById(R.id.landmark);
        state = findViewById(R.id.state);
        city = findViewById(R.id.city);
        country = findViewById(R.id.country);

    }

    public void addAddress(View view) {

        if(mAwesomeValidation.validate()){
            Toast.makeText(getApplicationContext(),"Saving...",Toast.LENGTH_LONG).show();
            getDataFromForm();
            HitAddAddressAPI();
        }


    }

    private void HitAddAddressAPI() {
        Logger.v("add address");
        final String url = "http://139.59.70.142:3055/api/v1/users/address";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Toast.makeText(getApplicationContext(),"Address Saved!",Toast.LENGTH_LONG).show();
                        Log.d("Response", response.toString());
                        finish();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                        Toast.makeText(getApplicationContext(),"Try Again!",Toast.LENGTH_LONG).show();

                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwZXJzb25JZCI6IjViMjM3MjllYjc3ZDBkMDAxNTU0NWU1OSIsInJvbGVJZCI6IjViMjM3MjllYjc3ZDBkMDAxNTU0NWU1YSIsImV4cGlyZXMiOjE1MjkxMzYxNTg1NTB9.QJLI7T-qkAhJyiHXDjffCClMZVTn8G8TV_SF2MN50Yg");
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

    private void getDataFromForm() {
        mNickname = nick.getText().toString().trim();
        mFlatSociety = flat.getText().toString().trim();
        mAddLine2 = line2.getText().toString().trim();
        mAddLine1 = line1.getText().toString().trim();
        mLandMark = landmark.getText().toString().trim();
        mCity = city.getText().toString().trim();
        mState = state.getText().toString().trim();
        mCountry = country.getText().toString().trim();

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("nickname", mNickname);
            jsonBody.put("flatSociety", mFlatSociety);
            jsonBody.put("addressLine1", mAddLine1);
            jsonBody.put("addressLine2", mAddLine2);
            jsonBody.put("country", mCountry);
            jsonBody.put("state", mState);
            jsonBody.put("city", mCity);
            jsonBody.put("landmark", mLandMark);
            mRequestBody = jsonBody.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
