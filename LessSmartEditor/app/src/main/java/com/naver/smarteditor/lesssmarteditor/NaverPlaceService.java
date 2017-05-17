package com.naver.smarteditor.lesssmarteditor;

import com.naver.smarteditor.lesssmarteditor.Objects.PlaceRequestResult;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by NAVER on 2017. 5. 17..
 */

public interface NaverPlaceService {
    @Headers({"X-Naver-Client-Id: gqPejUpvUeMl9HFXQ7h7", "X-Naver-Client-Secret: 5QInzrHLSf"})
    @GET("v1/search/local.json")
    Call<PlaceRequestResult> naverPlace(@Query("query") String place);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
