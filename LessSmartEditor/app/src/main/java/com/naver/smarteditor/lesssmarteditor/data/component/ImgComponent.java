package com.naver.smarteditor.lesssmarteditor.data.component;

import android.net.Uri;


/**
 * Created by NAVER on 2017. 5. 23..
 */

public class ImgComponent extends BaseComponent {

    String imgUri;

    public ImgComponent(String uri){
        this.componentType = TypE.IMG;
        imgUri = uri;
    }

    @Override
    public TypE getComponentType() {
        return this.componentType;
    }

    public String getImgUri(){
        return imgUri;
    }

    public void setImgUri(String uri){
        imgUri = uri;
    }
}
