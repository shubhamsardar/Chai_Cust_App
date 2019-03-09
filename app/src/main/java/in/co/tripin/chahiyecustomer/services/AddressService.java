package in.co.tripin.chahiyecustomer.services;

import in.co.tripin.chahiyecustomer.Model.responce.AddressResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface AddressService {
    @GET("api/v1/users/address?userAddress=All")
    Call<AddressResponse> getAddress(@Header("token")String token);
}
