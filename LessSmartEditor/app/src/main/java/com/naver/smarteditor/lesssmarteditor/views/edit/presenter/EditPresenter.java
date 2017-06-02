package com.naver.smarteditor.lesssmarteditor.views.edit.presenter;

import android.view.View;

import com.naver.smarteditor.lesssmarteditor.LogController;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.EditComponentAdapterContract;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.util.ComponentTouchEventListener;
import com.naver.smarteditor.lesssmarteditor.data.Document;
import com.naver.smarteditor.lesssmarteditor.data.DocumentParcelable;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.DocumentDataSource;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.DocumentRepository;
import com.naver.smarteditor.lesssmarteditor.listener.OnComponentLongClickListener;
import com.naver.smarteditor.lesssmarteditor.listener.OnEditTextComponentChangeListener;

import java.util.Calendar;
import java.util.List;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public class EditPresenter implements EditContract.Presenter{
    private final String TAG = "EditPresenter";
    private boolean localLogPermission = true;

    private EditContract.View view;
    private EditComponentAdapterContract.Model adapterModel;
    private EditComponentAdapterContract.View adapterView;

    private DocumentRepository editComponentRepository;




    private OnEditTextComponentChangeListener onEditTextComponentChangeListener;


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
    }

    @Override
    public void setComponentDataSource(DocumentRepository repository) {
        this.editComponentRepository = repository;
    }

    ////////////////////


    //done
    @Override
    public void addComponentToDocument(BaseComponent component){
        editComponentRepository.addComponent(component);
        adapterModel.initDocmentComponents(editComponentRepository.getDocumentData());
        adapterView.notifyDataChange();
    }


    //done
    @Override
    public void updateComponentInDocument(BaseComponent baseComponent, int position) {
        editComponentRepository.updateComponent(baseComponent, position);
        adapterModel.initDocmentComponents(editComponentRepository.getDocumentData());
    }

    @Override
    public void deleteComponentFromDocument(int position) {
        editComponentRepository.deleteComponent(position);
        adapterModel.initDocmentComponents(editComponentRepository.getDocumentData());
        adapterView.notifyDataChange();
    }


    //done
    @Override
    public void clearCurrentDocument() {
        editComponentRepository.clearComponent();
        adapterModel.clearDocumentComponent();
        adapterView.notifyDataChange();
    }

    //done
    @Override
    public void swapComponent(int fromPosition, int toPosition) {
        editComponentRepository.swapComponent(fromPosition, toPosition);
        adapterModel.initDocmentComponents(editComponentRepository.getDocumentData());
        adapterView.swapDocumentComponent(fromPosition, toPosition);
    }


    @Override
    public void updateDocument() {
        editComponentRepository.updateDocument(new DocumentDataSource.DatabaseCallback() {
            @Override
            public void OnSuccess(List<Document> documents) {
                view.showToast("update Sucess");
            }

            @Override
            public void OnFail() {
                view.showToast("update fail");
            }
        });
    }
}
