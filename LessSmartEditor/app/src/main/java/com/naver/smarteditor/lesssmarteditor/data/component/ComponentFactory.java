package com.naver.smarteditor.lesssmarteditor.data.component;

import com.naver.smarteditor.lesssmarteditor.MyApplication;

/**
 * Created by NAVER on 2017. 6. 1..
 */

public class ComponentFactory {
    public BaseComponent createComponent(BaseComponent.Type type, BaseComponent baseComponent){
        switch (type){
            case TEXT:
                return null;
            case IMG:
                return null;
            case MAP:
                return null;
            default:
                return null;
        }
    }
}
