package com.naver.smarteditor.lesssmarteditor.data.edit.local;

import com.naver.smarteditor.lesssmarteditor.data.Document;
import com.naver.smarteditor.lesssmarteditor.data.DocumentParcelable;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;

import java.util.List;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public interface DocumentDataSource {

    interface DatabaseUpdateCallback {

        void OnSuccess();
        void OnFail();
    }

    interface DatabaseReadCallback {

        void OnSuccess(List<Document> document);
        void OnFail();
    }

    interface DatabaseDeleteCallback{

        void OnSuccess();
        void OnFail();
    }


    interface LocalModel{

        void addComponentToDocument(BaseComponent componentData);

        void initDocumentComponents(List<BaseComponent> components);

        void deleteDocumentComponent(int position);

        void updateDocumentComponent(BaseComponent baseComponent, int position);

        void clearDocumentComponents();


        void swapDocumentComponent(int from, int to);

        List<BaseComponent> getComponents();
    }


    interface DocumentLocalDatabase {

        void clearDocumentInfo();

        void updateDocumentData(List<BaseComponent> documentData, DatabaseUpdateCallback databaseUpdateCallback);

        void deleteDocumentData(int documentId, DatabaseUpdateCallback databaseUpdateCallback);

        void readDocumentData(DatabaseReadCallback databaseReadCallback);

        void setDocumentInfo(int documentId);
    }

    interface Repository{

        void addComponent(BaseComponent component);

        void updateComponent(BaseComponent baseComponent, int position);

        void deleteComponent(int position);

        void swapComponent(int fromPosition, int toPosition);

        void clearComponent();


        void updateDocument(DatabaseUpdateCallback databaseUpdateCallback);

        void deleteDocument(DatabaseUpdateCallback databaseUpdateCallback);

        void getDocumentLists(DatabaseReadCallback databaseReadCallback);

        void getDocumentById(int documentId, DatabaseReadCallback databaseReadCallback);

    }
}
