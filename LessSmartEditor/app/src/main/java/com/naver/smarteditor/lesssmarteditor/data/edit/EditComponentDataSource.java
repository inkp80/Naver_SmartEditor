package com.naver.smarteditor.lesssmarteditor.data.edit;

import com.naver.smarteditor.lesssmarteditor.data.BaseComponent;

import java.util.ArrayList;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public interface EditComponentDataSource {
    interface LoadComponentCallBack{
        void OnComponentLoaded(ArrayList<BaseComponent> components);
    }

    void setComponent(ArrayList<BaseComponent> components, LoadComponentCallBack loadComponentCallBack);

    void getComponent(LoadComponentCallBack loadComponentCallBack);

    void addComponent(BaseComponent.TypE type, LoadComponentCallBack loadComponentCallBack);

    void editComponent(CharSequence s, int position, LoadComponentCallBack loadComponentCallBack);


}
