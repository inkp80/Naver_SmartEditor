package com.naver.smarteditor.lesssmarteditor.views.edit;

/**
 * Created by NAVER on 2017. 6. 6..
 */

public class ClassGenerator<T> {
    private Class<T> mClass;

    public ClassGenerator(Class<T> cls) {
        mClass = cls;
    }

    public T get(int value) {
        try {
//            return mClass.newInstance();
            if(value == 0)
                return mClass.newInstance();
            return mClass.getDeclaredConstructor( Integer.TYPE ).newInstance(value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}