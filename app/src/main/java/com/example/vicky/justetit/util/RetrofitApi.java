package com.example.vicky.justetit.util;

import com.example.vicky.justetit.pojo.Recipes;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public class RetrofitApi {

    public static final String baseUrl = "http://www.recipepuppy.com/";

    public static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static ApiInterface getApiInterfaceInstance() {
        return getRetrofitInstance().create(ApiInterface.class);
    }

    public interface ApiInterface {
        @GET("api/")
        Call<Recipes> recipes(@Query("p") String p);

        @GET("api/")
        Call<Recipes> leftovers(@Query("i") String i, @Query("p") String p);

        @GET("api/")
        Call<Recipes> search(@Query("q") String i, @Query("p") String p);
    }
}
