package in.co.tripin.chahiyecustomer.Model.responce;


import in.co.tripin.chahiyecustomer.dataproviders.CommonResponse;

public class ForgotPasswordResponse extends CommonResponse {

    private ForgotPasswordData data;

    public ForgotPasswordData getData() {
        return data;
    }

    public void setData(ForgotPasswordData data) {
        this.data = data;
    }

    public class ForgotPasswordData{
        String user_id;
        String username;

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
