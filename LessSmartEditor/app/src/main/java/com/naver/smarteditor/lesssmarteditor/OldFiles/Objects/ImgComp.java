package com.naver.smarteditor.lesssmarteditor.Objects;

import android.content.Intent;

/**
 * Created by NAVER on 2017. 5. 18..
 */

public class ImgComp extends Comp {
    private String imageUri;

    public ImgComp(){
        type = Type.IMAGE;
    }


    @Override
    public void setComponentData(Object componentData) {
        imageUri = componentData.toString();
    }
}
