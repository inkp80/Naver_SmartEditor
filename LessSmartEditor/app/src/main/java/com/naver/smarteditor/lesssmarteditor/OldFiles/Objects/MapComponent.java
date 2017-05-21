package com.naver.smarteditor.lesssmarteditor.Objects;

/**
 * Created by NAVER on 2017. 5. 17..
 */

public class MapComponent {
    String imageUri;
    String placeName;
    String placeAddress;
    String placeCoodrs;


    public MapComponent(String imageUri, String placeName, String placeAddress, String placeCoodrs) {
        this.imageUri = imageUri;
        this.placeName = placeName;
        this.placeAddress = placeAddress;
        this.placeCoodrs = placeCoodrs;
    }


    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    public String getPlaceCoodrs() {
        return placeCoodrs;
    }

    public void setPlaceCoodrs(String placeCoodrs) {
        this.placeCoodrs = placeCoodrs;
    }
}
