package com.naver.smarteditor.lesssmarteditor.data;

import android.widget.BaseAdapter;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public class TextComponent extends BaseComponent{

    private String text;

    @Override
    public void setComponentType(TypE type) {
        this.componentType = type;
    }

    @Override
    public int getComponentType() {
        return 0;
    }
}
