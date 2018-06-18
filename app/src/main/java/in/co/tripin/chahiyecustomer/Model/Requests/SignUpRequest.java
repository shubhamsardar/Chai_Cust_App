package in.co.tripin.chahiyecustomer.Model.Requests;



public class SignUpRequest  {
    private String name;
    private String mobile;
    private String pin;
    private String fcm;

    public SignUpRequest(String name, String mobile, String pin, String fcm) {
        this.name = name;
        this.mobile = mobile;
        this.pin = pin;
        this.fcm = fcm;
    }
}
