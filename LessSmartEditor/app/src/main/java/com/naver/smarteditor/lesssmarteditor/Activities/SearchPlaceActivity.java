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
import com.naver.smarteditor.lesssmarteditor.Objects.PlaceItem;
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

    List<PlaceItem> placeItemList;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_place);

        placeItemList = new ArrayList<PlaceItem>();

        mSearchPlaceButton = (Button) findViewById(R.id.searchplace_bt_search);
        mSearchTarget = (EditText) findViewById(R.id.searchplace_et_target);
        mTestImgView = (ImageView) findViewById(R.id.image);
        mSearchResultRecyclerView = (RecyclerView) findViewById(R.id.searchplace_recyclerview);

        mResultViewAdapter = new SearchPlaceResultAdatpter(this);
        mResultViewAdapter.setOnResultClickedListener(new SearchResultOnClickListener() {
            @Override
            public void OnClickListener(View v, int x, int y) {
                setStaticMapToComponent(x, y);
            }
        });


        mSearchPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               requestSearchPlaceService();
            }
        });
    }

    public void requestSearchPlaceService(){
        String query = mSearchTarget.getText().toString();


        NaverPlaceService naverService = NaverPlaceService.retrofit.create(NaverPlaceService.class);
        Call<PlaceRequestResult> call = naverService.naverPlace(query);
        call.enqueue(new Callback<PlaceRequestResult>() {
            @Override
            public void onResponse(Call<PlaceRequestResult> call, Response<PlaceRequestResult> response) {
                //Vaild check 필요하다.
                placeItemList = response.body().getPlaces();
                renewingAdapter();
            }

            @Override
            public void onFailure(Call<PlaceRequestResult> call, Throwable t) {
            }
        });
    }


    public String buildStaticMapUrlWithCoords(int x, int y){

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
                .appendQueryParameter("w","160")
                .appendQueryParameter("h","160")
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

    public void setStaticMapToComponent(int x, int y){
        String mapUrl = buildStaticMapUrlWithCoords(x, y);
        Glide.with(this).load(mapUrl).into(mTestImgView);
    }

    public void renewingAdapter(){
        mResultViewAdapter.changeData(placeItemList);
        mResultViewAdapter.notifyDataSetChanged();
    }

    public void initRecyclerView(){
        mSearchResultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchResultRecyclerView.setHasFixedSize(true);
        mSearchResultRecyclerView.setAdapter(mResultViewAdapter);

    }
}