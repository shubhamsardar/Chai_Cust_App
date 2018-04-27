package in.co.tripin.chahiyecustomer.Model.Requests;


import in.co.tripin.chahiyecustomer.dataproviders.CommonRequestKey;

public interface ValidateOtpRequest extends CommonRequestKey {

    String USER_ID = "user_id";
    String OTP = "otp";
}