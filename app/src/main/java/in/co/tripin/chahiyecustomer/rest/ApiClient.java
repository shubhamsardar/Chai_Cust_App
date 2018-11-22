package in.co.tripin.chahiyecustomer.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Ravishankar
 * @version 1.0
 * @since 07-05-2018
 */
public class ApiClient {
    public static final String BASE_URL = "139.59.70.142:3055/api/v1/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}
