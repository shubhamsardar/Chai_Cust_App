package in.co.tripin.chahiyecustomer.javacode.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
import in.co.tripin.chahiyecustomer.R;
import in.co.tripin.chahiyecustomer.helper.Logger;

public class ForgotPinActivity extends AppCompatActivity {

    TextInputEditText mMobile;
    Button mSubmit;
    AwesomeValidation awesomeValidation;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pin);

        init();
        setListners();

    }



    private void init() {
        mMobile = findViewById(R.id.mobile);
        mSubmit = findViewById(R.id.btn_submit);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.mobile, RegexTemplate.NOT_EMPTY, R.string.err_mobile);


    }

    private void setListners() {

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate()){
                    Intent intent = new Intent(ForgotPinActivity.this,OTPForChangePINActivity.class);
                    intent.putExtra("mobile",mMobile.getText().toString().trim());
                    startActivity(intent);
                }

            }
        });

    }

    public void back(View view) {
        View vieww = this.getCurrentFocus();
        if (vieww != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(vieww.getWindowToken(), 0);
        }
        finish();
    }


}
