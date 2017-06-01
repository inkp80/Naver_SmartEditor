package com.naver.smarteditor.lesssmarteditor.views.map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.naver.smarteditor.lesssmarteditor.LogController;
import com.naver.smarteditor.lesssmarteditor.NaverPlaceService;
import com.naver.smarteditor.lesssmarteditor.R;
import com.naver.smarteditor.lesssmarteditor.listener.OnPlaceItemClickListener;
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
import static com.naver.smarteditor.lesssmarteditor.MyApplication.RETROFIT_SUCCESS;
import static com.naver.smarteditor.lesssmarteditor.views.map.utils.MapUtils.buildCoords;
import static com.naver.smarteditor.lesssmarteditor.views.map.utils.MapUtils.buildStaticMapUrlWithCoords;

/**
 * Created by NAVER on 2017. 5. 17..
 */

public class SearchPlaceActivity extends AppCompatActivity {
    private final String TAG = "SearchPlaceActivity";
    private boolean localLogPermission = true;

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
            Toast.makeText(this, "검색 내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
            //do nothing - 요청이 없음
            return;
        }
        NaverPlaceService naverService = NaverPlaceService.retrofit.create(NaverPlaceService.class);
        Call<PlaceRequestResult> call = naverService.naverPlace(query);

        call.enqueue(new Callback<PlaceRequestResult>() {
            @Override
            public void onResponse(Call<PlaceRequestResult> call, Response<PlaceRequestResult> response) {
                placeItemList = response.body().getPlaces();
                if(placeItemList.size() == 0){
                    Toast.makeText(SearchPlaceActivity.this, "결과 없음", Toast.LENGTH_SHORT).show();
                    return;
                }
                renewingAdapter();
            }

            @Override
            public void onFailure(Call<PlaceRequestResult> call, Throwable t) {
                Toast.makeText(SearchPlaceActivity.this, String.valueOf(t.getCause()), Toast.LENGTH_SHORT).show();
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
        mResultViewAdapter.setOnResultClickedListener(new OnPlaceItemClickListener() {
            @Override
            public void OnPlaceItemClick(View v, int x, int y, int position) {
                String mapUri = getStaticMapUri(x, y);

                PlaceItemParcelable passer = new PlaceItemParcelable(
                        placeItemList.get(position).getPlaceName().toString(),
                        placeItemList.get(position).getPlaceAddress().toString(),
                        buildCoords(x,y).toString(),
                        mapUri
                );

                Intent intent = new Intent(SearchPlaceActivity.this, EditorActivity.class);
                intent.putExtra(MAPINFO_PARCEL, passer);

                setResult(RESULT_OK, intent);
                finish();

            }
        });
    }

    public boolean checkResponseVaild(Response<PlaceRequestResult> response){
        if(response.code() == RETROFIT_FAIL400 ){
            LogController.makeLog(TAG, "CODE 400", localLogPermission);
            Toast.makeText(this, "연결 실패 : 네트워크 상태를 확인하세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if(response.code() == RETROFIT_SUCCESS){
            return true;
        } else {
            Toast.makeText(this, "연결 실패", Toast.LENGTH_SHORT).show();
            LogController.makeLog(TAG, "CODE " + String.valueOf(response.code()), localLogPermission);
            return false;
        }
    }
}