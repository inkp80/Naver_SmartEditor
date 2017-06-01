package com.naver.smarteditor.lesssmarteditor.views.edit.presenter;

import com.naver.smarteditor.lesssmarteditor.adpater.edit.EditComponentAdapterContract;
import com.naver.smarteditor.lesssmarteditor.data.DocumentParcelable;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.DocumentRepository;
import com.naver.smarteditor.lesssmarteditor.views.basic.BaseView;
import com.naver.smarteditor.lesssmarteditor.views.basic.presenter.BasePresenter;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public interface EditContract {

    interface View extends BaseView{
        void showProgressBar();
        void showToast(String message);
        void setFocusForSelectedComponent(int componentIndex, android.view.View selectedComponent);
        void scrollToNewComponent(int componentIndex);
    }

    interface Presenter extends BasePresenter{

        //init & default setup Presenter
        void attachView(View view);
        void detachView();
        void clearCurrentDocument();

        void setComponentAdatperModel(EditComponentAdapterContract.Model adapter);
        void setComponentAdapterView(EditComponentAdapterContract.View adapter);
        void setComponentDataSource(DocumentRepository repository);


        //components
        void addComponentToDocument(BaseComponent componentData);
        void updateComponentInDocument(BaseComponent baseComponent, int position);
        void deleteComponentFromDocument(int position);
        void swapComponent(int fromPostition, int toPosition);


        //database
        void saveDocumentToDataBase(String title);


        //utils
        void convertParcelToDocumentComponents(DocumentParcelable documentParcelable);
    }
}
