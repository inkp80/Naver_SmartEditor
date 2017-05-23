package com.naver.smarteditor.lesssmarteditor.data.edit;

import com.naver.smarteditor.lesssmarteditor.MyApplication;
import com.naver.smarteditor.lesssmarteditor.data.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.TextComponent;

import java.util.ArrayList;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public class EditComponentLocalDataSource implements EditComponentDataSource {
    private final String TAG = "EditComponentLocalDataSource";
    private boolean localLogPermission = true;


    ArrayList<BaseComponent> mComponents;

    public EditComponentLocalDataSource(){
        mComponents = new ArrayList<>();
    }

    @Override
    public void setComponent(ArrayList<BaseComponent> components, LoadComponentCallBack loadComponentCallBack) {
        this.mComponents = components;
        if(loadComponentCallBack != null) {
            loadComponentCallBack.OnComponentLoaded(components);
        }
    }

    @Override
    public void getComponent(LoadComponentCallBack loadComponentCallBack) {
        //do something -

        if(loadComponentCallBack != null) {
            loadComponentCallBack.OnComponentLoaded(mComponents);
        }
    }

    @Override
    public void addComponent(BaseComponent.TypE type, LoadComponentCallBack loadComponentCallBack) {
        BaseComponent component = null;
        //TODO: check type then add to list

        if(type == BaseComponent.TypE.TEXT){
            TextComponent textComponent = new TextComponent(null);
            component = textComponent;

        }

        try {
            if (component == null) {
                throw new FoolException();
            }
            mComponents.add(component);
        } catch (FoolException e){
            MyApplication.LogController.makeLog(TAG, "Fail to add Component", localLogPermission);
        }


        if(loadComponentCallBack != null) {
            MyApplication.LogController.makeLog(TAG, "component Size : " + mComponents.size(), localLogPermission);
            loadComponentCallBack.OnComponentLoaded(mComponents);
        }

    }

    @Override
    public void editComponent(CharSequence s, int position, LoadComponentCallBack loadComponentCallBack) {

        ((TextComponent) mComponents.get(position)).setText(s.toString());
        if(loadComponentCallBack != null) {
            loadComponentCallBack.OnComponentLoaded(mComponents);
        }
    }
}

class FoolException extends Exception {
    //Exception for test
}
