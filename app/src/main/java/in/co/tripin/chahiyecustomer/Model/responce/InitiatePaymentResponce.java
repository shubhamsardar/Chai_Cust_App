package in.co.tripin.chahiyecustomer.Model.responce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InitiatePaymentResponce {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("hash")
        @Expose
        private String hash;
        @SerializedName("txnId")
        @Expose
        private String txnId;
        @SerializedName("transactionId")
        @Expose
        private String transactionId;
        @SerializedName("user")
        @Expose
        private User user;
        @SerializedName("hashObject")
        @Expose
        private HashObject hashObject;

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public String getTxnId() {
            return txnId;
        }

        public void setTxnId(String txnId) {
            this.txnId = txnId;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public HashObject getHashObject() {
            return hashObject;
        }

        public void setHashObject(HashObject hashObject) {
            this.hashObject = hashObject;
        }

    }

    public class HashObject {

        @SerializedName("MID")
        @Expose
        private String mID;
        @SerializedName("INDUSTRY_TYPE_ID")
        @Expose
        private String iNDUSTRYTYPEID;
        @SerializedName("CHANNEL_ID")
        @Expose
        private String cHANNELID;
        @SerializedName("WEBSITE")
        @Expose
        private String wEBSITE;
        @SerializedName("CALLBACK_URL")
        @Expose
        private String cALLBACKURL;
        @SerializedName("ORDER_ID")
        @Expose
        private String oRDERID;
        @SerializedName("CUST_ID")
        @Expose
        private String cUSTID;
        @SerializedName("TXN_AMOUNT")
        @Expose
        private String tXNAMOUNT;
        @SerializedName("EMAIL")
        @Expose
        private String eMAIL;
        @SerializedName("MOBILE_NO")
        @Expose
        private String mOBILENO;
        @SerializedName("CHECKSUMHASH")
        @Expose
        private String cHECKSUMHASH;

        public String getMID() {
            return mID;
        }

        public void setMID(String mID) {
            this.mID = mID;
        }

        public String getINDUSTRYTYPEID() {
            return iNDUSTRYTYPEID;
        }

        public void setINDUSTRYTYPEID(String iNDUSTRYTYPEID) {
            this.iNDUSTRYTYPEID = iNDUSTRYTYPEID;
        }

        public String getCHANNELID() {
            return cHANNELID;
        }

        public void setCHANNELID(String cHANNELID) {
            this.cHANNELID = cHANNELID;
        }

        public String getWEBSITE() {
            return wEBSITE;
        }

        public void setWEBSITE(String wEBSITE) {
            this.wEBSITE = wEBSITE;
        }

        public String getCALLBACKURL() {
            return cALLBACKURL;
        }

        public void setCALLBACKURL(String cALLBACKURL) {
            this.cALLBACKURL = cALLBACKURL;
        }

        public String getORDERID() {
            return oRDERID;
        }

        public void setORDERID(String oRDERID) {
            this.oRDERID = oRDERID;
        }

        public String getCUSTID() {
            return cUSTID;
        }

        public void setCUSTID(String cUSTID) {
            this.cUSTID = cUSTID;
        }

        public String getTXNAMOUNT() {
            return tXNAMOUNT;
        }

        public void setTXNAMOUNT(String tXNAMOUNT) {
            this.tXNAMOUNT = tXNAMOUNT;
        }

        public String getEMAIL() {
            return eMAIL;
        }

        public void setEMAIL(String eMAIL) {
            this.eMAIL = eMAIL;
        }

        public String getMOBILENO() {
            return mOBILENO;
        }

        public void setMOBILENO(String mOBILENO) {
            this.mOBILENO = mOBILENO;
        }

        public String getCHECKSUMHASH() {
            return cHECKSUMHASH;
        }

        public void setCHECKSUMHASH(String cHECKSUMHASH) {
            this.cHECKSUMHASH = cHECKSUMHASH;
        }

    }

    public class User {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("email")
        @Expose
        private String email;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

    }

}