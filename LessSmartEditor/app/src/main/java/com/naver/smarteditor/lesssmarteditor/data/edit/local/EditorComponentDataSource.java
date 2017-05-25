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

    void setComponent(ArrayList<BaseComponent> components, LoadComponentCallBack loadComponentCallBack);

    void getComponent(LoadComponentCallBack loadComponentCallBack);

    void addComponent(BaseComponent.TypE type, Object componentData, LoadComponentCallBack loadComponentCallBack);

    void editComponent(CharSequence s, int position, LoadComponentCallBack loadComponentCallBack);

    void clearComponents();

    void saveDocument(String title, SaveToDatabaseCallBack saveToDatabaseCallBack);
    void loadDocument(int _id,LoadFromDatabaseCallBack loadFromDataBaseCallBack);

    void requestDocuments(LoadFromDatabaseCallBack loadFromDatabaseCallBack);
}
