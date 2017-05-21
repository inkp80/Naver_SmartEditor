package com.naver.smarteditor.lesssmarteditor.Objects;

/**
 * Created by NAVER on 2017. 5. 18..
 */

public abstract class Comp {

    public enum Type {
        IMAGE(0), MAP(1), TEXT(2);
        private int value;
        private Type(int value){
            this.value = value;
        }
        public int getValue(){
            return this.value;
        }
    }


    public abstract Type getComponentType();
    public void setComponentData(Object componentData){

    }

}
