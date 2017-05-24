package com.naver.smarteditor.lesssmarteditor.data.edit.local;

import com.naver.smarteditor.lesssmarteditor.data.BaseComponent;

import java.util.ArrayList;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public interface EditorComponentDataSource {
    interface LoadComponentCallBack{
        void OnComponentLoaded(ArrayList<BaseComponent> components);
    }

    interface LoadFromDatabaseCallBack{
        void OnLoadFinished();
    }

    interface SaveToDatabaseCallBack{
        void OnSaveFinished();
    }

    void setComponent(ArrayList<BaseComponent> components, LoadComponentCallBack loadComponentCallBack);

    void getComponent(LoadComponentCallBack loadComponentCallBack);

    void addComponent(BaseComponent.TypE type, Object componentData, LoadComponentCallBack loadComponentCallBack);

    void editComponent(CharSequence s, int position, LoadComponentCallBack loadComponentCallBack);

    void clearComponents();

    void saveDocument(SaveToDatabaseCallBack saveToDatabaseCallBack);
    void loadDocument(LoadFromDatabaseCallBack loadFromDataBaseCallBack);
}
