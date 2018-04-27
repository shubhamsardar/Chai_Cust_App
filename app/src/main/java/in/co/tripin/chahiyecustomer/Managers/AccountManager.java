package in.co.tripin.chahiyecustomer.Managers;

import android.content.Context;

import in.co.tripin.chahiyecustomer.Communication.RquestHandler;
import in.co.tripin.chahiyecustomer.Model.responce.ChangeNumberResponse;
import in.co.tripin.chahiyecustomer.Model.responce.ForgotPasswordResponse;
import in.co.tripin.chahiyecustomer.Model.responce.ResendOtpResponse;
import in.co.tripin.chahiyecustomer.Model.responce.ResetPasswordResponse;
import in.co.tripin.chahiyecustomer.Model.responce.SignInResponse;
import in.co.tripin.chahiyecustomer.Model.responce.SignUpResponse;
import in.co.tripin.chahiyecustomer.Model.responce.ValidateOtpResponse;
import in.co.tripin.chahiyecustomer.dataproviders.ApiTag;
import in.co.tripin.chahiyecustomer.dataproviders.RequestProvider;
import in.co.tripin.chahiyecustomer.factory.RequestListener;
import in.co.tripin.chahiyecustomer.factory.Response;
import in.co.tripin.chahiyecustomer.helper.Logger;
import in.co.tripin.chahiyecustomer.helper.SharedPreferenceManager;
import in.co.tripin.chahiyecustomer.role.IAccountManagerOption;


public class AccountManager implements RequestListener, IAccountManagerOption {
    private Context mContext;

    private ChangeNumberListener mChangeNumberListener;

    private ForgotPasswordListener mForgotPasswordListener;

    private PreferenceManager mPreference;

    private RequestProvider mRequestProvider;

    private ResendOtpListener mResendOtpListener;

    private ResetPasswordListener mResetPasswordListener;

    private SharedPreferenceManager mSharedPref;

    private SignInListener mSignInListener;

    private SignUpListener mSignUpListener;

    private ValidateOtpListener mValidateOtpListener;

    public AccountManager(Context context) {
        mContext = context;
        mSharedPref = new SharedPreferenceManager(mContext);
        mPreference = PreferenceManager.getInstance(mContext);
        this.mRequestProvider = new RequestProvider(mContext);
    }

    @Override
    public void signUp(String name, String mobile, String password) {
        RquestHandler rquestHandler = new RquestHandler(mContext);
        rquestHandler.send(mRequestProvider.getSignUpRequest(this, name, mobile, password));
    }

    @Override
    public void signIn(String mobile, String password) {
        RquestHandler rquestHandler = new RquestHandler(mContext);
        rquestHandler.send(mRequestProvider.getSignInRequest(this, mobile, password));
    }

    @Override
    public void forgotPassword(String mobile) {
        RquestHandler rquestHandler = new RquestHandler(mContext);
        rquestHandler.send(mRequestProvider.getForgotPasswordRequest(this, mobile));
    }

    @Override
    public boolean isLogin() {
        return false;
    }

    @Override
    public void resendOtp() {
        RquestHandler rquestHandler = new RquestHandler(mContext);
        rquestHandler.send(mRequestProvider.getResendOtpRequest(this));
    }

    @Override
    public void validateOtp(String otp) {
        RquestHandler rquestHandler = new RquestHandler(mContext);
        rquestHandler.send(mRequestProvider.getValidateOtpRequest(this, otp));
    }

    @Override
    public void resetPassword(String newPass, String confirmPass) {
        RquestHandler rquestHandler = new RquestHandler(mContext);
        rquestHandler.send(mRequestProvider.getResetPasswordRequest(this, newPass, confirmPass));
    }

    @Override
    public void changeNumber(String newMobileNo) {
        RquestHandler rquestHandler = new RquestHandler(mContext);
        rquestHandler.send(mRequestProvider.getChangeNumberRequest(this, newMobileNo));
    }

    public void getSignInResult(String mobile, String password, SignInListener callback) {
        this.mSignInListener = callback;
        signIn(mobile, password);
    }

    public void getSignUpResult(SignUpListener callback, String name, String mobileNo, String password) {
        this.mSignUpListener = callback;
        signUp(name, mobileNo, password);
    }

    public void getForgotPasswordResult(ForgotPasswordListener callback, String mobileNo) {
        this.mForgotPasswordListener = callback;
        forgotPassword(mobileNo);
    }

    public void getResendOtpResult(ResendOtpListener resendOtpListener) {
        this.mResendOtpListener = resendOtpListener;
        resendOtp();
    }

    public void getValidateOtpResult(String otp, ValidateOtpListener validateOtpListener) {
        this.mValidateOtpListener = validateOtpListener;
        validateOtp(otp);
    }

    public void getResetPasswordResult(ResetPasswordListener resetPasswordListener, String newPass, String oldPass) {
        this.mResetPasswordListener = resetPasswordListener;
        resetPassword(newPass, oldPass);
    }

    public void getChangeNumberResult(ChangeNumberListener changeNumberListener, String newMobileNo) {
        this.mChangeNumberListener = changeNumberListener;
        changeNumber(newMobileNo);
    }

