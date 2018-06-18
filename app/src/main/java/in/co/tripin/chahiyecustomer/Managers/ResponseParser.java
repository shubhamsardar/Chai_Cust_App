package in.co.tripin.chahiyecustomer.Managers;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import in.co.tripin.chahiyecustomer.Model.responce.ChangeNumberResponse;
import in.co.tripin.chahiyecustomer.Model.responce.ForgotPasswordResponse;
import in.co.tripin.chahiyecustomer.Model.responce.OAuthResponse;
import in.co.tripin.chahiyecustomer.Model.responce.ResendOtpResponse;
import in.co.tripin.chahiyecustomer.Model.responce.ResetPasswordResponse;
import in.co.tripin.chahiyecustomer.Model.responce.SignInResponse;
import in.co.tripin.chahiyecustomer.Model.responce.SignUpResponse;
import in.co.tripin.chahiyecustomer.Model.responce.Tapri;
import in.co.tripin.chahiyecustomer.Model.responce.ValidateOtpResponse;
import in.co.tripin.chahiyecustomer.dataproviders.ApiTag;
import in.co.tripin.chahiyecustomer.dataproviders.CommonRequestKey;
import in.co.tripin.chahiyecustomer.factory.Request;
import in.co.tripin.chahiyecustomer.factory.RequestListener;
import in.co.tripin.chahiyecustomer.factory.Response;
import in.co.tripin.chahiyecustomer.helper.Logger;


public class ResponseParser {

    private int mApiTag;
    private RequestListener mConnectionListener;

    public  void parseJson(String data, Request request) {
        mApiTag = request.getTag();
        mConnectionListener = request.getConnectionListener();
        try {
            Gson gson = new Gson();
            if (this.mApiTag == ApiTag.O_AUTH) {
                OAuthResponse tOAuthResponse = new OAuthResponse();
                tOAuthResponse = gson.fromJson(data, OAuthResponse.class);
                this.mConnectionListener.onResponse(RequestListener.RESPONSE_OK,  mApiTag, tOAuthResponse);
            }
            /**
             * The following else condition handles the exception for PredictionResponse class for handling
             * Json response of Google Place API Autocomplete Service.
             */
            else {
                JSONObject jObj = new JSONObject(data);
//                int status = jObj.getInt(CommonRequestKey.STATUS);
                String status = jObj.getString(CommonRequestKey.STATUS);
                Response response = getResponseObject(jObj, gson);
                Logger.v("parse json object: " + response);
                switch (status) {
                    case CommonRequestKey.FAIL:
                        mConnectionListener.onError(RequestListener.RESULT_FAIL, mApiTag, jObj.getString(CommonRequestKey.MESSAGE), response);//getResponseObject(jObj,gson));
                        break;
                    case CommonRequestKey.SUCCESS:
                        mConnectionListener.onResponse(RequestListener.RESPONSE_OK, mApiTag, response);
                        break;
                    case CommonRequestKey.SUCCESS_TEMP:
                        mConnectionListener.onResponse(RequestListener.RESPONSE_OK, mApiTag, response);
                        break;
                }

            }
        } catch (JsonSyntaxException eJsonSyntaxException) {
            eJsonSyntaxException.printStackTrace();
            Logger.v("Exception JsonSyntaxException= " + eJsonSyntaxException.getMessage());
//            mConnectionListener.onError(RequestListener.PARSE_ERR0R, mApiTag, eJsonSyntaxException.getMessage(), null);
        } catch (JSONException eJSONException) {
            Logger.v("Exception JsonSyntaxException= " + eJSONException.getMessage());
            eJSONException.printStackTrace();
        }
    }

    private Response getResponseObject(JSONObject jsonObject, Gson gson) {
        Response response = null;
        try {
            switch (this.mApiTag) {
                case ApiTag.O_AUTH: //1
                    OAuthResponse oAuthResponse = gson.fromJson(jsonObject.toString(),OAuthResponse.class);
                    response = oAuthResponse;
                    break;
                case ApiTag.SIGN_IN: // 2
                    SignInResponse signInResponse = gson.fromJson(jsonObject.toString(), SignInResponse.class);
                    response =  signInResponse;
                    break;
                case ApiTag.SIGN_UP: // 3
                    SignUpResponse signUpResponse = gson.fromJson(jsonObject.toString(), SignUpResponse.class);
                    response = signUpResponse;
                    break;
                case ApiTag.VALIDATE_OTP: // 4
                    ValidateOtpResponse validateOtpResponse = gson.fromJson(jsonObject.toString(),  ValidateOtpResponse.class);
                    response = validateOtpResponse;
                    break;
                case ApiTag.CHANGE_NUMBER: // 5
                    ChangeNumberResponse changeNumberResponse = gson.fromJson(jsonObject.toString(), ChangeNumberResponse.class);
                    response = changeNumberResponse;
                    break;
                case ApiTag.RESEND_OTP: // 6
                    ResendOtpResponse resendOtpResponse = gson.fromJson(jsonObject.toString(), ResendOtpResponse.class);
                    response = resendOtpResponse;
                    break;
                case ApiTag.FORGOT_PASSWORD: // 7
                    ForgotPasswordResponse forgotPasswordResponse = gson.fromJson(jsonObject.toString(), ForgotPasswordResponse.class);
                    response = forgotPasswordResponse;
                    break;
                case ApiTag.RESET_PASSWORD: // 8
                    ResetPasswordResponse resetPasswordResponse = gson.fromJson(jsonObject.toString(), ResetPasswordResponse.class);
                    response = resetPasswordResponse;
                    break;
                case ApiTag.TAPRI_LIST: // 8
                    Tapri tapri = gson.fromJson(jsonObject.toString(), Tapri.class);
                    response = tapri;
                    break;

            }
        } catch (JsonSyntaxException eJsonSyntaxException) {
            eJsonSyntaxException.printStackTrace();
        } catch (Exception e) {
            Logger.v("Coming here in Exception in API TAg : " + this.mApiTag + "Exception message = " + e.getMessage());
        }
        return response;
    }
}
