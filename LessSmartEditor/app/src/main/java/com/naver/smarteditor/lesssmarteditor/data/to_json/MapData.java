package com.naver.smarteditor.lesssmarteditor.data.to_json;

/**
 * Created by NAVER on 2017. 5. 24..
 */

public class MapData {
    String placeName;
    String placeAddress;
    String placeCoords;
    String placeMapUri;

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

    public String getPlaceCoords() {
        return placeCoords;
    }

    public void setPlaceCoords(String placeCoords) {
        this.placeCoords = placeCoords;
    }

    public String getPlaceMapUri() {
        return placeMapUri;
    }

    public void setPlaceMapUri(String placeMapUri) {
        this.placeMapUri = placeMapUri;
    }
}
