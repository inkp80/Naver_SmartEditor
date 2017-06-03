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

    interface DatabaseCallback{
        void OnSuccess(List<Document> documents);
        void OnFail();
    }



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


    interface DocumentLocalDatabase {

        void saveDocument(List<BaseComponent> documentData, DatabaseCallback databaseCallback);

        void updateDocument(List<BaseComponent> documentData, DatabaseCallback databaseCallback);

        void getDocumentsList(DatabaseCallback databaseCallback);

        void deleteDocument(int documentId, DatabaseCallback databaseCallback);
    }

    interface Repository{
        void addComponent(BaseComponent component);
        void updateComponent(BaseComponent baseComponent, int position);
        void deleteComponent(int position);
        void replaceComponent(List<BaseComponent> components);
        void swapComponent(int fromPosition, int toPosition);
        void clearComponent();


        void updateDocument(DatabaseCallback databaseCallback);
        void deleteDocument(DatabaseCallback databaseCallback);
        void createDocument(DatabaseCallback databaseCallback);
        void readDocument(DatabaseCallback databaseCallback);

    }
}
