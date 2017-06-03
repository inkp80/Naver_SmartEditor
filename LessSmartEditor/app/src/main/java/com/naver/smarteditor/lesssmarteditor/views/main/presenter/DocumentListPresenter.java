package com.naver.smarteditor.lesssmarteditor.views.main.presenter;

import com.naver.smarteditor.lesssmarteditor.adpater.main.DocumentListAdapterContract;
import com.naver.smarteditor.lesssmarteditor.adpater.main.util.DocumentTouchEventListener;
import com.naver.smarteditor.lesssmarteditor.data.Document;
import com.naver.smarteditor.lesssmarteditor.data.DocumentParcelable;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.DocumentDataSource;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.DocumentRepository;
import com.naver.smarteditor.lesssmarteditor.listener.OnDocumentClickListener;

import java.util.List;

/**
 * Created by NAVER on 2017. 5. 24..
 */

public class DocumentListPresenter implements DocumentListContract.Presenter, OnDocumentClickListener, DocumentTouchEventListener {

    private DocumentListContract.View view;
    private DocumentListAdapterContract.Model adapterModel;
    private DocumentListAdapterContract.View adapterView;

    private DocumentRepository editComponentRepository;

    public DocumentListPresenter() {

    }


    @Override
    public void OnDocumentClick(DocumentParcelable documentParcelable) {
        view.editSelectedDocument(documentParcelable);
    }


    @Override
    public void requestDocumentsList() {
        editComponentRepository.getDocumentData();

    }
//        editComponentRepository.getDocumentsList(new DocumentDataSource.LoadFromDatabaseCallBack() {
//            @Override
//            public void OnLoadFinished(List<Document> data) {
//                adapterModel.initDocumentList(data);
//                adapterView.notifyDataChange();
//            }
//
//        });

    @Override
    public void setMainAdapterModel(DocumentListAdapterContract.Model adapter) {
        this.adapterModel = adapter;
    }

    @Override
    public void setMainAdapterView(DocumentListAdapterContract.View adapter) {
        this.adapterView = adapter;
        adapterView.setDocumentOnClickedListener(this);
    }

    @Override
    public void setComponentDataSource(DocumentRepository repository) {
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


    @Override
    public void OnItemDismiss(Document document) {
//        editComponentRepository.deleteDocument(document.get_id(), new DocumentDataSource.LoadFromDatabaseCallBack() {
//            @Override
//            public void OnLoadFinished(List<Document> data) {
//                    adapterModel.initDocumentList(data);
//                    adapterView.notifyDataChange();
//            }
//        });

    }
}
