package com.naver.smarteditor.lesssmarteditor.data.edit.local;

import android.content.Context;

import com.naver.smarteditor.lesssmarteditor.data.Document;
import com.naver.smarteditor.lesssmarteditor.data.DocumentParcelable;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;

import java.util.List;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public class DocumentRepository implements DocumentDataSource, DocumentDataSource.Repository{
    private final String TAG = "DocumentRepository";
    private boolean localLogPermission = true;

    private static DocumentRepository mEditComponentRepository;

    private DocumentLocalDataSource mEditComponentLocalDataSource;

    private DocumentModel mDocumentModel;


    ///////////
    private DocumentRepository(Context mContext){
        mEditComponentLocalDataSource = new DocumentLocalDataSource(mContext);
        mDocumentModel = new DocumentModel();
    }

    public static DocumentRepository getInstance(Context context){
        if(mEditComponentRepository == null){
            mEditComponentRepository = new DocumentRepository(context);
        }
        return mEditComponentRepository;
    } //Singleton


    //////////////////

    @Override
    public void updateDocument(final DatabaseCallback databaseCallback) {
        mEditComponentLocalDataSource.updateDocumentFromDatabase(mDocumentModel.returnModel(), new DatabaseCallback() {
            @Override
            public void OnSuccess(List<Document> documents) {
                if(databaseCallback != null){
                    databaseCallback.OnSuccess(null);
                }
            }

            @Override
            public void OnFail() {
                databaseCallback.OnFail();
            }
        });
    }

    @Override
    public void deleteDocument(DatabaseCallback databaseCallback) {

    }

    @Override
    public void createDocument(DatabaseCallback databaseCallback) {

    }

    @Override
    public void readDocument(final DatabaseCallback databaseCallback) {
        mEditComponentLocalDataSource.getDocumentsListFromDatabase(new DatabaseCallback() {
            @Override
            public void OnSuccess(List<Document> documents) {
                if(databaseCallback != null){
                    databaseCallback.OnSuccess(null);
                }
            }

            @Override
            public void OnFail() {
                if(databaseCallback != null){
                    databaseCallback.OnFail();
                }
            }
        });
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public void addComponent(BaseComponent component) {
        mDocumentModel.addComponentToDocument(component);
    }

    @Override
    public void updateComponent(BaseComponent baseComponent, int position) {
        mDocumentModel.updateDocumentComponent(baseComponent, position);
    }

    @Override
    public void deleteComponent(int position) {
        mDocumentModel.deleteDocumentComponent(position);
    }

    @Override
    public void replaceComponent(List<BaseComponent> components) {
        mDocumentModel.replaceDocumentComponents(components);
    }

    @Override
    public void swapComponent(int fromPosition, int toPosition) {
        mDocumentModel.swapDocumentComponent(fromPosition, toPosition);
    }

    @Override
    public void clearComponent() {
        mDocumentModel.clearDocumentComponents();
    }


    public List<BaseComponent> getDocumentData(){
        return mDocumentModel.returnModel();
    }
}
