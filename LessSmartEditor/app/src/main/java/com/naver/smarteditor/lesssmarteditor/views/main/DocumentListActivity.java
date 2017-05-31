package com.naver.smarteditor.lesssmarteditor.views.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.naver.smarteditor.lesssmarteditor.R;
import com.naver.smarteditor.lesssmarteditor.adpater.main.DocumentListAdapter;
import com.naver.smarteditor.lesssmarteditor.adpater.main.util.DocumentTouchItemHelperCallback;
import com.naver.smarteditor.lesssmarteditor.data.DocumentParcelable;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.DocumentRepository;
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

        documentListAdapter = new DocumentListAdapter(this);
        //init presenter
        initPresenter();
        //init recyclerview
        initRecyclerView();


        documentListPresenter.requestDocumentsFromLocal();
    }


    private void initRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView = (RecyclerView) findViewById(R.id.main_rv_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        addRecyclerViewBorder(layoutManager);
        recyclerView.setAdapter(documentListAdapter);


        ItemTouchHelper.Callback callback = new DocumentTouchItemHelperCallback(documentListAdapter, documentListPresenter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    private void initPresenter(){
        documentListPresenter = new DocumentListPresenter();
        documentListPresenter.setMainAdapterModel(documentListAdapter);
        documentListPresenter.setMainAdapterView(documentListAdapter);
        documentListPresenter.attachView(this);
        documentListPresenter.setComponentDataSource(DocumentRepository.getInstance(this));
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        documentListPresenter.detachView();

    }

    @Override
    protected void onResume(){
        super.onResume();
        documentListPresenter.requestDocumentsFromLocal();
    }


    @Override
    public void editSelectedDocument(DocumentParcelable documentParcelable) {
        Intent intent = new Intent(DocumentListActivity.this, EditorActivity.class);
        intent.putExtra(DOCUMENT_PARCEL, documentParcelable);
        intent.putExtra(EDITOR_MODE, EDIT_DOCUMENT_MODE);
        startActivity(intent);
    }

    private void addRecyclerViewBorder(LinearLayoutManager layoutManager){
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }



}
