package in.co.tripin.chahiyecustomer.javacode.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import in.co.tripin.chahiyecustomer.R;

public class TapriDetailsActivity extends AppCompatActivity {

    private String tapriId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tapri_details);

        //get Tapri Id from Intent
        tapriId = getIntent().getExtras().getString("tapri_id");
        if(tapriId.isEmpty()){
            finish();
        }else {
            //call tapri items api
        }
    }
}
