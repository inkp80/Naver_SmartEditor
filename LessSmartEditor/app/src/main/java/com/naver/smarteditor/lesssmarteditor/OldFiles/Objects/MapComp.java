package com.naver.smarteditor.lesssmarteditor.Objects;

/**
 * Created by NAVER on 2017. 5. 18..
 */

public class MapComp extends Comp {

    public String placeName;
    public String placeAddress;
    public String placeCoords;
    public String staticMapUri;

    public MapComp(){
        type = Type.MAP;
    }

    @Override
    public void setComponentData(Object componentData) {
        placeName = ((MapComponent) componentData).getPlaceName();
        placeAddress = ((MapComponent) componentData).getPlaceAddress();
        placeCoords = ((MapComponent) componentData).getPlaceCoodrs();
        staticMapUri = ((MapComponent) componentData).getImageUri();
    }

    public void setStaticMapUri(String imgUri){
        staticMapUri = imgUri;
    }

}
