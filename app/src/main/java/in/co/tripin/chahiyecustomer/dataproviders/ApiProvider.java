package in.co.tripin.chahiyecustomer.dataproviders;


import in.co.tripin.chahiyecustomer.BuildConfig;

public class ApiProvider {
    private static final String BASE_URL = BuildConfig.FLAVOR + "/api/";

    public static String getApiByTag(int apiTag) {
        String url = null;

        switch (apiTag) {

            case ApiTag.O_AUTH: // 1
                //url = BuildConfig.SERVER + "/Oauth2/ClientCredentials/get_token";
                break;

            case ApiTag.SIGN_IN: // 2
//                if(BuildConfig.OPERATIONS){
//                    url = BASE_URL + "user/sign_in_tripin";
//                }else {
//                    url = BASE_URL + "user/sign_in";
//                }
                break;

            case ApiTag.SIGN_UP: // 3
                url = BASE_URL + "user/sign_up";
                break;

            case ApiTag.VALIDATE_OTP: // 4
                url = BASE_URL + "user/otp_validate";
                break;

            case ApiTag.CHANGE_NUMBER: // 5
                url = BASE_URL + "user/sign_up_change_number";
                break;

            case ApiTag.RESEND_OTP: // 6
                url = BASE_URL + "user/resend_otp";
                break;

            case ApiTag.FORGOT_PASSWORD: // 7
                url = BASE_URL + "user/forgot_password";
                break;

            case ApiTag.RESET_PASSWORD: // 8
                url = BASE_URL + "user/reset_password";
                break;

         /*   case ApiTag.RESET_PASSWORD: // 8
                url = BASE_URL + "user/reset_password_new";
                break;*/

          
            default:
                return null;
        }
        return url;
    }
}

