package in.co.tripin.chahiyecustomer.Model.Requests;


import in.co.tripin.chahiyecustomer.dataproviders.CommonRequestKey;

public interface ResetPasswordRequest extends CommonRequestKey {

    String USER_ID = "user_id";
    String NEW_PASSWORD = "new_password";
    String CONFIRM_PASSWORD = "confirm_password";
}
