package com.naver.smarteditor.lesssmarteditor.data.component;


/**
 * Created by NAVER on 2017. 5. 23..
 */

public class ImgComponent extends BaseComponent {

    String imgUri;

    public ImgComponent(String uri){
        this.componentType = Type.IMG;
        imgUri = uri;
    }

    @Override
    public Type getComponentType() {
        return this.componentType;
    }

    public String getImgUri(){
        return imgUri;
    }

    public void setImgUri(String uri){
        imgUri = uri;
    }

    @Override
    public void updateData(BaseComponent baseComponent) {

    }
}
