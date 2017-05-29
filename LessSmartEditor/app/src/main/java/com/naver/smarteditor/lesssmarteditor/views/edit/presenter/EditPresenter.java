package com.naver.smarteditor.lesssmarteditor.views.edit.presenter;

import com.naver.smarteditor.lesssmarteditor.MyApplication;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.EditComponentAdapterContract;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.EditorComponentDataSource;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.EditorComponentRepository;
import com.naver.smarteditor.lesssmarteditor.listener.OnComponentMenuClickListener;
import com.naver.smarteditor.lesssmarteditor.listener.OnTextChangeListener;

import java.util.ArrayList;

import static com.naver.smarteditor.lesssmarteditor.MyApplication.REQ_ADD_DOCUMENT;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.REQ_UPDATE_DOCUMENT;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public class EditPresenter implements EditContract.Presenter, OnTextChangeListener, OnComponentMenuClickListener{
    private final String TAG = "EditPresenter";
    private boolean localLogPermission = true;

    private EditContract.View view;
    private EditComponentAdapterContract.Model adapterModel;
    private EditComponentAdapterContract.View adapterView;

    private EditorComponentRepository editComponentRepository;


    //Presenter 초기화 관련 동작
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
        this.adapterView.setOnComponentClickListener(this);
    }

    @Override
    public void setComponentDataSource(EditorComponentRepository repository) {
        this.editComponentRepository = repository;
    }



    //Component 관련 동작
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
    public void deleteComponent(int _id) {

    }

    @Override
    public void clearComponents() {
        editComponentRepository.clearComponents();
        adapterModel.clearComponent();
        adapterView.notifyAdapter();
    }

    @Override
    public void OnComponentMenuClick(int position) {
        editComponentRepository.deleteComponent(position, new EditorComponentDataSource.LoadComponentCallBack() {
            @Override
            public void OnComponentLoaded(ArrayList<BaseComponent> components) {
                adapterModel.setComponent(components);
                adapterView.notifyAdapter();
            }
        });
    }

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

    //문서 데이터베이스 관련 동작
    @Override
    public void saveDocumentToDataBase(String title) {
        editComponentRepository.saveDocument(title, new EditorComponentDataSource.SaveToDatabaseCallBack() {
            @Override
            public void OnSaveFinished() {
                MyApplication.LogController.makeLog(TAG, "DB request success", localLogPermission);
                editComponentRepository.clearComponents();
                view.finishActivity(REQ_ADD_DOCUMENT);
            }
        });
    }

    @Override
    public void updateDocumentOnDatabase(int doc_id) {
        editComponentRepository.updateDocument(doc_id, new EditorComponentDataSource.UpdateToDatabaseCallBack() {
            @Override
            public void OnUpdateFinished() {
                view.finishActivity(REQ_UPDATE_DOCUMENT);
            }
        });
    }


    //utils
    @Override
    public void loadComponentsFromJson(String jsonComponents) {
        // 메인에서 전달 받은 String json을 Model에 넘겨 컴포넌트를 로드한다.

        editComponentRepository.loadComponents(jsonComponents, new EditorComponentDataSource.LoadComponentCallBack() {
            @Override
            public void OnComponentLoaded(ArrayList<BaseComponent> components) {
                adapterModel.setComponent(components);
                adapterView.notifyAdapter();
            }
        });

    }
}
