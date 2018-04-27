package in.co.tripin.chahiyecustomer.Model.responce;


import in.co.tripin.chahiyecustomer.dataproviders.CommonResponse;

/**
 * Created by Yogesh N. Tikam on 3/22/2017.
 * Api no. 4
 */

public class ValidateOtpResponse extends CommonResponse {

    private ValidateOtpdata data = new ValidateOtpdata();

    public ValidateOtpdata getData() {
        return data;
    }

    public void setData(ValidateOtpdata data) {
        this.data = data;
    }

    public class ValidateOtpdata {
        private String user_id;
        private String username;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

}
