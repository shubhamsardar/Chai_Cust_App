package in.co.tripin.chahiyecustomer.Managers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import in.co.tripin.chahiyecustomer.Communication.RquestHandler;
import in.co.tripin.chahiyecustomer.Model.responce.Tapri;
import in.co.tripin.chahiyecustomer.dataproviders.RequestProvider;
import in.co.tripin.chahiyecustomer.factory.RequestListener;
import in.co.tripin.chahiyecustomer.factory.Response;

public class TapriManager implements RequestListener {
    private Context mContext;
    private RequestProvider mRequestProvider;
    private TapriListListener mTapriListListener;

    public TapriManager(Context mContext) {
        this.mContext = mContext;
        this.mRequestProvider = new RequestProvider(mContext);
    }

    public void getTupriList(String lat, String lng, TapriListListener tapriListListener) {
        RquestHandler rquestHandler = new RquestHandler(mContext);
        rquestHandler.send(mRequestProvider.getTapriListRequest(this, lat, lng));
        mTapriListListener = tapriListListener;
    }

    @Override
    public void onResponse(int responseResult, int apiTag, Response response) {
        Tapri tapri = (Tapri) response;
        Toast.makeText(mContext,"ONSuccess", Toast.LENGTH_SHORT).show();
        if(tapri.getStatus().equalsIgnoreCase("Success"))
        mTapriListListener.onSuccess(tapri.getData());
    }

    @Override
    public void onError(int responseResult, int apiTag, String message, Response response) {
        Toast.makeText(mContext,"ON-Failed", Toast.LENGTH_SHORT).show();
        Log.d("ERR", String.valueOf(message));
    }

    public interface TapriListListener {
        void onSuccess(Tapri.Data[] tapriData);

        void onFailed(String message);
    }
}
