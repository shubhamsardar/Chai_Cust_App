package in.co.tripin.chahiyecustomer.Model.responce;


import in.co.tripin.chahiyecustomer.dataproviders.CommonResponse;

public class SignUpResponse extends CommonResponse {
    private SignUpData data;
    private String is_mobile;

    public String getIs_mobile() {
        return is_mobile;
    }

    public void setIs_mobile(String is_mobile) {
        this.is_mobile = is_mobile;
    }

    public SignUpData getData() {
        return data;
    }

    public void setData(SignUpData data) {
        this.data = data;
    }

    public class SignUpData{
        private String user_id ;
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
