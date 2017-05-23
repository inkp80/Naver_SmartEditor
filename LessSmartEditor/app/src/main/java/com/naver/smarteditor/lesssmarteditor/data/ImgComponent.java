package com.naver.smarteditor.lesssmarteditor.data;

import android.net.Uri;

import com.naver.smarteditor.lesssmarteditor.data.BaseComponent;


/**
 * Created by NAVER on 2017. 5. 23..
 */

public class ImgComponent extends BaseComponent {

    Uri imgUri;

    public ImgComponent(Uri uri){
        this.componentType = TypE.IMG;
        imgUri = uri;
    }

    @Override
    public TypE getComponentType() {
        return this.componentType;
    }

    public Uri getImgUri(){
        return imgUri;
    }

    public void setImgUri(Uri uri){
        imgUri = uri;
    }
}
