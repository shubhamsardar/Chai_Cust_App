package in.co.tripin.chahiyecustomer.services;

import in.co.tripin.chahiyecustomer.Model.Requests.OfficeRequestBody;
import in.co.tripin.chahiyecustomer.dataproviders.CommonResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OfficeService {


    @GET("/api/v1/office?office=ALL")
    Call<OfficeRequestBody> getOffice();
}
