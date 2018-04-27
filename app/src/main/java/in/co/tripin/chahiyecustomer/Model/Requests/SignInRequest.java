package in.co.tripin.chahiyecustomer.Model.Requests;


import in.co.tripin.chahiyecustomer.dataproviders.CommonRequestKey;


public interface SignInRequest extends CommonRequestKey {
    String USER_NAME = "username";

    String PASSWORD = "password";

    String OAUTH_NAME = "outh_name";

    String SOCIAL_ACCESS_TOKEN = "social_access_token";

    String IMEI_NO = "imei_no";

    String GCM_ID = "gcm_id";
}
