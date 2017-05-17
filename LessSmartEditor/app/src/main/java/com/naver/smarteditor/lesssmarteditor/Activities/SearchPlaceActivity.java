package com.naver.smarteditor.lesssmarteditor.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.naver.smarteditor.lesssmarteditor.Adapter.SearchPlaceResultAdatpter;
import com.naver.smarteditor.lesssmarteditor.NaverPlaceService;
import com.naver.smarteditor.lesssmarteditor.Objects.Place;
import com.naver.smarteditor.lesssmarteditor.Objects.PlaceRequestResult;
import com.naver.smarteditor.lesssmarteditor.R;
import com.naver.smarteditor.lesssmarteditor.SearchResultOnClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NAVER on 2017. 5. 17..
 */

public class SearchPlaceActivity extends AppCompatActivity {
    final int REQUEST_FAIL_CODE400 = 400;

    Button mSearchPlaceButton;
    EditText mSearchTarget;
    ImageView mTestImgView;

    SearchPlaceResultAdatpter mResultViewAdapter;
    RecyclerView mSearchResultRecyclerView;

    List<Place> placeSearchResults;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_place);

        placeSearchResults = new ArrayList<Place>();

        mSearchPlaceButton = (Button) findViewById(R.id.searchplace_bt_search);
        mSearchTarget = (EditText) findViewById(R.id.searchplace_et_target);
        mTestImgView = (ImageView) findViewById(R.id.image);

        mResultViewAdapter = new SearchPlaceResultAdatpter(this);
        mResultViewAdapter.setOnResultClickedListener(new SearchResultOnClickListener() {
            @Override
            public void OnClickListener(View v) {
                Toast.makeText(SearchPlaceActivity.this, "hello!", Toast.LENGTH_SHORT).show();
            }
        });

        mSearchResultRecyclerView = (RecyclerView) findViewById(R.id.searchplace_recyclerview);
        mSearchResultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchResultRecyclerView.setHasFixedSize(true);
        mSearchResultRecyclerView.setAdapter(mResultViewAdapter);


        mSearchPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               requestSearchPlaceService();
            }
        });
    }

    public void requestSearchPlaceService(){
        String query = mSearchTarget.getText().toString();
        placeSearchResults = new ArrayList<Place>();

        NaverPlaceService naverService = NaverPlaceService.retrofit.create(NaverPlaceService.class);
        Call<PlaceRequestResult> call = naverService.naverPlace(query);
        call.enqueue(new Callback<PlaceRequestResult>() {
            @Override
            public void onResponse(Call<PlaceRequestResult> call, Response<PlaceRequestResult> response) {

                //Vaild check 필요하다.
                //내부에 이미지 로딩까지 구현되어 있음 분리 필요
                //adapter 데이터 갱신 함수 만들 것

                for (int i = 0; i < response.body().getPlaces().size(); i++) {
                    placeSearchResults.add(response.body().getPlaces().get(i));
                }

                String url_add
                        = buildRequestStaticMapImgUrlWithCoords(placeSearchResults.get(0).getKatechMapX(), placeSearchResults.get(0).getKatechMapY());

                Glide.with(getBaseContext()).load(url_add).into(mTestImgView);
                mResultViewAdapter.changeData(placeSearchResults);
                mResultViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PlaceRequestResult> call, Throwable t) {
                Log.d("fail","sdsd");
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

    public boolean checkResponseVaild(Response<PlaceRequestResult> response){
        if(response.code() == REQUEST_FAIL_CODE400 ){
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