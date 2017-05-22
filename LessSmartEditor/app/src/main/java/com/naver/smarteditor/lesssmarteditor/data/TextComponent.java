package com.naver.smarteditor.lesssmarteditor.data;

import android.widget.BaseAdapter;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public class TextComponent extends BaseComponent{

    private String text;

    public TextComponent(String text) {
        this.componentType = TypE.TEXT;
        this.text = text;
    }

    @Override
    public TypE getComponentType(){
        return this.componentType;
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;

    }
}
