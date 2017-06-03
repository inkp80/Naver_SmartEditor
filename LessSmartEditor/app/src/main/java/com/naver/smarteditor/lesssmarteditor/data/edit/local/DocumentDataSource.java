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

    interface DatabaseUpdateCallback {
        void OnSuccess();
        void OnFail();
    }

    interface DatabaseReadCallback {
        void OnSuccess(List<Document> document);
        void OnFail();
    }

    interface DatabaseDeleteCallback{
        void OnSuccess(Document document);
        void OnFail();
    }


    interface LocalModel{

        void addComponentToDocument(BaseComponent componentData);

        void initDocumentComponents(List<BaseComponent> components);

        void deleteDocumentComponent(int position);

        void updateDocumentComponent(BaseComponent baseComponent, int position);

        void clearDocumentComponents();

        void convertParcelToComponents(DocumentParcelable documentParcelable);

        void swapDocumentComponent(int from, int to);

        List<BaseComponent> getComponents();
    }


    interface DocumentLocalDatabase {

        void updateDocumentData(List<BaseComponent> documentData, DatabaseUpdateCallback databaseUpdateCallback);

        void deleteDocumentData(int documentId, DatabaseUpdateCallback databaseUpdateCallback);

        void readDocumentData(DatabaseReadCallback databaseReadCallback);
    }

    interface Repository{
        void addComponent(BaseComponent component);
        void updateComponent(BaseComponent baseComponent, int position);
        void deleteComponent(int position);
        void initComponent(List<BaseComponent> components);
        void swapComponent(int fromPosition, int toPosition);
        void clearComponent();


        void updateDocument(DatabaseUpdateCallback databaseUpdateCallback);
        void deleteDocument(DatabaseUpdateCallback databaseUpdateCallback);
        void createDocument(DatabaseUpdateCallback databaseUpdateCallback);
        void readDocuments(DatabaseReadCallback databaseReadCallback);

        void getDocumentById(int documentId, DatabaseReadCallback databaseReadCallback);

    }
}
