package `in`.co.tripin.chahiyecustomer.retrofit


import `in`.co.tripin.chahiyecustomer.helper.Constants
import `in`.co.tripin.chahiyecustomer.paytm.Checksum
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.Field
import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded




public interface APIService {

//
//    @GET("search/users")
//    fun search(@Query("q") query: String,
//               @Query("page") page: Int,
//               @Query("per_page") perPage: Int): Observable<Result>


    /**
     * Companion object to create the ApiService
     */
    companion object Factory {
        fun create(): APIService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Constants.BASE_URL)
                    .build()

            return retrofit.create(APIService::class.java)
        }
    }

    @FormUrlEncoded
    @POST("generateChecksum.php")
    fun getChecksum(
            @Field("MID") mId: String,
            @Field("ORDER_ID") orderId: String,
            @Field("CUST_ID") custId: String,
            @Field("CHANNEL_ID") channelId: String,
            @Field("TXN_AMOUNT") txnAmount: String,
            @Field("WEBSITE") website: String,
            @Field("CALLBACK_URL") callbackUrl: String,
            @Field("INDUSTRY_TYPE_ID") industryTypeId: String
    ): Call<Checksum>
}
