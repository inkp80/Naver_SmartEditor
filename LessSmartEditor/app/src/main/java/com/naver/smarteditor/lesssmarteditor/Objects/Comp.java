package com.naver.smarteditor.lesssmarteditor.Objects;

/**
 * Created by NAVER on 2017. 5. 18..
 */

public class Comp {

    public enum Type {IMAGE, MAP, TEXT}
    public Type type;

    public void Comp(){
        type = null;
    }


    public Type getComponentType(){
        return type;
    }
    public void setComponentData(Object componentData){

    }

}
