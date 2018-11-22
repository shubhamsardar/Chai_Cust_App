package in.co.tripin.chahiyecustomer.rest;

import in.co.tripin.chahiyecustomer.Model.Requests.SignUpRequest;
import in.co.tripin.chahiyecustomer.Model.responce.SignUpResponse;
import in.co.tripin.chahiyecustomer.paytm.Checksum;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author Ravishankar
 * @version 1.0
 * @since 07-05-2018
 */
public interface ApiInterface {
    @Headers("Content-Type: application/json")
    @POST("user/signUp")
    Call<SignUpResponse> signUp(@Body String body);

    @FormUrlEncoded
    @POST("generateChecksum.php")
    Call<Checksum> getChecksum(
            @Field("MID") String mId,
            @Field("ORDER_ID") String orderId,
            @Field("CUST_ID") String custId,
            @Field("CHANNEL_ID") String channelId,
            @Field("TXN_AMOUNT") String txnAmount,
            @Field("WEBSITE") String website,
            @Field("CALLBACK_URL") String callbackUrl,
            @Field("INDUSTRY_TYPE_ID") String industryTypeId
    );

}
