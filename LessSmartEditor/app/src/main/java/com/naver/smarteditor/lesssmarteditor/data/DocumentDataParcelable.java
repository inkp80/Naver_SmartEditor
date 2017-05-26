package com.naver.smarteditor.lesssmarteditor.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by macbook on 2017. 5. 25..
 */

public class DocumentDataParcelable implements Parcelable {
    int doc_id;
    String title;
    String timestamp;
    String componentsJson;

    public DocumentDataParcelable(Parcel in){
        readFromParcel(in);
    }

    public DocumentDataParcelable(int doc_id, String title, String timestamp, String componentsJson){
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
        public DocumentDataParcelable createFromParcel(Parcel in){
            return new DocumentDataParcelable(in);
        }

        public DocumentDataParcelable[] newArray(int size){
            return new DocumentDataParcelable[size];
        }
    };
}
