package com.naver.smarteditor.lesssmarteditor.views.edit.presenter;

import android.view.View;

import com.naver.smarteditor.lesssmarteditor.MyApplication;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.EditComponentAdapterContract;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.util.ComponentTouchEventListener;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.EditorComponentDataSource;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.EditorComponentRepository;
import com.naver.smarteditor.lesssmarteditor.listener.OnComponentLongClickListener;
import com.naver.smarteditor.lesssmarteditor.listener.OnTextChangeListener;

import java.util.ArrayList;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public class EditPresenter implements EditContract.Presenter, OnTextChangeListener,
        ComponentTouchEventListener, OnComponentLongClickListener {
    private final String TAG = "EditPresenter";
    private boolean localLogPermission = true;

    private EditContract.View view;
    private EditComponentAdapterContract.Model adapterModel;
    private EditComponentAdapterContract.View adapterView;

    private EditorComponentRepository editComponentRepository;


    //Presenter 초기화 관련
    public void attachView(EditContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void setComponentAdatperModel(EditComponentAdapterContract.Model adapter) {
        this.adapterModel = adapter;
    }

    @Override
    public void setComponentAdapterView(EditComponentAdapterContract.View adapter) {
        this.adapterView = adapter;
        this.adapterView.setOnTextChangeListener(this);
        this.adapterView.setOnComponentLongClickListener(this);
    }

    @Override
    public void setComponentDataSource(EditorComponentRepository repository) {
        this.editComponentRepository = repository;
    }


    @Override
    public void addComponentToDocument(final BaseComponent.TypE type, final Object componentData){
        editComponentRepository.addComponentToDocument(type, componentData, new EditorComponentDataSource.LoadComponentCallBack() {
            @Override
            public void OnComponentLoaded(ArrayList<BaseComponent> components) {
                if(components != null) {
                    adapterModel.setComponent(components);
                    adapterView.notifyAdapter();
                    view.scrollToNewComponent(components.size());
                    if(type == BaseComponent.TypE.TEXT){
                        adapterView.setFocus();
                    }
                }
            }
        });
    }

    @Override
    public void deleteComponentFromDocument(int position) {
        editComponentRepository.deleteComponent(position, new EditorComponentDataSource.LoadComponentCallBack() {
            @Override
            public void OnComponentLoaded(ArrayList<BaseComponent> components) {
                adapterModel.setComponent(components);
                adapterView.notifyAdapter();
            }
        });
    }

    @Override
    public void clearCurrentDocument() {
        editComponentRepository.clearComponents();
        adapterModel.clearComponent();
        adapterView.notifyAdapter();
    }

    //item touch helper - Callback
    @Override
    public boolean OnComponentMove(final int from, final int to) {
        editComponentRepository.changeComponentOrder(from, to, new EditorComponentDataSource.LoadComponentCallBack() {
            @Override
            public void OnComponentLoaded(ArrayList<BaseComponent> components) {
                adapterModel.setComponent(components);
                adapterView.swapComponent(from, to);
            }
        });
        return true;
    }




    //TextWatcher - Callback
    @Override
    public void onTextChanged(CharSequence s, int position) {
        editComponentRepository.updateEditTextComponent(s, position, new EditorComponentDataSource.LoadComponentCallBack(){
            @Override
            public void OnComponentLoaded(ArrayList<BaseComponent> components){
                if(components != null) {
                    adapterModel.setComponent(components);
                }
            }
        });

    }


    //데이터베이스 관련
    @Override
    public void saveDocumentToDataBase(String title) {
        editComponentRepository.saveDocumentToDatabase(title, new EditorComponentDataSource.SaveToDatabaseCallBack() {
            @Override
            public void OnSaveFinished() {
                MyApplication.LogController.makeLog(TAG, "DB request success : INSERT", localLogPermission);
            }
        });
    }

    @Override
    public void updateDocumentOnDatabase(String title, int doc_id) {
        editComponentRepository.updateDocumentInDatabase(title, doc_id, new EditorComponentDataSource.UpdateToDatabaseCallBack() {
            @Override
            public void OnUpdateFinished() {
                MyApplication.LogController.makeLog(TAG, "DB request success : UPDATE", localLogPermission);
            }
        });
    }


    //utils
    @Override
    public void getComponentsFromJson(String jsonComponents) {
        editComponentRepository.convertJsonToComponents(jsonComponents, new EditorComponentDataSource.LoadComponentCallBack() {
            @Override
            public void OnComponentLoaded(ArrayList<BaseComponent> components) {
                adapterModel.setComponent(components);
                adapterView.notifyAdapter();
            }
        });

    }


    @Override
    public void OnComponentLongClick(int position, View thisView) {
        view.setFocusForSelectedComponent(position, thisView);
    }
}
