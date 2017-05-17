package com.example.naver.library_ex.Retrofit2;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by NAVER on 2017. 5. 15..
 */

public interface NaverService {
    @Headers({"X-Naver-Client-Id: gbnn1os3zDfvajMF7RLC", "X-Naver-Client-Secret: b2HdZMiLb4"})
    @GET("local.json")

    Call<List<Places>> naverPlace(@Query("query") String place);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/v1/search/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


}