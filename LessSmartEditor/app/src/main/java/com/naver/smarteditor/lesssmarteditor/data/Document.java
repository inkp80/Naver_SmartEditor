package com.naver.smarteditor.lesssmarteditor.data;

/**
 * Created by NAVER on 2017. 5. 25..
 */

public class Document {
    private int _id;
    private String title;
    private String timeStamp;
    private String componentsJson;

    public Document(int _id, String title, String timeStamp, String componentsJson) {
        this._id = _id;
        this.title = title;
        this.timeStamp = timeStamp;
        this.componentsJson = componentsJson;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getComponentsJson() {
        return componentsJson;
    }

    public void setComponentsJson(String componentsJson) {
        this.componentsJson = componentsJson;
    }
}
