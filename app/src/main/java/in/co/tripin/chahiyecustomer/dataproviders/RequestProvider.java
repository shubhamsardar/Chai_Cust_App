package in.co.tripin.chahiyecustomer.dataproviders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.telephony.TelephonyManager;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import in.co.tripin.chahiyecustomer.Managers.PreferenceManager;
import in.co.tripin.chahiyecustomer.Model.Requests.ChangeNumberRequest;
import in.co.tripin.chahiyecustomer.Model.Requests.ForgotPasswordRequest;
import in.co.tripin.chahiyecustomer.Model.Requests.ResendOtpRequest;
import in.co.tripin.chahiyecustomer.Model.Requests.ResetPasswordRequest;
import in.co.tripin.chahiyecustomer.Model.Requests.SignInRequest;
import in.co.tripin.chahiyecustomer.Model.Requests.SignUpRequest;
import in.co.tripin.chahiyecustomer.Model.Requests.ValidateOtpRequest;
import in.co.tripin.chahiyecustomer.factory.Request;
import in.co.tripin.chahiyecustomer.factory.RequestListener;

/**
 * This class is a collection of all the request need to send to server,
 * for getting a specific request you need to pass certain parameter this will return a full request
 * object you just need to pass to your manager to controller
 */

public class RequestProvider {
    private Context mContext;
    private PreferenceManager mPreferenceManager;

    public RequestProvider(Context context) {
        mContext = context;
        mPreferenceManager = PreferenceManager.getInstance(mContext);
    }
    public Request getSignUpRequest(String body, RequestListener listener) {
        Map<String, String> headerParams = new HashMap<String, String>();
        headerParams.put("Content-Type", "application/json");

        Request stopEnquiryRequest = new Request.RequestBuilder(mContext, listener)
                .type(Request.Method.SEND_JSON)
                .url(ApiProvider.getApiByTag(ApiTag.SIGN_UP))
                .headerParams(headerParams)
                .rawData(body)
                .tag(ApiTag.SIGN_UP)
                .build();
        return stopEnquiryRequest;
    }

    /**
     * @param listener Call back listener used to build the calling request for Oauth Generation
     * @return an object of OAuthRequest
     */
    public Request getOauthRequest(RequestListener listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("client_id", "testclient");
        params.put("grant_type", "client_credentials");
        params.put("client_secret", "testpass");

        Request oAuthRequest = new Request.RequestBuilder(mContext, listener)
                .type(Request.Method.POST)
                .url(ApiProvider.getApiByTag(ApiTag.O_AUTH))
                .headerParams(params)
                .tag(ApiTag.O_AUTH)
                .build();
        return oAuthRequest;

    }

//    public Request getSignUpRequest(RequestListener listener, String username, String mobile, String password) {
//
//        String accessToken = mPreferenceManager.getAccessToken();
//
//        Map<String, String> params = new HashMap<String, String>();
//
////        params.put(SignUpRequest.PROFILE_NAME, username);
////        params.put(SignUpRequest.USER_NAME, mobile);
////        params.put(SignUpRequest.PASSWORD, password);
//
//        Request signUpRequest = new Request.RequestBuilder(mContext, listener)
//                .type(Request.Method.POST)
//                .url(ApiProvider.getApiByTag(ApiTag.SIGN_UP))
//                .postParams(params)
//                .tag(ApiTag.SIGN_UP)
//                .build();
//        return signUpRequest;
//    }

    public Request getSignInRequest(String body, RequestListener listener) {

        Map<String, String> headerParams = new HashMap<String, String>();
        headerParams.put("Content-Type", "application/json");

        Request signInRequest = new Request.RequestBuilder(mContext, listener)
                .type(Request.Method.SEND_JSON)
                .url(ApiProvider.getApiByTag(ApiTag.SIGN_IN))
                .postParams(headerParams)
                .rawData(body)
                .tag(ApiTag.SIGN_IN)
                .build();
        return signInRequest;
    }

