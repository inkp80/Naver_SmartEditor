package com.naver.smarteditor.lesssmarteditor.Objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by NAVER on 2017. 5. 18..
 */

public class PlaceItemPasser implements Parcelable {
    String placeName;
    String placeAddress;
    String placeCoords;
    String placeUri;

    public PlaceItemPasser(String name, String address, String coords, String uri){
        this.placeName = name;
        this.placeAddress = address;
        this.placeCoords = coords;
        this.placeUri = uri;
    }

    public PlaceItemPasser(Parcel src){
        this.placeName = src.readString();
        this.placeAddress = src.readString();
        this.placeCoords = src.readString();
        this.placeCoords = src.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public PlaceItemPasser createFromParcel(Parcel in){
            return new PlaceItemPasser(in);
        }

        public PlaceItemPasser[] newArray(int size){
            return new PlaceItemPasser[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(placeName);
        dest.writeString(placeAddress);
        dest.writeString(placeCoords);
        dest.writeString(placeUri);
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

    public String getPlaceUri() {
        return placeUri;
    }

    public void setPlaceUri(String placeUri) {
        this.placeUri = placeUri;
    }
}
