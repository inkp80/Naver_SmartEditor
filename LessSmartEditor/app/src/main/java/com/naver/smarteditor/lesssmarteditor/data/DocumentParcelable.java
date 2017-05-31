package com.naver.smarteditor.lesssmarteditor.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by macbook on 2017. 5. 25..
 */

public class DocumentParcelable implements Parcelable {
    int doc_id;
    String title;
    String timestamp;
    String componentsJson;

    public DocumentParcelable(Parcel in){
        readFromParcel(in);
    }

    public DocumentParcelable(int doc_id, String title, String timestamp, String componentsJson){
        this.doc_id = doc_id;
        this.title = title;
        this.timestamp = timestamp;
        this.componentsJson = componentsJson;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags){
        dest.writeInt(doc_id);
        dest.writeString(title);
        dest.writeString(timestamp);
        dest.writeString(componentsJson);
    }

    private void readFromParcel(Parcel in){
        this.doc_id = in.readInt();
        this.title = in.readString();
        this.timestamp = in.readString();
        this.componentsJson = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public DocumentParcelable createFromParcel(Parcel in){
            return new DocumentParcelable(in);
        }

        public DocumentParcelable[] newArray(int size){
            return new DocumentParcelable[size];
        }
    };

    public int getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(int doc_id) {
        this.doc_id = doc_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getComponentsJson() {
        return componentsJson;
    }

    public void setComponentsJson(String componentsJson) {
        this.componentsJson = componentsJson;
    }
}
