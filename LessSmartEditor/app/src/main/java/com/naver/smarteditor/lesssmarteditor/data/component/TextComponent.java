package com.naver.smarteditor.lesssmarteditor.data.component;

import com.naver.smarteditor.lesssmarteditor.LogController;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public class TextComponent extends BaseComponent{

    private String text;
    private String textSpans;

    public TextComponent(String text, String textSpans) {
        this.componentType = Type.TEXT;
        this.text = text;
        this.textSpans = textSpans;
    }

    @Override
    public Type getComponentType(){
        return this.componentType;
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }

    public String getTextSpans() {
        return textSpans;
    }

    public void setTextSpans(String textSpans) {
        this.textSpans = textSpans;
    }

    @Override
    public void updateData(BaseComponent baseComponent) {
        TextComponent component = (TextComponent) baseComponent;
        if(component.getText() != null){
            this.text = component.getText();
        }
        if(component.getTextSpans() != null){
            this.textSpans = component.textSpans;
        }
    }


}
