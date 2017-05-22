package com.naver.smarteditor.lesssmarteditor.data.edit;

import com.naver.smarteditor.lesssmarteditor.MyApplication;
import com.naver.smarteditor.lesssmarteditor.data.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.TextComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public class EditComponentLocalDataSource implements EditComponentDataSource {
    private final String TAG = "EditComponentLocalDataSource";
    private boolean localLogPermission = false;


    ArrayList<BaseComponent> components;

    public EditComponentLocalDataSource(){
        components = new ArrayList<>();
    }

    @Override
    public void setComponent(ArrayList<BaseComponent> components, LoadComponentCallBack loadComponentCallBack) {
        this.components = components;
        if(loadComponentCallBack != null) {
            loadComponentCallBack.OnComponentLoaded(components);
        }
    }

    @Override
    public void getComponent(LoadComponentCallBack loadComponentCallBack) {
        //do something -

        if(loadComponentCallBack != null) {
            loadComponentCallBack.OnComponentLoaded(components);
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
            components.add(component);
        } catch (FoolException e){
            MyApplication.LogController.makeLog(TAG, "Fail to add Component", localLogPermission);
        }


        if(loadComponentCallBack != null) {
            loadComponentCallBack.OnComponentLoaded(components);
        }

    }

    @Override
    public void editComponent(CharSequence s, int position, LoadComponentCallBack loadComponentCallBack) {
        ((TextComponent) components.get(position)).setText(s.toString());

        for(int i=0; i<components.size(); i++){
            TextComponent textComponent = (TextComponent) components.get(i);
            MyApplication.LogController.makeLog(TAG, String.valueOf(i)+","+String.valueOf(textComponent.getText()), localLogPermission);
        }
        if(loadComponentCallBack != null) {
            loadComponentCallBack.OnComponentLoaded(components);
        }
    }
}

class FoolException extends Exception {
    //Exception for test
}
