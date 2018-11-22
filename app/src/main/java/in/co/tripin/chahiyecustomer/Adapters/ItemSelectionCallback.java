package in.co.tripin.chahiyecustomer.Adapters;

import in.co.tripin.chahiyecustomer.Model.responce.UserAddress;

public interface ItemSelectionCallback {

    public void onitemAdded(Double cost, int quant);

    public void onItemRemoved(Double cost, int quant);
}
