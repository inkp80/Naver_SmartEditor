package com.naver.smarteditor.lesssmarteditor.views.main;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.naver.smarteditor.lesssmarteditor.MyApplication;

import com.naver.smarteditor.lesssmarteditor.R;
import com.naver.smarteditor.lesssmarteditor.adpater.main.MainAdapter;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.EditorComponentRepository;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.utils.EditorContract;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.utils.EditorDbHelper;
import com.naver.smarteditor.lesssmarteditor.views.edit.EditorActivity;
import com.naver.smarteditor.lesssmarteditor.views.main.presenter.MainContract;
import com.naver.smarteditor.lesssmarteditor.views.main.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainContract.View{

    private final String TAG = "MainActivity";
    private boolean localLogPermission = true;


    private final int REQ_CODE_UPDATE = 101;
    private final int REQ_ADD_DOCUMENT = 102;
    private FloatingActionButton mAddDocumentButton;

    private MainPresenter mainPresenter;
    private MainAdapter mainAdapter;

    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init recyclerview
        mainAdapter = new MainAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.main_rv_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mainAdapter);


        //init presenter
        mainPresenter = new MainPresenter();
        mainPresenter.setMainAdapterModel(mainAdapter);
        mainPresenter.setMainAdapterView(mainAdapter);
        mainPresenter.attachView(this);
        mainPresenter.setComponentDataSource(EditorComponentRepository.getInstance(this));


        mainPresenter.requestDocList();


        mAddDocumentButton = (FloatingActionButton) findViewById(R.id.fab);
        mAddDocumentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivityForResult(intent, REQ_ADD_DOCUMENT);
            }
        });

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQ_CODE_UPDATE){
            if(resultCode == RESULT_OK){
                try {
                    //update
                } catch (Exception e){

                }
            }
        }
    }

    @Override
    public void passDataToEditor() {

    }
}
