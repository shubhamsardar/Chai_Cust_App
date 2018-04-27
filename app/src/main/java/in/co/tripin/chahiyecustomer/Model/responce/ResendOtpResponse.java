package in.co.tripin.chahiyecustomer.Model.responce;


import in.co.tripin.chahiyecustomer.dataproviders.CommonResponse;

public class ResendOtpResponse extends CommonResponse {

    private ResendOtpData data;

    public ResendOtpData getData() {
        return data;
    }

    public void setData(ResendOtpData data) {
        this.data = data;
    }

    public class ResendOtpData {
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
