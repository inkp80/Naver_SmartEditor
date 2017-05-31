package com.naver.smarteditor.lesssmarteditor.data.edit.local;

import android.content.Context;

import com.naver.smarteditor.lesssmarteditor.data.Document;
import com.naver.smarteditor.lesssmarteditor.data.DocumentParcelable;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;

import java.util.List;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public class DocumentRepository implements DocumentDataSource {
    private final String TAG = "DocumentRepository";
    private boolean localLogPermission = true;

    private static DocumentRepository editComponentRepository;

    public static DocumentRepository getInstance(Context context){
        if(editComponentRepository == null){
            editComponentRepository = new DocumentRepository(context);
        }
        return editComponentRepository;
    } //Singleton

    private DocumentLocalDataSource editComponentLocalDataSource;

    private DocumentRepository(Context mContext){
        editComponentLocalDataSource = new DocumentLocalDataSource(mContext);
    }

    @Override
    public void replaceDocumentComponents(List<BaseComponent> components, final LoadComponentCallBack loadComponentCallBack) {
        editComponentLocalDataSource.replaceDocumentComponents(components, new LoadComponentCallBack() {
            @Override
            public void OnComponentLoaded(List<BaseComponent> components) {
                if(loadComponentCallBack != null) {
                    loadComponentCallBack.OnComponentLoaded(components);
                }
            }
        });
    }

    @Override
    public void addComponentToDocument(BaseComponent.TypE type, Object componentData, final LoadComponentCallBack loadComponentCallBack) {
        editComponentLocalDataSource.addComponentToDocument(type, componentData, new LoadComponentCallBack() {
            @Override
            public void OnComponentLoaded(List<BaseComponent> components) {
                if(loadComponentCallBack != null) {
                    loadComponentCallBack.OnComponentLoaded(components);
                }
            }
        });
    }

    @Override
    public void updateEditTextComponent(CharSequence s, int position, final LoadComponentCallBack loadComponentCallBack){
        editComponentLocalDataSource.updateEditTextComponent(s, position, new LoadComponentCallBack() {
            @Override
            public void OnComponentLoaded(List<BaseComponent> components) {
                if(loadComponentCallBack != null) {
                    loadComponentCallBack.OnComponentLoaded(components);
                }
            }
        });
    }

    @Override
    public void clearDocumentComponents() {
        editComponentLocalDataSource.clearDocumentComponents();
    }

    @Override
    public void saveDocumentToDatabase(String title, final SaveToDatabaseCallBack saveToDatabaseCallBack) {
        editComponentLocalDataSource.saveDocumentToDatabase(title, new SaveToDatabaseCallBack() {
            @Override
            public void OnSaveFinished() {
                if(saveToDatabaseCallBack != null) {
                    saveToDatabaseCallBack.OnSaveFinished();
                }
            }
            @Override
            public void OnSaveFailed(){
                if(saveToDatabaseCallBack != null) {
                    saveToDatabaseCallBack.OnSaveFailed();
                }
            }
        });
    }



    @Override
    public void getDocumentsListFromDatabase(final LoadFromDatabaseCallBack loadFromDatabaseCallBack) {
        editComponentLocalDataSource.getDocumentsListFromDatabase(new LoadFromDatabaseCallBack() {
            @Override
            public void OnLoadFinished(List<Document> data) {
                if(loadFromDatabaseCallBack != null){
                    loadFromDatabaseCallBack.OnLoadFinished(data);
                }
            }
        });
    }

    @Override
    public void convertParcelToComponents(DocumentParcelable documentParcelable, final LoadComponentCallBack loadComponentCallBack) {
        editComponentLocalDataSource.convertParcelToComponents(documentParcelable, new LoadComponentCallBack() {
            @Override
            public void OnComponentLoaded(List<BaseComponent> components) {
                if(loadComponentCallBack != null){
                    loadComponentCallBack.OnComponentLoaded(components);
                }
            }
        });
    }

    @Override
    public void deleteDocumentComponent(int position, final LoadComponentCallBack loadComponentCallBack) {
        editComponentLocalDataSource.deleteDocumentComponent(position, new LoadComponentCallBack() {
            @Override
            public void OnComponentLoaded(List<BaseComponent> components) {
                if(loadComponentCallBack != null){
                    loadComponentCallBack.OnComponentLoaded(components);
                }
            }
        });

    }

    @Override
    public void updateDocumentFromDatabase(String title, int doc_id, final UpdateToDatabaseCallBack updateToDatabaseCallBack) {
        editComponentLocalDataSource.updateDocumentFromDatabase(title, doc_id, new UpdateToDatabaseCallBack() {
            @Override
            public void OnUpdateFinished() {
                if(updateToDatabaseCallBack != null){
                    updateToDatabaseCallBack.OnUpdateFinished();
                }
            }
        });

    }


    @Override
    public void swapDocumentComponent(int from, int to, final LoadComponentCallBack loadComponentCallBack) {
        editComponentLocalDataSource.swapDocumentComponent(from, to, new LoadComponentCallBack() {
            @Override
            public void OnComponentLoaded(List<BaseComponent> components) {
                if(loadComponentCallBack != null){
                    loadComponentCallBack.OnComponentLoaded(components);
                }
            }
        });
    }

    @Override
    public void deleteDocumentFromDatabase(int doc_id, final LoadFromDatabaseCallBack loadFromDatabaseCallBack) {
        editComponentLocalDataSource.deleteDocumentFromDatabase(doc_id, new LoadFromDatabaseCallBack() {
            @Override
            public void OnLoadFinished(List<Document> data) {
                if(loadFromDatabaseCallBack != null){
                    loadFromDatabaseCallBack.OnLoadFinished(data);
                }
            }
        });
    }
}