    public Request getForgotPasswordRequest(RequestListener listener, String mobile) {

        String accessToken = mPreferenceManager.getAccessToken();

        Map<String, String> params = new HashMap<String, String>();
        params.put(ForgotPasswordRequest.ACCESS_TOKEN, accessToken);
        params.put(ForgotPasswordRequest.USER_NAME, mobile);
        Request signInRequest = new Request.RequestBuilder(mContext, listener)
                .type(Request.Method.POST)
                .url(ApiProvider.getApiByTag(ApiTag.FORGOT_PASSWORD))
                .postParams(params)
                .tag(ApiTag.FORGOT_PASSWORD)
                .build();
        return signInRequest;
    }

    public Request getResendOtpRequest(RequestListener listener) {

        String accessToken = mPreferenceManager.getAccessToken();
        String userId = mPreferenceManager.getTempUserId();
        String mobileNo = mPreferenceManager.getMobileNo();

        Map<String, String> params = new HashMap<String, String>();
        params.put(ResendOtpRequest.ACCESS_TOKEN, accessToken);
        params.put(ResendOtpRequest.USER_ID, userId);
        params.put(ResendOtpRequest.MOBILE_NUMBER, mobileNo);

        Request resendOtpRequest = new Request.RequestBuilder(mContext, listener)
                .type(Request.Method.POST)
                .url(ApiProvider.getApiByTag(ApiTag.RESEND_OTP))
                .postParams(params)
                .tag(ApiTag.RESEND_OTP)
                .build();
        return resendOtpRequest;

    }

    public Request getValidateOtpRequest(RequestListener listener, String otp) {


        String accessToken = mPreferenceManager.getAccessToken();
        String userId = mPreferenceManager.getTempUserId();


        Map<String, String> params = new HashMap<String, String>();
        params.put(ValidateOtpRequest.ACCESS_TOKEN, accessToken);
        params.put(ValidateOtpRequest.USER_ID, userId);
        params.put(ValidateOtpRequest.OTP, otp);

        Request validateOtpRequest = new Request.RequestBuilder(mContext, listener)
                .type(Request.Method.POST)
                .url(ApiProvider.getApiByTag(ApiTag.VALIDATE_OTP))
                .postParams(params)
                .tag(ApiTag.VALIDATE_OTP)
                .build();
        return validateOtpRequest;

    }

    public Request getResetPasswordRequest(RequestListener listener, String newPass, String oldPass) {
        String accessToken = mPreferenceManager.getAccessToken();
        String userId = mPreferenceManager.getTempUserId();

        Map<String, String> params = new HashMap<String, String>();
        params.put(ResetPasswordRequest.ACCESS_TOKEN, accessToken);
        params.put(ResetPasswordRequest.USER_ID, userId);
        params.put(ResetPasswordRequest.NEW_PASSWORD, newPass);
        params.put(ResetPasswordRequest.CONFIRM_PASSWORD, oldPass);

        Request resetPasswordRequest = new Request.RequestBuilder(mContext, listener)
                .type(Request.Method.POST)
                .url(ApiProvider.getApiByTag(ApiTag.RESET_PASSWORD))
                .postParams(params)
                .tag(ApiTag.RESET_PASSWORD)
                .build();
        return resetPasswordRequest;

    }

    public Request getChangeNumberRequest(RequestListener listener, String newMonileNo) {

        String accessToken = mPreferenceManager.getAccessToken();
        String userId = mPreferenceManager.getTempUserId();

        Map<String, String> params = new HashMap<String, String>();
        params.put(ChangeNumberRequest.ACCESS_TOKEN, accessToken);
        params.put(ChangeNumberRequest.USER_ID, userId);
        params.put(ChangeNumberRequest.MOBILE_NO, newMonileNo);


        Request changeNumberRequest = new Request.RequestBuilder(mContext, listener)
                .type(Request.Method.POST)
                .url(ApiProvider.getApiByTag(ApiTag.CHANGE_NUMBER))
                .postParams(params)
                .tag(ApiTag.CHANGE_NUMBER)
                .build();
        return changeNumberRequest;


    }







}
