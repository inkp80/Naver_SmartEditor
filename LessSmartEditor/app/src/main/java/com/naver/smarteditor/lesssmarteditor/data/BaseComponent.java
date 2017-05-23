package com.naver.smarteditor.lesssmarteditor.data;

/**
 * Created by NAVER on 2017. 5. 21..
 */

abstract public class BaseComponent {

    //역할에 대해서 고려해볼 것
    //interface - type
    public enum TypE {
        TEXT(0), IMG(1), MAP(2), NOTDEFINE(-1);
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

    public static TypE getType(int typeNum){
        for(TypE type : TypE.values()){
            if(type.getTypeValue() == typeNum){
                return type;
            }
        }
        return TypE.NOTDEFINE;
    }
}
