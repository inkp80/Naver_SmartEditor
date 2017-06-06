package com.naver.smarteditor.smarteditor_textspannable;

/**
 * Created by NAVER on 2017. 6. 6..
 */

public class Gen<T> {
    private Class<T> mClass;

    public Gen(Class<T> cls) {
        mClass = cls;
    }

    public T get(int value) {
        try {
//            return mClass.newInstance();
            return mClass.getDeclaredConstructor( Integer.TYPE ).newInstance(value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}