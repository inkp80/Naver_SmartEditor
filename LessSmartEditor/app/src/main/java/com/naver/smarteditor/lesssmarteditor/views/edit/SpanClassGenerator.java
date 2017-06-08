package com.naver.smarteditor.lesssmarteditor.views.edit;

import static com.naver.smarteditor.lesssmarteditor.views.edit.SmartEditText.TYPE_UNDERLINE;

/**
 * Created by NAVER on 2017. 6. 6..
 */

public class SpanClassGenerator<T> {
    private Class<T> mClass;

    public SpanClassGenerator(Class<T> cls) {
        mClass = cls;
    }

    public T get(int value) {
        try {
//            return mClass.newInstance();
            if(value == TYPE_UNDERLINE)
                return mClass.newInstance();
            return mClass.getDeclaredConstructor( Integer.TYPE ).newInstance(value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}