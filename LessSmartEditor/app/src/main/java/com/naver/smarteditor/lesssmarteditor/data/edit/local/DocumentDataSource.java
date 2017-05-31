package com.naver.smarteditor.lesssmarteditor.data.edit.local;

import com.naver.smarteditor.lesssmarteditor.data.Document;
import com.naver.smarteditor.lesssmarteditor.data.DocumentParcelable;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;

import java.util.List;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public interface DocumentDataSource {

    interface LoadComponentCallBack{
        void OnComponentLoaded(List<BaseComponent> components);
    }

    interface LoadFromDatabaseCallBack{
        void OnLoadFinished(List<Document> data);
    }

    interface SaveToDatabaseCallBack{
        void OnSaveFinished();
        void OnSaveFailed();
    }

    interface UpdateToDatabaseCallBack{
        void OnUpdateFinished();
    }


    //component
    void addComponentToDocument(BaseComponent.TypE type, Object componentData, LoadComponentCallBack loadComponentCallBack);

    void replaceDocumentComponents(List<BaseComponent> components, LoadComponentCallBack loadComponentCallBack);

    void deleteDocumentComponent(int position, LoadComponentCallBack loadComponentCallBack);

    void updateEditTextComponent(CharSequence s, int position, LoadComponentCallBack loadComponentCallBack);

    void clearDocumentComponents();

    void convertParcelToComponents(DocumentParcelable documentParcelable, LoadComponentCallBack loadComponentCallBack);

    void swapDocumentComponent(int from, int to, LoadComponentCallBack loadComponentCallBack);



    //database
    void saveDocumentToDatabase(String title, SaveToDatabaseCallBack saveToDatabaseCallBack);

    void updateDocumentFromDatabase(String title, int doc_id, UpdateToDatabaseCallBack updateToDatabaseCallBack);

    void getDocumentsListFromDatabase(LoadFromDatabaseCallBack loadFromDatabaseCallBack);

    void deleteDocumentFromDatabase(int doc_id, LoadFromDatabaseCallBack loadFromDatabaseCallBack);
}
