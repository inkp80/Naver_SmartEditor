package com.naver.smarteditor.lesssmarteditor.data.api.naver_map;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by NAVER on 2017. 5. 22..
 */

public class PlaceItemParcelable implements Parcelable {
    String placeName;
    String placeAddress;
    String placeCoords;
    String placeUri;


    public PlaceItemParcelable(Parcel in){
        readFromParcel(in);
    }

    public PlaceItemParcelable(String name, String address, String coords, String uri){
        this.placeName = name;
        this.placeAddress = address;
        this.placeCoords = coords;
        this.placeUri = uri;
    }


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

    private void readFromParcel(Parcel in){
        this.placeName = in.readString();
        this.placeAddress = in.readString();
        this.placeCoords = in.readString();
        this.placeUri = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public PlaceItemParcelable createFromParcel(Parcel in){
            return new PlaceItemParcelable(in);
        }

        public PlaceItemParcelable[] newArray(int size){
            return new PlaceItemParcelable[size];
        }
    };

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
