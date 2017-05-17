package com.naver.smarteditor.map_app;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    final int HTTP_INVAILD_400 = 400;



    ImageView img;
    EditText editText;
    Button button;

    List<item> results;

    MyAdapter adapter;
    RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.bt_search);
        editText = (EditText) findViewById(R.id.edit_keyword);
        img = (ImageView) findViewById(R.id.image);

        adapter = new MyAdapter(this, null);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = editText.getText().toString();
                NaverMapService naverService = NaverMapService.retrofit.create(NaverMapService.class);
                Call<Places> call = naverService.naverPlace(query);
                results = new ArrayList<item>();

                call.enqueue(new Callback<Places>() {
                    @Override
                    public void onResponse(Call<Places> call, Response<Places> response) {
//                        if(checkResponseVaild(response) == false){
//                            return;
//                        }

                        for (int i = 0; i < response.body().item.size(); i++) {
                            results.add(response.body().item.get(i));
                        }

                        String url_add = buildRequestStaticMapImgUrlWithCoords(results.get(0).getMapx(), results.get(0).getMapy());
                        Log.d("######", url_add);

                        Glide.with(getBaseContext()).load(url_add).into(img);
                        adapter.changeData(results);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<Places> call, Throwable t) {
                        Log.d("fail","sdsd");
                    }
                });
            }
        });
    }


    public String buildRequestStaticMapImgUrlWithCoords(int x, int y){


        String coords = buildCoords(x,y);

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("openapi.naver.com")
                .appendPath("v1").appendPath("map").appendPath("staticmap.bin")
                .appendQueryParameter("clientId", "gqPejUpvUeMl9HFXQ7h7")
                .appendQueryParameter("url", "com.naver.smarteditor.map_app")
                .appendQueryParameter("crs", "NHN:128")
                .appendQueryParameter("center", coords)
                .appendQueryParameter("level","11")
                .appendQueryParameter("w","320")
                .appendQueryParameter("h","320")
                .appendQueryParameter("baselayer","default")
                .appendQueryParameter("format","png")
                .appendQueryParameter("markers", coords);

        return builder.toString();
    }


    public String buildCoords(int x, int y){
        return String.valueOf(x) + "," + String.valueOf(y);
    }

    public boolean checkResponseVaild(Response<Places> response){
        if(response.code() == HTTP_INVAILD_400 ){
            Log.d("HTTP protocol", "ERROR");
            return true;
        }
//        if(response.body() == 0){
//            Log.d("HTTP protocol", "EMPTY");
//            return true;
//        }
        return false;
    }
}

