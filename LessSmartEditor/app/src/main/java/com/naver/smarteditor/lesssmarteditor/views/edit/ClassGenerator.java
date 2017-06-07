package com.naver.smarteditor.lesssmarteditor.views.edit;

import static com.naver.smarteditor.lesssmarteditor.views.edit.SmartEditText.Typeface_Underline;

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
            if(value == Typeface_Underline)
                return mClass.newInstance();
            return mClass.getDeclaredConstructor( Integer.TYPE ).newInstance(value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}