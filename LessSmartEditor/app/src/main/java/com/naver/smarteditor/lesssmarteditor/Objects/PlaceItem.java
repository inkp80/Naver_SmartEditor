package com.naver.smarteditor.lesssmarteditor.Objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NAVER on 2017. 5. 17..
 */

public class PlaceItem {

    @SerializedName("title")
    private String placeName;
    @SerializedName("roadAddress")
    private String placeAddress;

    public PlaceItem(String placeName, String placeAddress, int katechMapX, int katechMapY) {
        this.placeName = placeName;
        this.placeAddress = placeAddress;
        this.katechMapX = katechMapX;
        this.katechMapY = katechMapY;
    }

    @SerializedName("mapx")
    int katechMapX;
    @SerializedName("mapy")
    int katechMapY;

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

    public int getKatechMapX() {
        return katechMapX;
    }

    public void setKatechMapX(int katechMapX) {
        this.katechMapX = katechMapX;
    }

    public int getKatechMapY() {
        return katechMapY;
    }

    public void setKatechMapY(int katechMapY) {
        this.katechMapY = katechMapY;
    }
}
