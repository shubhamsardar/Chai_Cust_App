package in.co.tripin.chahiyecustomer.Model.Requests;


import in.co.tripin.chahiyecustomer.dataproviders.CommonRequestKey;

public interface SignUpRequest extends CommonRequestKey {

    String PROFILE_NAME = "name";
    String USER_NAME = "username";
    String PASSWORD = "password";
    String OUTH_MOBILE_NUMBER = "outh_mobile_number";
    String SN_ID = "sn_id";
    String OUTH_NAME = "outh_name";
    String SOCIAL_ACCESS_TOKEN = "social_access_token";
}
