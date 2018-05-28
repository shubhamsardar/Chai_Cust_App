package in.co.tripin.chahiyecustomer.Communication;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import in.co.tripin.chahiyecustomer.Managers.ResponseParser;
import in.co.tripin.chahiyecustomer.controller.AppController;
import in.co.tripin.chahiyecustomer.dataproviders.ParamsProvider;
import in.co.tripin.chahiyecustomer.dataproviders.ProviderUtils;
import in.co.tripin.chahiyecustomer.factory.ConnectRequest;
import in.co.tripin.chahiyecustomer.factory.Connector;
import in.co.tripin.chahiyecustomer.factory.Request;
import in.co.tripin.chahiyecustomer.factory.RequestListener;
import in.co.tripin.chahiyecustomer.factory.RequestPool;
import in.co.tripin.chahiyecustomer.helper.Logger;
import in.co.tripin.chahiyecustomer.volly.DataModel;
import in.co.tripin.chahiyecustomer.volly.MultipartRequest;




public class RquestHandler extends ConnectRequest implements Connector {
    private static final String CONTENT_TYPE = "application/json";

    private Request mRequest;
    private RequestListener mCallback;

    public RquestHandler(Context context) {
        this.mContext = context;
    }

    public void send(Request request) {
        mRequest = request;
        mRawData = request.getRawData();
        mPostParams = request.getPostParams();
        mCallback = request.getConnectionListener();
        if (mRequest.getType() == Request.Method.MULTIPART) {
            multiPart();
            Logger.v("\nMultiPart Request : API  : " + mRequest.getRequestUrl() + " : " + mRequest.getPostParams());
        } else if (mRequest.getType() == Request.Method.SEND_JSON) {
            Logger.v("\nRequest : API  : " + mRequest.getRequestUrl());
            sendJson();
        } else if (mRequest.getType() == Request.Method.GET) {
            sendGetRequest();
        } else {
                Logger.v("\nRequest : API  : " + mRequest.getRequestUrl() + " : " + mRequest.getRequestUrl());
                connect();
            }

    }

    @Override
    public void setPostParams(Map<String, String> postParams) {

    }

    @Override
    public void connect() {
        cancelRequest();

        StringRequest postRequest = new StringRequest(mRequest.getType(), mRequest.getRequestUrl(),
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Logger.v("\nResponse : API  : " + mRequest.getRequestUrl() + " : " + response);
//                        mCallback.onResponse(ResponseResults.RESPONSE_OK, response);
                        parseJson(response);
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorResponsHandler(error);

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                if (mPostParams == null) {
                    Logger.v("Request : Null Params");
                    return null;
                } else {
                    Logger.v("\nRequest : API :  " + mRequest.getTag() + " : " + mPostParams.toString());
                    return mPostParams;
                }

            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (mRequest.getHeaderParams() == null) {
                    return ParamsProvider.getHeaderParams();
                } else {
                    return mRequest.getHeaderParams();
                }
            }

            @Override
            public String getBodyContentType() {
                return CONTENT_TYPE;
            }
        };
        postRequest.setRetryPolicy(ProviderUtils.getRetryPolicy());
        postRequest.setTag(mRequest.getTag());
        RequestPool.getInstance(this.mContext).addToRequestQueue(postRequest);
    }


    public void cancelRequest() {
        RequestPool.getInstance(this.mContext).cancellAllPreviousRequestWithSameTag(String.valueOf(mRequest.getTag()));
    }


    @Override
    public void parseJson(String response) {
        ResponseParser responseParser = new ResponseParser();
        responseParser.parseJson(response, mRequest);
    }

    @Override
    public void setHeaderParams(Map<String, String> postParams) {
    }

    private void sendGetRequest() {
        cancelRequest();

            StringRequest postRequest = new StringRequest(mRequest.getType(), mRequest.getRequestUrl(),
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Logger.v("\nResponse : API  : " + mRequest.getRequestUrl() + " : " + response);
//                        mCallback.onResponse(ResponseResults.RESPONSE_OK, response);
                            parseJson(response);
                        }
                    }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    errorResponsHandler(error);

                }
            });
            postRequest.setRetryPolicy(ProviderUtils.getRetryPolicy());
            postRequest.setTag(mRequest.getTag());
            RequestPool.getInstance(this.mContext).addToRequestQueue(postRequest);
    }

    //========================== MULTIPART REQUEST ===================================

    private void multiPart() {
        MultipartRequest multipartRequest = new MultipartRequest(Request.Method.POST,
                mRequest.getRequestUrl(), new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                Logger.v("\nResponse : API  : " + resultResponse);
                parseJson(resultResponse);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        errorResponsHandler(error);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return mRequest.getPostParams();
            }

            @Override
            protected Map<String, DataModel> getByteData() {
                return mRequest.getByteParams();
            }
        };
        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(multipartRequest, "tag_json_obj");

//        multipartRequest.setRetryPolicy(ProviderUtils.getRetryPolicy());
//        multipartRequest.setTag(mRequest.getTag());
//        RequestPool.getInstance(this.mContext).addToRequestQueue(multipartRequest);
//        AppController.getInstance().addToRequestQueue(multipartRequest, "json_obj_req");
    }

    private void errorResponsHandler(VolleyError error) {
        String errorMessage = "Unknown error";
        NetworkResponse errorResponse = error.networkResponse;
        if (errorResponse == null) {
            if (error.getClass().equals(TimeoutError.class)) {
                errorMessage = "Request timeout";
            } else if (error.getClass().equals(NoConnectionError.class)) {
                errorMessage = "Failed to connect server";
            }
        } else {
            if (error.networkResponse != null) {
                switch (error.networkResponse.statusCode) {
                    case 400:
                        errorMessage = "400 : Resource not found : " + error.getMessage();
                        break;
                    case 401:
                        errorMessage = error.getMessage();
                        break;
                    case 404:
                        errorMessage = "404 : Check your inputs : " + error.getMessage();
                        break;
                    case 500:
                        errorMessage = "500 : Something is getting wrong : " + error.getMessage();
                        break;
                }
            }
        }
        mCallback.onError(RequestListener.CONNECTION_ERROR, mRequest.getTag(), errorMessage, null);
        Logger.v("\nResponse : Error : mApiTag : " + mRequest.getRequestUrl() + ", " + errorMessage);
    }

    //================= SEND JSON SPECIAL CASE =================

    public void sendJson() {
        cancelRequest();

        StringRequest jsonRequest = new StringRequest(Request.Method.POST, mRequest.getRequestUrl(),
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Logger.v("\nResponse : API  : " + mRequest.getRequestUrl() + " : " + response);
//                        mCallback.onResponse(ResponseResults.RESPONSE_OK, response);
                        parseJson(response);
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorResponsHandler(error);

            }
        }) {

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRawData == null ? null : mRawData.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRawData, "utf-8");
                    return null;
                }
            }

            @Override
            protected Map<String, String> getParams() {
                if (mPostParams == null) {
                    Logger.v("Request : Null Params");
                    return null;
                } else {
                    Logger.v("\nRequest : API :  " + mRequest.getTag() + " : " + mPostParams.toString());
                    return mPostParams;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (mRequest.getHeaderParams() == null) {
                    return ParamsProvider.getHeaderParams();
                } else {
                    return mRequest.getHeaderParams();
                }
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        jsonRequest.setRetryPolicy(ProviderUtils.getRetryPolicy());
        jsonRequest.setTag(mRequest.getTag());
        RequestPool.getInstance(this.mContext).addToRequestQueue(jsonRequest);
    }

}
