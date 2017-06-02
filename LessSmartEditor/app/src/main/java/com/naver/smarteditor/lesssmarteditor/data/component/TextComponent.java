package com.naver.smarteditor.lesssmarteditor.data.component;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public class TextComponent extends BaseComponent{

    private String text;

    public TextComponent(String text) {
        this.componentType = Type.TEXT;
        this.text = text;
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

    @Override
    void updateData(BaseComponent baseComponent) {
        TextComponent component = (TextComponent) baseComponent;
        if(component.getText() != null){
            this.text = component.getText();
        }
    }
}
