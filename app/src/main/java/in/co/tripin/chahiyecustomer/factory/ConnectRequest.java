package in.co.tripin.chahiyecustomer.factory;

import android.content.Context;

import java.util.Map;



public class ConnectRequest {
    protected Context mContext;
    protected String mRawData;
    protected Map<String, String> mPostParams;
    protected Map<String, String> mHeaderParams;
    protected RequestListener mConnectionListener;
}
