package in.co.tripin.chahiyecustomer.Model.Requests;


import in.co.tripin.chahiyecustomer.dataproviders.CommonRequestKey;

public interface ResendOtpRequest extends CommonRequestKey {
    String USER_ID = "user_id";
    String MOBILE_NUMBER = "mobile_number";
}
