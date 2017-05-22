package com.naver.smarteditor.lesssmarteditor.data;

/**
 * Created by NAVER on 2017. 5. 21..
 */

abstract public class BaseComponent {

    public enum TypE {
        TEXT(0), IMG(1), MAP(2);
        private int value;
        private TypE(int value){
            this.value = value;
        }
        public int getTypeValue(){
            return value;
        }

    };

    public int indexOfComponent;
    public TypE componentType;

    abstract public TypE getComponentType();

    public int getComponentIndex(){
        return indexOfComponent;
    }
}
