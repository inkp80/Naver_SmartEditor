package com.naver.smarteditor.lesssmarteditor.views.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.naver.smarteditor.lesssmarteditor.R;
import com.naver.smarteditor.lesssmarteditor.adpater.main.DocumentListAdapter;
import com.naver.smarteditor.lesssmarteditor.data.DocumentDataParcelable;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.EditorComponentRepository;
import com.naver.smarteditor.lesssmarteditor.views.edit.EditorActivity;
import com.naver.smarteditor.lesssmarteditor.views.main.presenter.DocumentListContract;
import com.naver.smarteditor.lesssmarteditor.views.main.presenter.DocumentListPresenter;

import static com.naver.smarteditor.lesssmarteditor.MyApplication.DOCUMENT_PARCEL;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.EDIT_DOCUMENT_MODE;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.EDITOR_MODE;

public class DocumentListActivity extends AppCompatActivity implements DocumentListContract.View{

    private final String TAG = "DocumentListActivity";
    private boolean localLogPermission = true;

    private DocumentListPresenter documentListPresenter;
    private DocumentListAdapter documentListAdapter;

    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init recyclerview
        initRecyclerView();

        //init presenter
        initPresenter();

        documentListPresenter.requestDocList();
    }


    private void initRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        documentListAdapter = new DocumentListAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.main_rv_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        addRecyclerViewBorder(layoutManager);
        recyclerView.setAdapter(documentListAdapter);
    }

    private void initPresenter(){
        documentListPresenter = new DocumentListPresenter();
        documentListPresenter.setMainAdapterModel(documentListAdapter);
        documentListPresenter.setMainAdapterView(documentListAdapter);
        documentListPresenter.attachView(this);
        documentListPresenter.setComponentDataSource(EditorComponentRepository.getInstance(this));
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        documentListPresenter.detachView();

    }

    @Override
    protected void onResume(){
        super.onResume();
        documentListPresenter.requestDocList();
    }


    @Override
    public void passDocumentDataToEditor(DocumentDataParcelable documentDataParcelable) {
        Intent intent = new Intent(DocumentListActivity.this, EditorActivity.class);
        intent.putExtra(DOCUMENT_PARCEL, documentDataParcelable);
        intent.putExtra(EDITOR_MODE, EDIT_DOCUMENT_MODE);
        startActivity(intent);
    }

    private void addRecyclerViewBorder(LinearLayoutManager layoutManager){
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }



}