package com.naver.smarteditor.lesssmarteditor.views.main.presenter;

import com.naver.smarteditor.lesssmarteditor.adpater.main.DocumentListAdapterContract;
import com.naver.smarteditor.lesssmarteditor.data.DocumentData;
import com.naver.smarteditor.lesssmarteditor.data.DocumentDataParcelable;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.EditorComponentDataSource;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.EditorComponentRepository;
import com.naver.smarteditor.lesssmarteditor.listener.OnDocumentClickListener;

import java.util.List;

/**
 * Created by NAVER on 2017. 5. 24..
 */

public class DocumentListPresenter implements DocumentListContract.Presenter, OnDocumentClickListener {

    private DocumentListContract.View view;
    private DocumentListAdapterContract.Model adapterModel;
    private DocumentListAdapterContract.View adapterView;

    private EditorComponentRepository editComponentRepository;

    public DocumentListPresenter() {

    }


    @Override
    public void OnClick(DocumentDataParcelable documentDataParcelable) {
        view.passDocumentDataToEditor(documentDataParcelable);
    }


    @Override
    public void requestDocList() {

        editComponentRepository.getDocumentsListFromDatabase(new EditorComponentDataSource.LoadFromDatabaseCallBack() {
            @Override
            public void OnLoadFinished(List<DocumentData> data) {
                adapterModel.setComponent(data);
                adapterView.notifyAdapter();
            }

        });
    }

    @Override
    public void setMainAdapterModel(DocumentListAdapterContract.Model adapter) {
        this.adapterModel = adapter;
    }

    @Override
    public void setMainAdapterView(DocumentListAdapterContract.View adapter){
        this.adapterView = adapter;
        adapterView.setDocumentOnClickedListener(this);
    }

    @Override
    public void setComponentDataSource(EditorComponentRepository repository) {
        this.editComponentRepository = repository;
    }


    @Override
    public void attachView(DocumentListContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
