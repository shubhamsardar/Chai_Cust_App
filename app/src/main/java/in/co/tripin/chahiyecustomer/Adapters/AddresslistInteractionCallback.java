package in.co.tripin.chahiyecustomer.Adapters;

import in.co.tripin.chahiyecustomer.Model.responce.UserAddress;

public interface AddresslistInteractionCallback {

    public void onAddressSelected(UserAddress.Data address);

    public void onAddressRemoved(UserAddress.Data address);
}
