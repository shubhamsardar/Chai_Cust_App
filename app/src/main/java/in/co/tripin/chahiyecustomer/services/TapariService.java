package in.co.tripin.chahiyecustomer.services;

import in.co.tripin.chahiyecustomer.Model.Requests.OfficeRequestBody;
import in.co.tripin.chahiyecustomer.Model.Requests.PlaceOrderRequestBody;
import in.co.tripin.chahiyecustomer.Model.responce.AddressResponse;
import in.co.tripin.chahiyecustomer.Model.responce.TapriMenuResponce;
import in.co.tripin.chahiyecustomer.Model.responce.TapriMenuResponses;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TapariService {


    @GET("/api/v1/tapri/{tapriId}/items/all")
    Call<TapriMenuResponses> getTapriMenu(@Header("token")String token, @Path("tapriId")String tapriId);

    @POST("/api/v2/initiateOrder")
    Call<PlaceOrderRequestBody> toPlaceOrder(@Header("token")String token, @Body PlaceOrderRequestBody placeOrderRequestBody);

    @GET("/api/v1/document/download")
    Call<ResponseBody> downloadImage(@Header("token") String token, @Query("path") String logoUrlPath);
}
