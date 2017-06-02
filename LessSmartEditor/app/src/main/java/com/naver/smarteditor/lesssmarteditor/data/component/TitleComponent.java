package com.naver.smarteditor.lesssmarteditor.data.component;

/**
 * Created by NAVER on 2017. 6. 2..
 */

public class TitleComponent extends BaseComponent {
    private String title;
    private String titleBackgroundUri;

    public TitleComponent(String title, String titleBackgroundUri){
        this.componentType = Type.TITLE;
        this.title = title;
        this.titleBackgroundUri = titleBackgroundUri;
    }
    @Override
    public Type getComponentType() {
        return this.componentType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleBackgroundUri() {
        return titleBackgroundUri;
    }

    public void setTitleBackgroundUri(String titleBackgroundUri) {
        this.titleBackgroundUri = titleBackgroundUri;
    }

    @Override
    void updateData(BaseComponent baseComponent) {
        TitleComponent component = (TitleComponent) baseComponent;
        if(component.getTitle() != null){
            this.title = component.getTitle();
        }

        if(component.getTitleBackgroundUri() != null){
            this.titleBackgroundUri = component.titleBackgroundUri;
        }
    }
}
