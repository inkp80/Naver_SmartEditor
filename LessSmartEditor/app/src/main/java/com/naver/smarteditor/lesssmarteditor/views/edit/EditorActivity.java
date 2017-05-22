package com.naver.smarteditor.lesssmarteditor.views.edit;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.naver.smarteditor.lesssmarteditor.R;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.EditComponentAdapter;
import com.naver.smarteditor.lesssmarteditor.data.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.edit.EditComponentDataSource;
import com.naver.smarteditor.lesssmarteditor.data.edit.EditComponentRepository;
import com.naver.smarteditor.lesssmarteditor.views.edit.presenter.EditContract;
import com.naver.smarteditor.lesssmarteditor.views.edit.presenter.EditPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NAVER on 2017. 5. 11..
 */

public class EditorActivity extends AppCompatActivity implements EditContract.View{


    EditContract.Presenter mPresenter;
    EditComponentAdapter mAdapter;


    @BindView(R.id.editor_bt_addcomponent)
    Button mButton;
    @BindView(R.id.editor_recyclerview)
    RecyclerView mEditorRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_view);
        ButterKnife.bind(this);


        mAdapter = new EditComponentAdapter(this);
        initPresenter();
        initRecyclerView();



        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add text component;
                mPresenter.addComponent(BaseComponent.TypE.TEXT);
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
        mPresenter.setComponentDataSource(EditComponentRepository.getInstance());
    }

}