    @Override
    public void onResponse(int responseResult, int apiTag, Response response) {
        String message;
        switch (apiTag) {
            case ApiTag.SIGN_UP:
                SignUpResponse signUpResponse = (SignUpResponse) response;
                message = signUpResponse.getMessage();
                handleSignUpresponse(signUpResponse);
                mSignUpListener.onSuccess(message);
                break;
            case ApiTag.SIGN_IN:
                SignInResponse signInResponse = (SignInResponse) response;
                message = signInResponse.getMessage();
                handleSignInResponse(signInResponse);
                mSignInListener.onSuccess(message);
                break;
            case ApiTag.FORGOT_PASSWORD:
                ForgotPasswordResponse forgotPasswordResponse = (ForgotPasswordResponse) response;
                message = forgotPasswordResponse.getMessage();
                handleForgotPasswordResponse(forgotPasswordResponse);
                mForgotPasswordListener.onSuccess(message);
                break;
            case ApiTag.RESEND_OTP:
                ResendOtpResponse resendOtpResponse = (ResendOtpResponse) response;
                message = resendOtpResponse.getMessage();
                mResendOtpListener.onSuccess(message);
                break;
            case ApiTag.VALIDATE_OTP:
                ValidateOtpResponse validateOtpResponse = (ValidateOtpResponse) response;
                message = validateOtpResponse.getMessage();
                handleValidateOtpResponse(validateOtpResponse);
                mValidateOtpListener.onSuccess(message);
                break;
            case ApiTag.RESET_PASSWORD:
                ResetPasswordResponse resetPasswordResponse = (ResetPasswordResponse) response;
                message = resetPasswordResponse.getMessage();
                mResetPasswordListener.onSuccess(message);
                break;
            case ApiTag.CHANGE_NUMBER:
                ChangeNumberResponse changeNumberResponse = (ChangeNumberResponse) response;
                message = changeNumberResponse.getMessage();
                mChangeNumberListener.onSuccess(message);
                break;

        }
    }

    @Override
    public void onError(int responseResult, int apiTag, String message, Response response) {
        switch (apiTag) {
            case ApiTag.SIGN_UP:
                mSignUpListener.onFailed(message);
                break;
            case ApiTag.SIGN_IN:
                mSignInListener.onFailed(message);
                break;
            case ApiTag.FORGOT_PASSWORD:
                mForgotPasswordListener.onFailed(message);
                break;
            case ApiTag.RESEND_OTP:
                mResendOtpListener.onFailed(message);
                break;
            case ApiTag.VALIDATE_OTP:
                mValidateOtpListener.onFailed(message);
                break;
            case ApiTag.RESET_PASSWORD:
                mResetPasswordListener.onFailed(message);
                break;
            case ApiTag.CHANGE_NUMBER:
                mChangeNumberListener.onFailed(message);
                break;
        }
    }

    private void handleSignUpresponse(SignUpResponse signUpResponse) {
        mSharedPref.setStringPrefrence("sign_user_id", signUpResponse.getData().getUser_id());
        mSharedPref.setStringPrefrence("sign_username", signUpResponse.getData().getUsername());
        mSharedPref.setStringPrefrence("sign_mobile_number", signUpResponse.getData().getUsername());

        mPreference.setUserId(signUpResponse.getData().getUser_id());
        mPreference.setTempUserId(signUpResponse.getData().getUser_id());
        mPreference.setMobileNo(signUpResponse.getData().getUsername());

        Logger.v("SignUp Username:" + signUpResponse.getData().getUsername());
    }

    private void handleSignInResponse(SignInResponse signInResponse) {
        Logger.v("handle sign in response  " + signInResponse);
        Logger.v("Username " + signInResponse.getData().getName());
        mSharedPref.setStringPrefrence("sign_user_id", signInResponse.getData().getUser_id());
        mSharedPref.setStringPrefrence("sign_profilename", signInResponse.getData().getName());
        mSharedPref.setStringPrefrence("sign_user_groupid", signInResponse.getData().getUsers_group_id());
        mSharedPref.setStringPrefrence("sign_tripinid", signInResponse.getData().getTripin_id());
        mSharedPref.setStringPrefrence("sign_email", signInResponse.getData().getEmail());
        mSharedPref.setStringPrefrence("sign_mobile_number", signInResponse.getData().getMobile_number());
        mSharedPref.setStringPrefrence("map_status", signInResponse.getData().getCompany_name());

        mPreference.setUserId(signInResponse.getData().getUser_id());
        mPreference.setTempUserId(signInResponse.getData().getUser_id());
        mPreference.setUserName(signInResponse.getData().getName());
        mPreference.setMobileNo(signInResponse.getData().getMobile_number());
        mPreference.setTripinId(signInResponse.getData().getTripin_id());
        mPreference.setTripinId(signInResponse.getData().getTripin_id());
    }

    private void handleForgotPasswordResponse(ForgotPasswordResponse forgotPasswordResponse) {
        mPreference.setTempUserId(forgotPasswordResponse.getData().getUser_id());
        mPreference.setMobileNo(forgotPasswordResponse.getData().getUsername());
    }

    private void handleValidateOtpResponse(ValidateOtpResponse validateOtpResponse) {
        String userId = validateOtpResponse.getData().getUser_id();
        mPreference.setTempUserId(userId);
    }


    public interface SignUpListener {
        void onSuccess(String message);

        void onFailed(String message);
    }

    public interface SignInListener {
        void onSuccess(String message);

        void onFailed(String message);
    }

    public interface ForgotPasswordListener {
        void onSuccess(String message);

        void onFailed(String message);
    }

    public interface ResendOtpListener {
        void onSuccess(String message);

        void onFailed(String message);
    }

    public interface ValidateOtpListener {
        void onSuccess(String message);

        void onFailed(String message);
    }

    public interface ResetPasswordListener {
        void onSuccess(String message);

        void onFailed(String message);
    }

    public interface ChangeNumberListener {
        void onSuccess(String message);

        void onFailed(String message);
    }
}

