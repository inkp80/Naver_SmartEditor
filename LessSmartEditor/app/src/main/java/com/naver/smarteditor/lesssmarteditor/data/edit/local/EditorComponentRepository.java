package com.naver.smarteditor.lesssmarteditor.data.edit.local;

import android.content.Context;

import com.naver.smarteditor.lesssmarteditor.data.DocumentData;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public class EditorComponentRepository implements EditorComponentDataSource {
    private final String TAG = "EditorComponentRepository";
    private boolean localLogPermission = true;

    private static EditorComponentRepository editComponentRepository;

    public static EditorComponentRepository getInstance(Context context){
        if(editComponentRepository == null){
            editComponentRepository = new EditorComponentRepository(context);
        }
        return editComponentRepository;
    } //Singleton

    private EditorComponentLocalDataSource editComponentLocalDataSource;

    private EditorComponentRepository(Context mContext){
        editComponentLocalDataSource = new EditorComponentLocalDataSource(mContext);
    }

    @Override
    public void setComponents(ArrayList<BaseComponent> components, final LoadComponentCallBack loadComponentCallBack) {
        editComponentLocalDataSource.setComponents(components, new LoadComponentCallBack() {
            @Override
            public void OnComponentLoaded(ArrayList<BaseComponent> components) {
                if(loadComponentCallBack != null) {
                    loadComponentCallBack.OnComponentLoaded(components);
                }
            }
        });
    }

    @Override
    public void addComponent(BaseComponent.TypE type, Object componentData, final LoadComponentCallBack loadComponentCallBack) {
        editComponentLocalDataSource.addComponent(type, componentData, new LoadComponentCallBack() {
            @Override
            public void OnComponentLoaded(ArrayList<BaseComponent> components) {
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
            public void OnComponentLoaded(ArrayList<BaseComponent> components) {
                if(loadComponentCallBack != null) {
                    loadComponentCallBack.OnComponentLoaded(components);
                }
            }
        });
    }

    @Override
    public void clearComponents() {
        editComponentLocalDataSource.clearComponents();
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
        });
    }



    @Override
    public void getDocumentsListFromDatabase(final LoadFromDatabaseCallBack loadFromDatabaseCallBack) {
        editComponentLocalDataSource.getDocumentsListFromDatabase(new LoadFromDatabaseCallBack() {
            @Override
            public void OnLoadFinished(List<DocumentData> data) {
                if(loadFromDatabaseCallBack != null){
                    loadFromDatabaseCallBack.OnLoadFinished(data);
                }
            }
        });
    }

    @Override
    public void convertJsonToComponents(String jsonComponents, final LoadComponentCallBack loadComponentCallBack) {
        editComponentLocalDataSource.convertJsonToComponents(jsonComponents, new LoadComponentCallBack() {
            @Override
            public void OnComponentLoaded(ArrayList<BaseComponent> components) {
                if(loadComponentCallBack != null){
                    loadComponentCallBack.OnComponentLoaded(components);
                }
            }
        });
    }

    @Override
    public void deleteComponent(int position, final LoadComponentCallBack loadComponentCallBack) {
        editComponentLocalDataSource.deleteComponent(position, new LoadComponentCallBack() {
            @Override
            public void OnComponentLoaded(ArrayList<BaseComponent> components) {
                if(loadComponentCallBack != null){
                    loadComponentCallBack.OnComponentLoaded(components);
                }
            }
        });

    }

    @Override
    public void updateDocumentInDatabase(String title, int doc_id, final UpdateToDatabaseCallBack updateToDatabaseCallBack) {
        editComponentLocalDataSource.updateDocumentInDatabase(title, doc_id, new UpdateToDatabaseCallBack() {
            @Override
            public void OnUpdateFinished() {
                if(updateToDatabaseCallBack != null){
                    updateToDatabaseCallBack.OnUpdateFinished();
                }
            }
        });

    }


    @Override
    public void changeComponentOrder(int from, int to, final LoadComponentCallBack loadComponentCallBack) {
        editComponentLocalDataSource.changeComponentOrder(from, to, new LoadComponentCallBack() {
            @Override
            public void OnComponentLoaded(ArrayList<BaseComponent> components) {
                if(loadComponentCallBack != null){
                    loadComponentCallBack.OnComponentLoaded(components);
                }
            }
        });
    }
}
