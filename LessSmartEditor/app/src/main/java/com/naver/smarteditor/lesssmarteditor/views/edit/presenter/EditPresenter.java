package com.naver.smarteditor.lesssmarteditor.views.edit.presenter;

import android.view.View;

import com.naver.smarteditor.lesssmarteditor.MyApplication;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.EditComponentAdapterContract;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.util.ComponentTouchEventListener;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.DocumentDataSource;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.DocumentRepository;
import com.naver.smarteditor.lesssmarteditor.listener.OnComponentLongClickListener;
import com.naver.smarteditor.lesssmarteditor.listener.OnEditTextComponentChangeListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public class EditPresenter implements EditContract.Presenter, OnEditTextComponentChangeListener,
        ComponentTouchEventListener, OnComponentLongClickListener {
    private final String TAG = "EditPresenter";
    private boolean localLogPermission = true;

    private EditContract.View view;
    private EditComponentAdapterContract.Model adapterModel;
    private EditComponentAdapterContract.View adapterView;

    private DocumentRepository editComponentRepository;


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
        this.adapterView.setOnEditTextComponentChangeListener(this);
        this.adapterView.setOnComponentLongClickListener(this);
    }

    @Override
    public void setComponentDataSource(DocumentRepository repository) {
        this.editComponentRepository = repository;
    }


    @Override
    public void addComponentToDocument(final BaseComponent.TypE type, final Object componentData){
        editComponentRepository.addComponentToDocument(type, componentData, new DocumentDataSource.LoadComponentCallBack() {
            @Override
            public void OnComponentLoaded(List<BaseComponent> components) {
                if(components != null) {
                    adapterModel.initDocmentComponents(components);
                    adapterView.notifyDataChange();
                    view.scrollToNewComponent(components.size());
                }
            }
        });
    }

    @Override
    public void deleteComponentFromDocument(int position) {
        editComponentRepository.deleteDocumentComponent(position, new DocumentDataSource.LoadComponentCallBack() {
            @Override
            public void OnComponentLoaded(List<BaseComponent> components) {
                adapterModel.initDocmentComponents(components);
                adapterView.notifyDataChange();
            }
        });
    }

    @Override
    public void clearCurrentDocument() {
        editComponentRepository.clearDocumentComponents();
        adapterModel.clearDocumentComponent();
        adapterView.notifyDataChange();
    }

    //item touch helper - Callback
    @Override
    public boolean OnComponentMove(final int from, final int to) {
        editComponentRepository.swapDocumentComponent(from, to, new DocumentDataSource.LoadComponentCallBack() {
            @Override
            public void OnComponentLoaded(List<BaseComponent> components) {
                adapterModel.initDocmentComponents(components);
                adapterView.swapDocumentComponent(from, to);
            }
        });
        return true;
    }




    //TextWatcher - Callback
    @Override
    public void onEditTextComponentTextChange(CharSequence s, int position) {
        editComponentRepository.updateEditTextComponent(s, position, new DocumentDataSource.LoadComponentCallBack(){
            @Override
            public void OnComponentLoaded(List<BaseComponent> components){
                if(components != null) {
                    adapterModel.initDocmentComponents(components);
                }
            }
        });

    }


    //데이터베이스 관련
    @Override
    public void saveDocumentToDataBase(String title) {
        editComponentRepository.saveDocumentToDatabase(title, new DocumentDataSource.SaveToDatabaseCallBack() {
            @Override
            public void OnSaveFinished() {
                MyApplication.LogController.makeLog(TAG, "DB request success : INSERT", localLogPermission);
                view.showToast(Calendar.getInstance().getTime() + " 저장되었습니다.");
            }

            @Override
            public void OnSaveFailed() {
                MyApplication.LogController.makeLog(TAG, "DB request failed : empty document", localLogPermission);
                adapterView.notifyDataChange();
                view.showToast("저장 실패 : 빈 문서입니다.");
            }
        });
    }

    @Override
    public void updateDocumentOnDatabase(String title, int doc_id) {
        editComponentRepository.updateDocumentFromDatabase(title, doc_id, new DocumentDataSource.UpdateToDatabaseCallBack() {
            @Override
            public void OnUpdateFinished() {
                MyApplication.LogController.makeLog(TAG, "DB request success : UPDATE", localLogPermission);
                view.showToast(Calendar.getInstance().getTime() + " 저장되었습니다.");
            }
        });
    }


    //utils
    @Override
    public void getComponentsFromJson(String jsonComponents) {
        editComponentRepository.convertJsonToComponents(jsonComponents, new DocumentDataSource.LoadComponentCallBack() {
            @Override
            public void OnComponentLoaded(List<BaseComponent> components) {
                adapterModel.initDocmentComponents(components);
                adapterView.notifyDataChange();
            }
        });

    }


    @Override
    public void OnComponentLongClick(int position, View thisView) {
        view.setFocusForSelectedComponent(position, thisView);
    }
}
