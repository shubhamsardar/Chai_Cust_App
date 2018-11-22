package in.co.tripin.chahiyecustomer.javacode.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import in.co.tripin.chahiyecustomer.Activities.MainLandingMapActivity;
import in.co.tripin.chahiyecustomer.Activities.SplashActivity;
import in.co.tripin.chahiyecustomer.Managers.PreferenceManager;
import in.co.tripin.chahiyecustomer.R;
import in.co.tripin.chahiyecustomer.helper.Constants;
import in.co.tripin.chahiyecustomer.helper.Logger;

public class EditPinActivity extends AppCompatActivity {

    EditText oldpin, newpin, reenterpin;
    Button submit;
    AwesomeValidation awesomeValidation;
    String mRequestBody = "";
    private AlertDialog dialog;
    PreferenceManager preferenceManager;
    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pin);
        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setCancelable(false)
                .setMessage("Changing PIN")
                .build();
        preferenceManager = PreferenceManager.getInstance(this);
        queue = Volley.newRequestQueue(this);

        setTitle("Change PIN");
        oldpin = findViewById(R.id.oldpin);
        newpin = findViewById(R.id.newpin);
        reenterpin = findViewById(R.id.reenternewpin);
        submit = findViewById(R.id.submit);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.oldpin, RegexTemplate.NOT_EMPTY, R.string.error_invalid_password);
        awesomeValidation.addValidation(this, R.id.newpin, RegexTemplate.NOT_EMPTY, R.string.error_invalid_password);
        awesomeValidation.addValidation(this, R.id.reenternewpin, R.id.newpin, R.string.err_password_confirmation);
        setListners();
    }

    private void setListners() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()) {
                    JSONObject jsonBody = new JSONObject();
                    try {
                        jsonBody.put("pin", oldpin.getText().toString());
                        jsonBody.put("newPin", newpin.getText().toString());

                        mRequestBody = jsonBody.toString();
                        HitChangePinapi();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void HitChangePinapi() {
        dialog.show();
        Logger.v("Changing PIN...");
        final String url = Constants.BASE_URL+"api/v1/user/password/update";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        dialog.dismiss();
                        preferenceManager.setUserId(null);
                        preferenceManager.setMobileNo(null);
                        preferenceManager.setAccessToken(null);
                        Toast.makeText(getApplicationContext(), "PIN Changed!, please login", Toast.LENGTH_LONG).show();
                        Logger.v("Response: " + response.toString());
                        startActivity(new Intent(EditPinActivity.this, SplashActivity.class));
                        finish();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Logger.v("Error.Response: " + error.toString());
                        Toast.makeText(getApplicationContext(), "Try Again!", Toast.LENGTH_SHORT).show();
                        try {
                            String s = new String(error.networkResponse.data, "UTF-8");
                            Logger.v("Error.Response: " + s);
                            JSONObject jsonObject = new JSONObject(s);
                            Double code = jsonObject.getDouble("errorCode");
                            if (code == 203) {
                                Toast.makeText(getApplicationContext(), "Wrong Old Pin!", Toast.LENGTH_LONG).show();
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
}
