package com.naver.smarteditor.lesssmarteditor.views.map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.naver.smarteditor.lesssmarteditor.MyApplication;
import com.naver.smarteditor.lesssmarteditor.NaverPlaceService;
import com.naver.smarteditor.lesssmarteditor.R;
import com.naver.smarteditor.lesssmarteditor.SearchResultOnClickListener;
import com.naver.smarteditor.lesssmarteditor.adpater.map.SearchPlaceResultAdatpter;
import com.naver.smarteditor.lesssmarteditor.data.api.naver_map.PlaceItem;
import com.naver.smarteditor.lesssmarteditor.data.api.naver_map.PlaceItemParcelable;
import com.naver.smarteditor.lesssmarteditor.data.api.naver_map.PlaceRequestResult;
import com.naver.smarteditor.lesssmarteditor.views.edit.EditorActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.naver.smarteditor.lesssmarteditor.MyApplication.MAPINFO_PARCEL;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.RETROFIT_FAIL400;
import static com.naver.smarteditor.lesssmarteditor.util.map.MapUtils.buildCoords;
import static com.naver.smarteditor.lesssmarteditor.util.map.MapUtils.buildStaticMapUrlWithCoords;

/**
 * Created by NAVER on 2017. 5. 17..
 */

public class SearchPlaceActivity extends AppCompatActivity {

    Button mSearchPlaceButton;
    EditText mSearchTarget;

    SearchPlaceResultAdatpter mResultViewAdapter;
    RecyclerView mSearchResultRecyclerView;

    List<PlaceItem> placeItemList;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_place);

        placeItemList = new ArrayList<>();

        mSearchPlaceButton = (Button) findViewById(R.id.searchplace_bt_search);
        mSearchTarget = (EditText) findViewById(R.id.searchplace_et_target);
        mSearchResultRecyclerView = (RecyclerView) findViewById(R.id.searchplace_recyclerview);

        initAdapter();
        initRecyclerView();

        mSearchPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSearchPlaceService();
            }
        });
    }

    public void requestSearchPlaceService(){
        String query = mSearchTarget.getText().toString();
        if(query.length() == 0 || query == null){
            //do nothing -
            return;
        }
        NaverPlaceService naverService = NaverPlaceService.retrofit.create(NaverPlaceService.class);
        Call<PlaceRequestResult> call = naverService.naverPlace(query);

        call.enqueue(new Callback<PlaceRequestResult>() {
            @Override
            public void onResponse(Call<PlaceRequestResult> call, Response<PlaceRequestResult> response) {
                //TODO : vaildation check
                placeItemList = response.body().getPlaces();
                renewingAdapter();
            }

            @Override
            public void onFailure(Call<PlaceRequestResult> call, Throwable t) {
                //TODO: 예외 처리 -
            }
        });
    }


    public String getStaticMapUri(int x, int y){
        return buildStaticMapUrlWithCoords(x, y);
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

    public void initAdapter(){
        mResultViewAdapter = new SearchPlaceResultAdatpter(this);
        mResultViewAdapter.setOnResultClickedListener(new SearchResultOnClickListener() {
            @Override
            public void OnClickListener(View v, int x, int y, int position) {
//                setStaticMapToComponent(x, y);
                String mapUri = getStaticMapUri(x, y);

                PlaceItemParcelable passer = new PlaceItemParcelable(placeItemList.get(position).getPlaceName().toString(), placeItemList.get(position).getPlaceAddress().toString(), buildCoords(x,y).toString(), mapUri);

                Intent intent = new Intent(SearchPlaceActivity.this, EditorActivity.class);
                intent.putExtra(MAPINFO_PARCEL, passer);

                setResult(RESULT_OK, intent);
                finish();

            }
        });
    }

    public boolean checkResponseVaild(Response<PlaceRequestResult> response){
        if(response.code() == RETROFIT_FAIL400 ){
            Log.d("HTTP protocol", "ERROR");
            return true;
        }
        return false;
    }
}