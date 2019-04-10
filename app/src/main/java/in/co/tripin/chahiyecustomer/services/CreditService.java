package in.co.tripin.chahiyecustomer.services;

import in.co.tripin.chahiyecustomer.Model.responce.OrderHistoryResponce;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface CreditService {

    @GET(" /api/v1/office/{officeId}/orders")
    Call<OrderHistoryResponce> getCreditOrders(@Header("token") String token, @Path("officeId") String officeId);
}
