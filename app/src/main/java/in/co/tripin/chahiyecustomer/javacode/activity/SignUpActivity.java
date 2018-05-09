package in.co.tripin.chahiyecustomer.javacode.activity;


import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;

import android.widget.AutoCompleteTextView;

import android.widget.Button;
import android.widget.EditText;

import in.co.tripin.chahiyecustomer.R;
import in.co.tripin.chahiyecustomer.Managers.AccountManager;
import in.co.tripin.chahiyecustomer.helper.Logger;
import android.support.design.widget.TextInputEditText;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 */
public class SignUpActivity extends AppCompatActivity {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private AccountManager  mAccountManager;

    TextInputEditText mMobile;
    TextInputEditText mPin;
    TextInputEditText mReenterPin;
    TextInputEditText mName;
    Button mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_details);
        mAccountManager = new AccountManager(this);
        init();
    }

    public void init() {
        mMobile = findViewById(R.id.mobile);
        mPin = findViewById(R.id.pin);
        mName = findViewById(R.id.name);
        mReenterPin = findViewById(R.id.pin_reenter);
        mSubmit = findViewById(R.id.btn_signup);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

        private void signUp() {
            String mobile = mMobile.getText().toString();
            String pin = mPin.getText().toString();
            String name = mName.getText().toString();
            String reentPin = mReenterPin.getText().toString();

        mAccountManager.getSignUpResult(new AccountManager.SignUpListener() {
            @Override
            public void onSuccess(String message) {
                Toast.makeText(SignUpActivity.this, "onSuccess",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(String message) {
                Logger.v("SignUp Failed " + message);
                Toast.makeText(SignUpActivity.this, "onFailed",Toast.LENGTH_SHORT).show();
            }
        }, name,mobile,pin);
    }
}

