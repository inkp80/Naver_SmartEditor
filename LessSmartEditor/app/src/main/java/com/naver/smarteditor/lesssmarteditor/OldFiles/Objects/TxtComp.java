package com.naver.smarteditor.lesssmarteditor.Objects;

import android.util.Log;

/**
 * Created by NAVER on 2017. 5. 18..
 */

public class TxtComp extends Comp {

    private String text;
    public TxtComp(){
        type = Type.TEXT;
    }

    @Override
    public Type getComponentType() {
        return Type.TEXT;
    }

    @Override
    public void setComponentData(Object componentData) {
        TextComponent txt = (TextComponent) componentData;
        text = txt.getTextString();
    }
}
