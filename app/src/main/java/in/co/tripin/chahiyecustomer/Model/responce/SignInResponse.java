package in.co.tripin.chahiyecustomer.Model.responce;


import in.co.tripin.chahiyecustomer.dataproviders.CommonResponse;

public class SignInResponse extends CommonResponse {

    private UserInfoData data;

    public UserInfoData getData() {
        return data;
    }

    public void setData(UserInfoData data) {
        this.data = data;
    }

    public class UserInfoData {
        private String user_id;
        private String name;
        private String users_group_id;
        private String tripin_id;
        private String email;
        private String mobile_number;
        private String company_name;
        // private CompanyData company_data;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsers_group_id() {
            return users_group_id;
        }

        public void setUsers_group_id(String users_group_id) {
            this.users_group_id = users_group_id;
        }

        public String getTripin_id() {
            return tripin_id;
        }

        public void setTripin_id(String tripin_id) {
            this.tripin_id = tripin_id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile_number() {
            return mobile_number;
        }

        public void setMobile_number(String mobile_number) {
            this.mobile_number = mobile_number;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        /* public CompanyData getCompany_data() {
             return company_data;
         }

         public void setCompany_data(CompanyData company_data) {
             this.company_data = company_data;
         }
 */
        public class CompanyData{
            private String company_name;
            private String email;
            private String phone_number;
            private String address;
            private String city;
            private String pincode;

            public String getAccount_number() {
                return account_number;
            }

            public void setAccount_number(String account_number) {
                this.account_number = account_number;
            }

            private String account_number;
            private String account_type;

            public String getAccount_type() {
                return account_type;
            }

            public void setAccount_type(String account_type) {
                this.account_type = account_type;
            }

            private String bank_name;
            private String bank_branch;
            private String ifsc_code;
            private String pan_card;
            private String pan_card_url;
            private String no_of_truck;

            public String getCompany_name() {
                return company_name;
            }

            public void setCompany_name(String company_name) {
                this.company_name = company_name;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getPhone_number() {
                return phone_number;
            }

            public void setPhone_number(String phone_number) {
                this.phone_number = phone_number;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getPincode() {
                return pincode;
            }

            public void setPincode(String pincode) {
                this.pincode = pincode;
            }

            public String getBank_name() {
                return bank_name;
            }

            public void setBank_name(String bank_name) {
                this.bank_name = bank_name;
            }

            public String getBank_branch() {
                return bank_branch;
            }

            public void setBank_branch(String bank_branch) {
                this.bank_branch = bank_branch;
            }

            public String getIfsc_code() {
                return ifsc_code;
            }

            public void setIfsc_code(String ifsc_code) {
                this.ifsc_code = ifsc_code;
            }

            public String getPan_card() {
                return pan_card;
            }

            public void setPan_card(String pan_card) {
                this.pan_card = pan_card;
            }

            public String getPan_card_url() {
                return pan_card_url;
            }

            public void setPan_card_url(String pan_card_url) {
                this.pan_card_url = pan_card_url;
            }

            public String getNo_of_truck() {
                return no_of_truck;
            }

            public void setNo_of_truck(String no_of_truck) {
                this.no_of_truck = no_of_truck;
            }
        }
    }

}
