package com.naver.smarteditor.lesssmarteditor.views.edit.presenter;

import com.naver.smarteditor.lesssmarteditor.adpater.edit.EditComponentAdapterContract;
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
        void addComponentToDocument(BaseComponent.TypE type, Object componentData);
        void deleteComponentFromDocument(int componentIndex);


        //database
        void saveDocumentToDataBase(String title);
        void updateDocumentOnDatabase(String title, int doc_id);


        //utils
        void getComponentsFromJson(String jsonComponents);
    }
}
