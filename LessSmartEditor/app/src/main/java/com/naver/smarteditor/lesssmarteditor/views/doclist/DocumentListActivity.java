package com.naver.smarteditor.lesssmarteditor.views.doclist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.naver.smarteditor.lesssmarteditor.LogController;
import com.naver.smarteditor.lesssmarteditor.R;
import com.naver.smarteditor.lesssmarteditor.adpater.main.DocumentListAdapter;
import com.naver.smarteditor.lesssmarteditor.adpater.main.util.DocumentTouchItemHelperCallback;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.DocumentRepository;
import com.naver.smarteditor.lesssmarteditor.listener.OnDocumentItemClickListener;
import com.naver.smarteditor.lesssmarteditor.views.edit.EditorActivity;
import com.naver.smarteditor.lesssmarteditor.views.doclist.presenter.DocumentListContract;
import com.naver.smarteditor.lesssmarteditor.views.doclist.presenter.DocumentListPresenter;

import static com.naver.smarteditor.lesssmarteditor.MyApplication.DOCUMENT_ID;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.REQ_MOV2_DOCLIST;

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
        documentListAdapter.setOnDocumentItemClickListener(new OnDocumentItemClickListener() {
            @Override
            public void OnItemClick(int documentId) {
                editSelectedDocument(documentId);
            }
        });

        initPresenter();
        initRecyclerView();
        documentListPresenter.requestDocumentsList();
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
        documentListPresenter.requestDocumentsList();
    }


    @Override
    public void editSelectedDocument(int documentId) {
        Intent intent = new Intent(DocumentListActivity.this, EditorActivity.class);
        LogController.makeLog(TAG, "editSelectDocument : "+String.valueOf(documentId), localLogPermission);
        intent.putExtra(DOCUMENT_ID, documentId);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void addRecyclerViewBorder(LinearLayoutManager layoutManager){
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
