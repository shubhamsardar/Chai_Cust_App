package in.co.tripin.chahiyecustomer.javacode.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import in.co.tripin.chahiyecustomer.Activities.MainLandingMapActivity;
import in.co.tripin.chahiyecustomer.Managers.AccountManager;
import in.co.tripin.chahiyecustomer.Managers.PreferenceManager;
import in.co.tripin.chahiyecustomer.R;
import in.co.tripin.chahiyecustomer.helper.Logger;

import android.support.design.widget.TextInputEditText;
import android.widget.Toast;


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
    TextInputEditText mMobile;
    TextInputEditText mPin;
    Button mSubmit;
    PreferenceManager mPreferenceManger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_details);
        mAccountManager = new AccountManager(this);
        mPreferenceManger = PreferenceManager.getInstance(this);

        mMobile = findViewById(R.id.mobile);
        mPin = findViewById(R.id.pin);
        mSubmit = findViewById(R.id.btn_login);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singIn();
            }
        });
    }

    public void singIn() {
        String mobile = mMobile.getText().toString();
        String pin = mPin.getText().toString();

        mAccountManager.getSignInResult(new AccountManager.SignInListener() {
            @Override
            public void onSuccess(String message) {
                mPreferenceManger.setUserId("1234");
                startMainLandingMapActivity();
                Toast.makeText(LoginActivity.this, "Success",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(String message) {
                Toast.makeText(LoginActivity.this, "Mobile or pin wrong",Toast.LENGTH_SHORT).show();
            }
        }, mobile, pin);
    }

    private void startMainLandingMapActivity() {
        startActivity(new Intent(LoginActivity.this, MainLandingMapActivity.class));
    }
}
