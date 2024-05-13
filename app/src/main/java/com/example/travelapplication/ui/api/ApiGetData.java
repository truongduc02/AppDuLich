package com.example.travelapplication.ui.api;


import com.example.travelapplication.ui.model.Bill;
import com.example.travelapplication.ui.model.BillDetail;
import com.example.travelapplication.ui.model.Rank;
import com.example.travelapplication.ui.model.User;
import com.example.travelapplication.ui.model.Account;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiGetData {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.1.13/json-api/travel-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ApiGetData apiGetData = retrofit.create(ApiGetData.class);

    // @GET("product")
    // Call<ArrayList<Product>> getDataProduct();
    @GET("User")
    Call<User> getUserByEmail(@Query("email") String email, @Query("token") String token);

    @GET("User")
    Call<Rank> getRankByEmail(@Query("email") String email, @Query("token") String token);
    @GET("bill")
    Call<List<Bill>> getBillByEmail(@Query("email") String email, @Query("token") String token);

    @GET("bill/detail")
    Call<List<BillDetail>> getDetailBillById(@Query("id") int id, @Query("token") String token);
    @Multipart
    @POST("bill/delete")
    Call<RegisterReponse> updatestatus(@Query("id") int id, @Query("token") String token,
                                       @Part("status") RequestBody status);
    @POST("bill/order")
    Call<RegisterReponse> createBill(@Body Bill bill, @Query("token") String token);

    @Multipart
    @POST("account/update")
    Call<RegisterReponse> updatescore(@Query("email") String email, @Query("token") String token,
                                       @Part("score") RequestBody score);

    @Multipart
    @POST("account/update")
    Call<RegisterReponse> updateRank(@Query("email") String email, @Query("token") String token,
                                     @Part("rank_id") RequestBody rank_id,
                                      @Part("score") RequestBody score);

    @Multipart
    @POST("account/sendmail")
    Call<RegisterReponse> sendMail(@Query("email") String email,
                                     @Part("role_id") RequestBody rank_id);

    @Multipart
    @POST("account/updatepass")
    Call<RegisterReponse> updatepass(@Query("email") String email,@Query("otp") String otp,@Query("password") String password,
                                   @Part("role_id") RequestBody rank_id);



    @Multipart
    @POST("User/update")
    Call<RegisterReponse> updateUserByEmail(@Query("email") String email, @Query("token") String token,
                                            @Part("name") RequestBody name,
                                            @Part("phone_number") RequestBody phone_number,
                                            @Part("date_of_birth") RequestBody date_of_birth,
                                            @Part("avatar") RequestBody avatar,
                                            @Part("gender") RequestBody gender,
                                            @Part("address") RequestBody address);

    @Multipart
    @POST("User/update")
    Call<RegisterReponse> updateDiachiVaSDTByEmail(@Query("email") String email, @Query("token") String token,
                                            @Part("phone_number") RequestBody phone_number,
                                            @Part("address") RequestBody address);

    @Headers("Content-Type: application/json")
    @POST("account")
    Call<RegisterReponse> register(@Body JsonObject jsonObject);

    @Headers("Content-Type: application/json")
    @POST("account/auth")
    Call<LoginReponse> login(@Body JsonObject jsonObject);

}