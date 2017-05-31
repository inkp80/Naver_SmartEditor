package com.naver.smarteditor.lesssmarteditor.data;

/**
 * Created by NAVER on 2017. 5. 25..
 */

public class Document {
    private int _id;
    private String title;
    private String timeStemp;
    private String componentsJson;

    public Document(int _id, String title, String timeStemp, String componentsJson) {
        this._id = _id;
        this.title = title;
        this.timeStemp = timeStemp;
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

    public String getTimeStemp() {
        return timeStemp;
    }

    public void setTimeStemp(String timeStemp) {
        this.timeStemp = timeStemp;
    }

    public String getComponentsJson() {
        return componentsJson;
    }

    public void setComponentsJson(String componentsJson) {
        this.componentsJson = componentsJson;
    }
}
