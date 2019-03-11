package in.co.tripin.chahiyecustomer.Model.Requests;

import java.util.List;

import in.co.tripin.chahiyecustomer.Model.OrderItemModel;

public class PlaceOrderRequestBody {
    String tapriId,paymentType;
    int totalAmount;
    List<OrderItemModel> details;
    String addressId;

    public PlaceOrderRequestBody(String tapriId, String paymentType, int totalAmount, List<OrderItemModel> details, String addressId) {
        this.tapriId = tapriId;
        this.paymentType = paymentType;
        this.totalAmount = totalAmount;
        this.details = details;
        this.addressId = addressId;
    }

    public String getTapriId() {
        return tapriId;
    }

    public void setTapriId(String tapriId) {
        this.tapriId = tapriId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderItemModel> getDetails() {
        return details;
    }

    public void setDetails(List<OrderItemModel> details) {
        this.details = details;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }
}
