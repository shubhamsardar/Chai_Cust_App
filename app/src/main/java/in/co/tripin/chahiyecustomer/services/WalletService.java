package in.co.tripin.chahiyecustomer.services;

import in.co.tripin.chahiyecustomer.Model.Requests.OfficeRequestBody;
import in.co.tripin.chahiyecustomer.Model.responce.WalletResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface WalletService {


    @GET("/api/v2/users/wallet/balance")
    Call<WalletResponse> getWallet(@Header("token")String token);
}
