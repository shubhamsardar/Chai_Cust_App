package in.co.tripin.chahiyecustomer.role;


public interface IAccountManagerOption {
    void signUp(String name, String mobile, String password);

    void signIn(String mobile, String password);

    void forgotPassword(String mobile);

    boolean isLogin();

    void resendOtp();

    void validateOtp(String otp);

    void resetPassword(String newPass, String confirmPass);

    void changeNumber(String newMobileNo);
}
