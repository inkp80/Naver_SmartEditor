package com.naver.smarteditor.lesssmarteditor.data.component;

import com.naver.smarteditor.lesssmarteditor.LogController;
import com.naver.smarteditor.lesssmarteditor.MyApplication;

/**
 * Created by NAVER on 2017. 6. 2..
 */

public class TitleComponent extends BaseComponent {

    private String title;
    private String titleBackgroundUri = MyApplication.NO_TITLE_IMG;

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
    public void updateData(BaseComponent baseComponent) {
        TitleComponent component = (TitleComponent) baseComponent;
        if(component.getTitle() != null){
            this.title = component.getTitle();
        }

        if(component.getTitleBackgroundUri() != MyApplication.NO_TITLE_IMG){
            LogController.makeLog("titlecompoennt", "updating uri", true);
            this.titleBackgroundUri = component.titleBackgroundUri;
        }
    }
}
