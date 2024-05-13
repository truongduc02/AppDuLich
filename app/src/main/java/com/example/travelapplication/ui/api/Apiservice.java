package com.example.travelapplication.ui.api;

import com.example.travelapplication.ui.model.Article;
import com.example.travelapplication.ui.model.GioHang;
import com.example.travelapplication.ui.model.Tour;
import com.example.travelapplication.ui.model.UserModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Apiservice {

    //http://localhost:81/json-api/travel-api/product?category_id=2
    //https://jsonplaceholder.typicode.com/posts?userId=1
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
    Apiservice apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.13/json-api/travel-api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(Apiservice.class);
    @GET("product")
    Call<List<Tour>> getAllTour();
    @GET("product")
    Call<List<Tour>> getListTour(@Query("category_id") int category_id, @Query("token") String token);
    @GET("article")
    Call<List<Article>> getListArticle();

    @POST("cart/")
    Call<Void> postTourToDB(@Body UserModel userModel);

    @POST("cart/info")
    Call<List<GioHang>> getListGioHang(@Query("user_id") int user_id, @Query("token") String token);

    @POST("cart/quantity")
    Call<Void> postChangeCart(@Query("user_id") int user_id, @Query("product_id") int product_id, @Query("action") String action, @Query("departure") String departure, @Query("token") String token );

    @POST("cart/del")
    Call<Void> deleteCart(@Query("user_id") int user_id, @Query("product_id") int product_id, @Query("departure") String departure, @Query("token") String token);


    // thong tin san pham
    Apiservice apiServiceDetail = new Retrofit.Builder()
            .baseUrl("http://192.168.1.13/json-api/travel-api/product/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(Apiservice.class);
    @POST("detail")
    Call<Tour> getTourDetail(@Query("id") int id,@Query("token") String token);
    // thong tin tin tuc http://172.20.10.2:81/json-api/travel-api/article/detail?id=1
    Apiservice apiServiceArticleDetail = new Retrofit.Builder()
            .baseUrl("http://192.168.1.13/json-api/travel-api/article/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(Apiservice.class);
    @POST("detail")
    Call<Article> getArticleDetail(@Query("id") int id,@Query("token") String token);
}
