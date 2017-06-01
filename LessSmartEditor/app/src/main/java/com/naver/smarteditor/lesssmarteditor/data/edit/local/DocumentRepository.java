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

    public static DocumentRepository getInstance(Context context){
        if(mEditComponentRepository == null){
            mEditComponentRepository = new DocumentRepository(context);
        }
        return mEditComponentRepository;
    } //Singleton

    private DocumentLocalDataSource mEditComponentLocalDataSource;
    private DocumentModel mDocumentModel;

    private DocumentRepository(Context mContext){
        mEditComponentLocalDataSource = new DocumentLocalDataSource(mContext);
        mDocumentModel = new DocumentModel();
    }

    @Override
    public void replaceDocumentComponents(List<BaseComponent> components, final LoadComponentCallBack loadComponentCallBack) {
        mEditComponentLocalDataSource.replaceDocumentComponents(components, new LoadComponentCallBack() {
            @Override
            public void OnComponentLoaded(List<BaseComponent> components) {
                if(loadComponentCallBack != null) {
                    loadComponentCallBack.OnComponentLoaded(components);
                }
            }
        });
    }

    @Override
    public void addComponentToDocument(BaseComponent.Type type, Object componentData, final LoadComponentCallBack loadComponentCallBack) {
        mEditComponentLocalDataSource.addComponentToDocument(type, componentData, new LoadComponentCallBack() {
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
        mEditComponentLocalDataSource.updateEditTextComponent(s, position, new LoadComponentCallBack() {
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
        mEditComponentLocalDataSource.clearDocumentComponents();
    }

    @Override
    public void saveDocumentToDatabase(String title, final SaveToDatabaseCallBack saveToDatabaseCallBack) {
        mEditComponentLocalDataSource.saveDocumentToDatabase(title, new SaveToDatabaseCallBack() {
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
        mEditComponentLocalDataSource.getDocumentsListFromDatabase(new LoadFromDatabaseCallBack() {
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
        mEditComponentLocalDataSource.convertParcelToComponents(documentParcelable, new LoadComponentCallBack() {
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
        mEditComponentLocalDataSource.deleteDocumentComponent(position, new LoadComponentCallBack() {
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
        mEditComponentLocalDataSource.updateDocumentFromDatabase(title, doc_id, new UpdateToDatabaseCallBack() {
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
        mEditComponentLocalDataSource.swapDocumentComponent(from, to, new LoadComponentCallBack() {
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
        mEditComponentLocalDataSource.deleteDocumentFromDatabase(doc_id, new LoadFromDatabaseCallBack() {
            @Override
            public void OnLoadFinished(List<Document> data) {
                if(loadFromDatabaseCallBack != null){
                    loadFromDatabaseCallBack.OnLoadFinished(data);
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
    public void requestUpdateDocument() {

    }

    @Override
    public void requestDeleteDocument() {

    }

    @Override
    public void requestCreateDocument() {

    }

    @Override
    public void requestReadDocument() {

    }

    public List<BaseComponent> getDocumentData(){
        return mDocumentModel.returnModel();
    }
}
