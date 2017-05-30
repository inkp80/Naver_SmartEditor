package com.naver.smarteditor.lesssmarteditor.views.edit.presenter;

import android.view.View;

import com.naver.smarteditor.lesssmarteditor.adpater.edit.EditComponentAdapterContract;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.EditorComponentRepository;
import com.naver.smarteditor.lesssmarteditor.views.basic.BaseView;
import com.naver.smarteditor.lesssmarteditor.views.basic.presenter.BasePresenter;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public interface EditContract {

    interface View extends BaseView{
        void waitForDbProcessing();
        void setMenuForSelectedComponent(int position, android.view.View selectedComponent);
        void scrollToNewComponent(int position);
    }

    interface Presenter extends BasePresenter{

        //init & default setup Presenter
        void attachView(View view);
        void detachView();
        void clearComponents();

        void setComponentAdatperModel(EditComponentAdapterContract.Model adapter);
        void setComponentAdapterView(EditComponentAdapterContract.View adapter);
        void setComponentDataSource(EditorComponentRepository repository);


        //components
        void addComponent(BaseComponent.TypE type, Object componentData);
        void deleteComponent(int doc_id);


        //database
        void saveDocumentToDataBase(String title);
        void updateDocumentOnDatabase(String title, int doc_id);


        //utils
        void getComponentsFromJson(String jsonComponents);
    }
}
