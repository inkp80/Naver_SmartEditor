package com.naver.smarteditor.lesssmarteditor.views.edit;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.naver.smarteditor.lesssmarteditor.MyApplication;
import com.naver.smarteditor.lesssmarteditor.R;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.EditComponentAdapter;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.api.naver_map.PlaceItemParcelable;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.EditorComponentRepository;
import com.naver.smarteditor.lesssmarteditor.dialog.SelectComponentDialog;
import com.naver.smarteditor.lesssmarteditor.views.edit.presenter.EditContract;
import com.naver.smarteditor.lesssmarteditor.views.edit.presenter.EditPresenter;
import com.naver.smarteditor.lesssmarteditor.views.map.SearchPlaceActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NAVER on 2017. 5. 11..
 */

public class EditorActivity extends AppCompatActivity implements EditContract.View{
    private final String TAG = "EditorActivity";
    private boolean localLogPermission = true;

    private final int REQ_CODE_MOV2_GALLERY = 100;
    private final int REQ_CODE_MOV2_SEARCH_PLACE = 101;


    EditContract.Presenter mPresenter;
    EditComponentAdapter mAdapter;

    @BindView(R.id.editor_et_title)
    EditText mTitle;
    @BindView(R.id.editor_bt_save)
    Button mSaveButton;
    @BindView(R.id.editor_bt_addcomponent)
    Button mButton;
    @BindView(R.id.editor_recyclerview)
    RecyclerView mEditorRecyclerView;

    private SelectComponentDialog mSelectComponentDialog;


    View.OnClickListener txtButtonListener;
    View.OnClickListener imgButtonListener;
    View.OnClickListener mapButtonListener;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_view);
        ButterKnife.bind(this);


        mAdapter = new EditComponentAdapter(this);
        initPresenter();
        initRecyclerView();
        initDialog();


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectComponentDialog.show();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.saveDocumentToDataBase(mTitle.getText().toString());
                mPresenter.loadDocumentFromDataBase(0);
                //메인 액티비티로 재전송
                //이때 화면에 pregress-bar를 보인다.
                //main activity로
            }
        });

    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        mPresenter.detachView();
    }

    private void initRecyclerView(){
        mEditorRecyclerView = (RecyclerView) findViewById(R.id.editor_recyclerview);
        mEditorRecyclerView.setHasFixedSize(true);
        mEditorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mEditorRecyclerView.setAdapter(mAdapter);
        mEditorRecyclerView.setItemViewCacheSize(100);
    }

    private void initPresenter(){
        mPresenter = new EditPresenter();
        mPresenter.attachView(this);
        mPresenter.setComponentAdapterView(mAdapter);
        mPresenter.setComponentAdatperModel(mAdapter);
        mPresenter.setComponentDataSource(EditorComponentRepository.getInstance(this));
    }

    private void initDialog(){
        //TODO: rename & restructuring

        txtButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ADD TEXT COMPONENT TO EDITOR
                mPresenter.addComponent(BaseComponent.TypE.TEXT, null);
                mSelectComponentDialog.dismiss();
            }
        };

        imgButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImgSrcFromGallery();
                mSelectComponentDialog.dismiss();
            }
        };

        mapButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMapSrcFromNaverPlaceAPI();
                mSelectComponentDialog.dismiss();
            }
        };

        mSelectComponentDialog = new SelectComponentDialog(this, txtButtonListener, imgButtonListener, mapButtonListener);
    }


    public void getImgSrcFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ_CODE_MOV2_GALLERY);
    }

    public void getMapSrcFromNaverPlaceAPI(){
        Intent intent = new Intent(this, SearchPlaceActivity.class);
        startActivityForResult(intent, REQ_CODE_MOV2_SEARCH_PLACE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //TODO: handling data
        if(requestCode == REQ_CODE_MOV2_GALLERY) {
            if(resultCode == Activity.RESULT_OK) {
                try {
                    Uri selectedImgUri = data.getData();
                    mPresenter.addComponent(BaseComponent.TypE.IMG, selectedImgUri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if(requestCode == REQ_CODE_MOV2_SEARCH_PLACE){
            if(resultCode == RESULT_OK){
                try{
                    //TODO: parcel should declared with final
                    Bundle bundle = data.getExtras();
                    PlaceItemParcelable passer = bundle.getParcelable("parcel");
                    mPresenter.addComponent(BaseComponent.TypE.MAP, passer);

                } catch (Exception e) {
                    MyApplication.LogController.makeLog(TAG, "ERROR", localLogPermission);
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void waitForDbProcessing() {
        //TODO : list on wait
        //setProgress Bar - Waiting
        //save & add comp bt inactivate
    }

    @Override
    public void finishActivity() {
        finish();
    }
}
