package com.naver.smarteditor.lesssmarteditor.data.edit;

import com.naver.smarteditor.lesssmarteditor.MyApplication;
import com.naver.smarteditor.lesssmarteditor.data.BaseComponent;

import java.util.ArrayList;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public class EditComponentRepository implements EditComponentDataSource {
    private final String TAG = "EditComponentRepository";
    private boolean localLogPermission = true;

    private static EditComponentRepository editComponentRepository;

    public static EditComponentRepository getInstance(){
        if(editComponentRepository == null){
            editComponentRepository = new EditComponentRepository();
        }
        return editComponentRepository;
    } //Singleton

    private EditComponentLocalDataSource editComponentLocalDataSource;

    private EditComponentRepository(){
        editComponentLocalDataSource = new EditComponentLocalDataSource();
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

}
