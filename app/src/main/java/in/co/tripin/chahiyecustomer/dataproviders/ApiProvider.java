package in.co.tripin.chahiyecustomer.dataproviders;


import in.co.tripin.chahiyecustomer.BuildConfig;

public class ApiProvider {
    private static final String BASE_URL = "http://139.59.70.142:3055/api/v1";

    public static String getApiByTag(int apiTag) {
        String url = null;

        switch (apiTag) {

            case ApiTag.O_AUTH: // 1
                //url = BuildConfig.SERVER + "/Oauth2/ClientCredentials/get_token";
                break;

            case ApiTag.SIGN_IN: // 2
                url = BASE_URL + "/user/signIn";
                break;

            case ApiTag.SIGN_UP: // 3
                url = BASE_URL + "/user/signUp";
                break;

            case ApiTag.VALIDATE_OTP: // 4
                url = BASE_URL + "user/otp_validate";
                break;

            case ApiTag.CHANGE_NUMBER: // 5
                url = BASE_URL + "user/sign_up_change_number";
                break;

            case ApiTag.OTP_VERIFY: // 6
                url = BASE_URL + "user/resend_otp";
                break;

            case ApiTag.FORGOT_PASSWORD: // 7
                url = BASE_URL + "user/password/forget";
                break;

            case ApiTag.RESET_PASSWORD: // 8
                url = BASE_URL + "user/reset_password";
                break;


            case ApiTag.ADDRESS_LIST: // 11
                url = BASE_URL + "/users/address?userAddress=ALL";
                break;
            default:
                return null;
        }
        return url;
    }

    public static String getTapriApi(String lat, String lng) {

        return BASE_URL + "/tapriNearBy?lat=" + lat + "&long=" + lng;

    }
}

