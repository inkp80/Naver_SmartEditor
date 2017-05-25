package com.naver.smarteditor.lesssmarteditor.views.main.presenter;

import com.naver.smarteditor.lesssmarteditor.adpater.main.MainAdapterContract;
import com.naver.smarteditor.lesssmarteditor.data.DocumentData;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.EditorComponentDataSource;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.EditorComponentRepository;
import com.naver.smarteditor.lesssmarteditor.listener.OnDocumentClickedListener;

import java.util.List;

/**
 * Created by NAVER on 2017. 5. 24..
 */

public class MainPresenter implements MainContract.Presenter, OnDocumentClickedListener {

    private MainContract.View view;
    private MainAdapterContract.Model adapterModel;
    private MainAdapterContract.View adapterView;

    private EditorComponentRepository editComponentRepository;

    public MainPresenter() {

    }


    @Override
    public void OnClick(DocumentData data) {
        //데이터 담아서
        //setResult로
    }


    @Override
    public void requestDocList() {

        //callback & lists
        editComponentRepository.requestDocuments(new EditorComponentDataSource.LoadFromDatabaseCallBack() {
            @Override
            public void OnLoadFinished(List<DocumentData> data) {
                adapterModel.setComponent(data);
                adapterView.notifyAdapter();
            }
        });
    }

    @Override
    public void setMainAdapterModel(MainAdapterContract.Model adapter) {
        this.adapterModel = adapter;
    }

    @Override
    public void setMainAdapterView(MainAdapterContract.View adapter){
        this.adapterView = adapter;
    }

    @Override
    public void setComponentDataSource(EditorComponentRepository repository) {
        this.editComponentRepository = repository;
    }

    @Override
    public void attachView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
