package com.naver.smarteditor.lesssmarteditor.views.edit.presenter;

import com.naver.smarteditor.lesssmarteditor.MyApplication;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.EditComponentAdapter;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.EditComponentAdapterContract;
import com.naver.smarteditor.lesssmarteditor.data.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.TextComponent;
import com.naver.smarteditor.lesssmarteditor.data.edit.EditComponentDataSource;
import com.naver.smarteditor.lesssmarteditor.data.edit.EditComponentRepository;
import com.naver.smarteditor.lesssmarteditor.listener.OnTextChangeListener;
import com.naver.smarteditor.lesssmarteditor.views.basic.presenter.BasePresenter;

import java.util.ArrayList;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public class EditPresenter implements EditContract.Presenter, OnTextChangeListener{
    private final String TAG = "EditPresenter";
    private boolean localLogPermission = true;

    private EditContract.View view;
    private EditComponentAdapterContract.Model adapterModel;
    private EditComponentAdapterContract.View adapterView;

    private EditComponentRepository editComponentRepository;


    public void attachView(EditContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void loadComponent() {

    }

    @Override
    public void addComponent(BaseComponent.TypE type){
        editComponentRepository.addComponent(type, new EditComponentDataSource.LoadComponentCallBack() {
            @Override
            public void OnComponentLoaded(ArrayList<BaseComponent> components) {
                if(components != null) {
//                    adapterModel.clearComponent();
                    adapterModel.setComponent(components);
                    adapterView.notifyAdapter();
                }
            }
        });
    }

    @Override
    public void setComponentAdatperModel(EditComponentAdapterContract.Model adapter) {
        this.adapterModel = adapter;
    }

    @Override
    public void setComponentAdapterView(EditComponentAdapterContract.View adapter) {
        this.adapterView = adapter;
        this.adapterView.setOnTextChangeListener(this);
    }

    @Override
    public void setComponentDataSource(EditComponentRepository repository) {
        this.editComponentRepository = repository;
    }


    @Override
    public void onTextChanged(CharSequence s, int position) {
        MyApplication.LogController.makeLog(TAG,"onTextChanged : " + String.valueOf(position),localLogPermission);
        editComponentRepository.editComponent(s, position, new EditComponentDataSource.LoadComponentCallBack(){
            @Override
            public void OnComponentLoaded(ArrayList<BaseComponent> components){
                if(components != null) {
                    adapterModel.setComponent(components);
                }
            }
        });

    }


}
