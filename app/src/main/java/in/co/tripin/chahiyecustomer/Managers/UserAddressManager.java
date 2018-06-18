package in.co.tripin.chahiyecustomer.Managers;

import android.content.Context;

import in.co.tripin.chahiyecustomer.Communication.RquestHandler;
import in.co.tripin.chahiyecustomer.Model.responce.Tapri;
import in.co.tripin.chahiyecustomer.Model.responce.UserAddress;
import in.co.tripin.chahiyecustomer.dataproviders.RequestProvider;
import in.co.tripin.chahiyecustomer.factory.RequestListener;
import in.co.tripin.chahiyecustomer.factory.Response;

public class UserAddressManager implements RequestListener{

    private Context mContext;
    private RequestProvider mRequestProvider;
    private AddressListListner mAddressListListner;

    public UserAddressManager(Context mContext) {
        this.mContext = mContext;
        this.mRequestProvider = new RequestProvider(mContext);
    }

    public void getAddressList(UserAddressManager.AddressListListner addressListListner) {
        RquestHandler rquestHandler = new RquestHandler(mContext);
        rquestHandler.send(mRequestProvider.getAddressListRequest(this));
        mAddressListListner = addressListListner;
    }


    @Override
    public void onResponse(int responseResult, int apiTag, Response response) {

    }

    @Override
    public void onError(int responseResult, int apiTag, String message, Response response) {

    }

    public interface AddressListListner {
        void onSuccess(UserAddress.Data[] addressData);

        void onFailed(String message);
    }
}
