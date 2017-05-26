package com.naver.smarteditor.lesssmarteditor.data.edit.local;

import com.naver.smarteditor.lesssmarteditor.data.DocumentData;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public interface EditorComponentDataSource {

    interface LoadComponentCallBack{
        void OnComponentLoaded(ArrayList<BaseComponent> components);
    }

    interface LoadFromDatabaseCallBack{
        void OnLoadFinished(List<DocumentData> data);
    }

    interface SaveToDatabaseCallBack{
        void OnSaveFinished();
    }

    interface UpdateToDatabaseCallBack{
        void OnUpdateFinished();
    }


    //component
    void addComponent(BaseComponent.TypE type, Object componentData, LoadComponentCallBack loadComponentCallBack);

    void setComponents(ArrayList<BaseComponent> components, LoadComponentCallBack loadComponentCallBack);

    void deleteComponent(int position);

    void updateEditTextComponent(CharSequence s, int position, LoadComponentCallBack loadComponentCallBack);

    void clearComponents();

    void loadComponents(String jsonComponents, LoadComponentCallBack loadComponentCallBack);



    //database
    void saveDocument(String title, SaveToDatabaseCallBack saveToDatabaseCallBack);

    void updateDocument(int doc_id, UpdateToDatabaseCallBack updateToDatabaseCallBack);

    void getDocumentsList(LoadFromDatabaseCallBack loadFromDatabaseCallBack);
}
