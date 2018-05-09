package in.co.tripin.chahiyecustomer.rest;

import in.co.tripin.chahiyecustomer.Model.Requests.SignUpRequest;
import in.co.tripin.chahiyecustomer.Model.responce.SignUpResponse;
import retrofit2.Call;
import retrofit2.http.Body;
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
}
