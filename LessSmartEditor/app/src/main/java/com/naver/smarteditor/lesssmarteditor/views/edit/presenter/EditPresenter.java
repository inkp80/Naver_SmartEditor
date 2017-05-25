package com.naver.smarteditor.lesssmarteditor.views.edit.presenter;

import com.naver.smarteditor.lesssmarteditor.MyApplication;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.EditComponentAdapterContract;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.EditorComponentDataSource;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.EditorComponentRepository;
import com.naver.smarteditor.lesssmarteditor.listener.OnTextChangeListener;

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

    private EditorComponentRepository editComponentRepository;

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
    public void setComponentAdatperModel(EditComponentAdapterContract.Model adapter) {
        this.adapterModel = adapter;
    }

    @Override
    public void setComponentAdapterView(EditComponentAdapterContract.View adapter) {
        this.adapterView = adapter;
        this.adapterView.setOnTextChangeListener(this);
    }

    @Override
    public void setComponentDataSource(EditorComponentRepository repository) {
        this.editComponentRepository = repository;
    }

    @Override
    public void addComponent(BaseComponent.TypE type, Object componentData){
        editComponentRepository.addComponent(type, componentData, new EditorComponentDataSource.LoadComponentCallBack() {
            @Override
            public void OnComponentLoaded(ArrayList<BaseComponent> components) {
                if(components != null) {
                    adapterModel.setComponent(components);
                    adapterView.notifyAdapter();
                }
            }
        });
    }

    @Override
    public void onTextChanged(CharSequence s, int position) {
        editComponentRepository.editComponent(s, position, new EditorComponentDataSource.LoadComponentCallBack(){
            @Override
            public void OnComponentLoaded(ArrayList<BaseComponent> components){
                if(components != null) {
                    adapterModel.setComponent(components);
                }
            }
        });

    }


    @Override
    public void saveDocumentToDataBase(String title) {
        editComponentRepository.saveDocument(title, new EditorComponentDataSource.SaveToDatabaseCallBack() {
            @Override
            public void OnSaveFinished() {
                MyApplication.LogController.makeLog(TAG, "DB request success", localLogPermission);
                editComponentRepository.clearComponents();
//                view.finishActivity();
            }
        });
    }

    @Override
    public void loadDocumentFromDataBase(int _id) {
//        editComponentRepository.loadDocument(_id, new EditorComponentDataSource.LoadFromDatabaseCallBack() {
//            @Override
//            public void OnLoadFinished() {
//                MyApplication.LogController.makeLog(TAG, "load from database success", localLogPermission);
//            }
//        });
    }
}
