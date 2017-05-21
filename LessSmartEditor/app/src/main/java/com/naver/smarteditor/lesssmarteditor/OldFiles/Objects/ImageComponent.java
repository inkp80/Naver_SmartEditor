package com.naver.smarteditor.lesssmarteditor.Objects;

/**
 * Created by NAVER on 2017. 5. 17..
 */

public class ImageComponent {
    String imageUri;

    public ImageComponent( String imageUri) {

        this.imageUri = imageUri;
    }


    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
