package com.naver.smarteditor.lesssmarteditor.data.edit.local;

import android.content.Context;

import com.naver.smarteditor.lesssmarteditor.MyApplication;
import com.naver.smarteditor.lesssmarteditor.data.BaseComponent;

import java.util.ArrayList;

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
    public void setComponent(ArrayList<BaseComponent> components, final LoadComponentCallBack loadComponentCallBack) {
        editComponentLocalDataSource.setComponent(components, new LoadComponentCallBack() {
            @Override
            public void OnComponentLoaded(ArrayList<BaseComponent> components) {
                if(loadComponentCallBack != null) {
                    loadComponentCallBack.OnComponentLoaded(components);
                }
            }
        });
    }

    @Override
    public void getComponent(final LoadComponentCallBack loadComponentCallBack) {
        editComponentLocalDataSource.getComponent(new LoadComponentCallBack() {
            @Override
            public void OnComponentLoaded(ArrayList<BaseComponent> components) {
                if(loadComponentCallBack != null){
                    loadComponentCallBack.OnComponentLoaded(components);
                }
            }
        });
    }

    @Override
    public void addComponent(BaseComponent.TypE type, Object componentData, final LoadComponentCallBack loadComponentCallBack) {
        MyApplication.LogController.makeLog(TAG, "addComponent", localLogPermission);
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
    public void editComponent(CharSequence s, int position, final LoadComponentCallBack loadComponentCallBack){
        editComponentLocalDataSource.editComponent(s, position, new LoadComponentCallBack() {
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
    public void saveDocument(final SaveToDatabaseCallBack saveToDatabaseCallBack) {
        editComponentLocalDataSource.saveDocument(new SaveToDatabaseCallBack() {
            @Override
            public void OnSaveFinished() {
                if(saveToDatabaseCallBack != null) {
                    saveToDatabaseCallBack.OnSaveFinished();
                }
            }
        });
    }

    @Override
    public void loadDocument(final LoadFromDatabaseCallBack loadFromDataBaseCallBack) {
        editComponentLocalDataSource.loadDocument(new LoadFromDatabaseCallBack() {
            @Override
            public void OnLoadFinished() {
                if(loadFromDataBaseCallBack != null){
                    loadFromDataBaseCallBack.OnLoadFinished();
                }
            }
        });
    }


}
