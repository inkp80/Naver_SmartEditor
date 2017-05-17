package com.naver.smarteditor.map_app;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by NAVER on 2017. 5. 17..
 */

public interface NaverStaticMapService {//    @Headers({"X-Naver-Client-Id: gqPejUpvUeMl9HFXQ7h7", "X-Naver-Client-Secret: 5QInzrHLSf"})
    @GET("staticmap.bin")
    Call<MapImage> naverMap(@Query("dlientId") String id
            , @Query("url") String serviceUrl
            , @Query("crs") String crs
            , @Query("center") String coords
            , @Query("level") String lev, @Query("w") String w, @Query("h") String h, @Query("baselayer") String baselayer, @Query("format") String form);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/v1/map/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}