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
    void addComponentToDocument(BaseComponent.Type type, Object componentData, LoadComponentCallBack loadComponentCallBack);

    void replaceDocumentComponents(List<BaseComponent> components, LoadComponentCallBack loadComponentCallBack);

    void deleteDocumentComponent(int position, LoadComponentCallBack loadComponentCallBack);

    void updateEditTextComponent(CharSequence s, int position, LoadComponentCallBack loadComponentCallBack);

    void clearDocumentComponents();

    void convertParcelToComponents(DocumentParcelable documentParcelable, LoadComponentCallBack loadComponentCallBack);

    void swapDocumentComponent(int from, int to, LoadComponentCallBack loadComponentCallBack);

    interface LocalModel{

        void addComponentToDocument(BaseComponent componentData);

        void replaceDocumentComponents(List<BaseComponent> components);

        void deleteDocumentComponent(int position);

        void updateDocumentComponent(BaseComponent baseComponent, int position);

        void clearDocumentComponents();

        void convertParcelToComponents(DocumentParcelable documentParcelable);

        void swapDocumentComponent(int from, int to);

        List<BaseComponent> returnModel();
    }



    //database
    void saveDocumentToDatabase(String title, SaveToDatabaseCallBack saveToDatabaseCallBack);

    void updateDocumentFromDatabase(String title, int doc_id, UpdateToDatabaseCallBack updateToDatabaseCallBack);

    void getDocumentsListFromDatabase(LoadFromDatabaseCallBack loadFromDatabaseCallBack);

    void deleteDocumentFromDatabase(int doc_id, LoadFromDatabaseCallBack loadFromDatabaseCallBack);

    interface DocumentLocalDatabase {

        void saveDocumentToDatabase(String title, SaveToDatabaseCallBack saveToDatabaseCallBack);

        void updateDocumentFromDatabase(String title, int doc_id, UpdateToDatabaseCallBack updateToDatabaseCallBack);

        void getDocumentsListFromDatabase(LoadFromDatabaseCallBack loadFromDatabaseCallBack);

        void deleteDocumentFromDatabase(int doc_id, LoadFromDatabaseCallBack loadFromDatabaseCallBack);
    }

    interface Repository{
        void addComponent(BaseComponent component);
        void updateComponent(BaseComponent baseComponent, int position);
        void deleteComponent(int position);
        void replaceComponent(List<BaseComponent> components);
        void swapComponent(int fromPosition, int toPosition);

        void requestUpdateDocument();
        void requestDeleteDocument();
        void requestCreateDocument();
        void requestReadDocument();

    }
}
