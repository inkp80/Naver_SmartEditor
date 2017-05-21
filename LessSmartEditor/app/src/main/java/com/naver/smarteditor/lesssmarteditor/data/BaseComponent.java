package com.naver.smarteditor.lesssmarteditor.data;

/**
 * Created by NAVER on 2017. 5. 21..
 */

abstract public class BaseComponent {

    public enum TypE {
        TEXT, IMG, MAP};

    public int indexOfComponent;
    public TypE componentType;

    abstract public void setComponentType(TypE type);
    abstract public int getComponentType();

    public int getComponentIndex(){
        return indexOfComponent;
    }
}
