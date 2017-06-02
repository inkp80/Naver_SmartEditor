package com.naver.smarteditor.lesssmarteditor.data.component;

/**
 * Created by NAVER on 2017. 5. 21..
 */

abstract public class BaseComponent {

    //역할에 대해서 고려해볼 것
    //interface - type
    public enum Type {
        TEXT(0), IMG(1), MAP(2), TITLE(3), NOTDEFINE(-1);
        private int value;
        private Type(int value){
            this.value = value;
        }
        public int getTypeValue(){
            return value;
        }
    };

    public int indexOfComponent;
    public Type componentType;

    abstract public Type getComponentType();

    public int getComponentIndex(){
        return indexOfComponent;
    }

    public static Type getType(int typeNum){
        for(Type type : Type.values()){
            if(type.getTypeValue() == typeNum){
                return type;
            }
        }
        return Type.NOTDEFINE;
    }

    abstract void updateData(BaseComponent baseComponent);
}
