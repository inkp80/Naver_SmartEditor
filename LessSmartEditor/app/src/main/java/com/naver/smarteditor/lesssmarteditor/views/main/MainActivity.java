package com.naver.smarteditor.lesssmarteditor.views.main;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.naver.smarteditor.lesssmarteditor.MyApplication;

import com.naver.smarteditor.lesssmarteditor.R;
import com.naver.smarteditor.lesssmarteditor.adpater.main.MainAdapter;
import com.naver.smarteditor.lesssmarteditor.data.DocumentDataParcelable;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.EditorComponentRepository;
import com.naver.smarteditor.lesssmarteditor.views.edit.EditorActivity;
import com.naver.smarteditor.lesssmarteditor.views.main.presenter.MainContract;
import com.naver.smarteditor.lesssmarteditor.views.main.presenter.MainPresenter;

import static com.naver.smarteditor.lesssmarteditor.MyApplication.ADD_MODE;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.DOCUMENT_PARCEL;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.EDIT_MODE;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.MODE_FLAG;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.REQ_ADD_DOCUMENT;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.REQ_CODE_UPDATE;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.REQ_EDIT_DOCUMENT;

public class MainActivity extends AppCompatActivity implements MainContract.View{

    private final String TAG = "MainActivity";
    private boolean localLogPermission = true;


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
                intent.putExtra(MODE_FLAG, ADD_MODE);
                startActivityForResult(intent, REQ_ADD_DOCUMENT);
            }
        });

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mainPresenter.detachView();

    }

    @Override
    protected void onResume(){
        super.onResume();
        mainPresenter.requestDocList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQ_CODE_UPDATE){
            if(resultCode == RESULT_OK){
                try {
                    mainPresenter.requestDocList();
                } catch (Exception e){

                }
            }
        }

        if(requestCode == REQ_EDIT_DOCUMENT){
            if(resultCode == RESULT_OK){
                try {
                    mainPresenter.requestDocList();
                } catch (Exception e) {

                }
            }
        }

        if(requestCode == REQ_ADD_DOCUMENT){
            if(resultCode == RESULT_OK){
                try {
                    MyApplication.LogController.makeLog(TAG, "onresult", localLogPermission);
                    mainPresenter.requestDocList();
                } catch (Exception e) {

                }
            }
        }


    }

    @Override
    public void passDataToEditor(DocumentDataParcelable documentDataParcelable) {
        Intent intent = new Intent(MainActivity.this, EditorActivity.class);
        intent.putExtra(DOCUMENT_PARCEL, documentDataParcelable);
        intent.putExtra(MODE_FLAG, EDIT_MODE);
        startActivityForResult(intent, REQ_EDIT_DOCUMENT);
    }

}
