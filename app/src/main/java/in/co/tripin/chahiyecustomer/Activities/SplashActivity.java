package in.co.tripin.chahiyecustomer.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.co.tripin.chahiyecustomer.Managers.PreferenceManager;
import in.co.tripin.chahiyecustomer.R;
import in.co.tripin.chahiyecustomer.javacode.activity.AuthLandingActivity;

public class SplashActivity extends AppCompatActivity {

    PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        preferenceManager = PreferenceManager.getInstance(this);



        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(preferenceManager.getAccessToken()==null){
                    startActivity(new Intent(SplashActivity.this, AuthLandingActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(SplashActivity.this, MainLandingMapActivity.class));
                    finish();
                }
            }
        }, 2000);
    }


}
