package com.naver.smarteditor.lesssmarteditor.data.component;

/**
 * Created by NAVER on 2017. 5. 24..
 */

public class MapComponent extends BaseComponent {

    private String placeName;
    private String placeAddress;
    private String placeCoords;
    private String placeMapImgUri;

    public MapComponent(String name, String address, String coords, String uri){
        this.componentType = TypE.MAP;
        this.placeName = name;
        this.placeAddress = address;
        this.placeCoords = coords;
        this.placeMapImgUri = uri;
    }
    @Override
    public TypE getComponentType() {
        return this.componentType;
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

    public String getPlaceCoords() {
        return placeCoords;
    }

    public void setPlaceCoords(String placeCoords) {
        this.placeCoords = placeCoords;
    }

    public String getPlaceMapImgUri() {
        return placeMapImgUri;
    }

    public void setPlaceMapImgUri(String placeMapImgUri) {
        this.placeMapImgUri = placeMapImgUri;
    }
}
