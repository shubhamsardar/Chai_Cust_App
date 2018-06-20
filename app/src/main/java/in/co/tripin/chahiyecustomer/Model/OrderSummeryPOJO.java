package in.co.tripin.chahiyecustomer.Model;

import java.io.Serializable;
import java.util.ArrayList;

import in.co.tripin.chahiyecustomer.Model.responce.TapriMenuResponce;
import in.co.tripin.chahiyecustomer.Model.responce.UserAddress;

public class OrderSummeryPOJO implements Serializable {

    private String mTapriId;
    private String mTapriName;
    private String mTotalCost;
    private UserAddress.Data mAddress;
    private String mPaymentMethod;
    private ArrayList<TapriMenuResponce.Data.Item> mItems;

    public OrderSummeryPOJO(String mTapriId, String mTapriName, String mTotalCost, UserAddress.Data mAddress, String mPaymentMethod, ArrayList<TapriMenuResponce.Data.Item> mItems) {
        this.mTapriId = mTapriId;
        this.mTapriName = mTapriName;
        this.mTotalCost = mTotalCost;
        this.mAddress = mAddress;
        this.mPaymentMethod = mPaymentMethod;
        this.mItems = mItems;
    }

    public String getmPaymentMethod() {
        return mPaymentMethod;
    }

    public void setmPaymentMethod(String mPaymentMethod) {
        this.mPaymentMethod = mPaymentMethod;
    }

    public ArrayList<TapriMenuResponce.Data.Item> getmItems() {
        return mItems;
    }

    public void setmItems(ArrayList<TapriMenuResponce.Data.Item> mItems) {
        this.mItems = mItems;
    }

    public String getmTapriId() {
        return mTapriId;
    }

    public void setmTapriId(String mTapriId) {
        this.mTapriId = mTapriId;
    }

    public String getmTapriName() {
        return mTapriName;
    }

    public void setmTapriName(String mTapriName) {
        this.mTapriName = mTapriName;
    }

    public String getmTotalCost() {
        return mTotalCost;
    }

    public void setmTotalCost(String mTotalCost) {
        this.mTotalCost = mTotalCost;
    }


    public UserAddress.Data getmAddress() {
        return mAddress;
    }

    public void setmAddress(UserAddress.Data mAddress) {
        this.mAddress = mAddress;
    }

    public String getFullAddressString(){
        return getmAddress().getLandmark()+", "
                +getmAddress().getFlatSociety()+", "
                +getmAddress().getAddressLine1()+", "
                +getmAddress().getAddressLine2()+", "
                +getmAddress().getCity()+", "
                +getmAddress().getCountry();
    }
}
