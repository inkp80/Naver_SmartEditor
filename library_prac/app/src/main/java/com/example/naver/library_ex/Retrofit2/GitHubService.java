package com.example.naver.library_ex.Retrofit2;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by NAVER on 2017. 5. 10..
 */

public interface GitHubService {
    @GET("repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> repoContributors(@Path("owner") String owner, @Path("repo") String repo);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}



//retrofit = new Retrofit.Builder()
//        .baseUrl("https://api.github.com/")
//        .addConverterFactory(GsonConverterFactory.create())
//        .build();
//        }
