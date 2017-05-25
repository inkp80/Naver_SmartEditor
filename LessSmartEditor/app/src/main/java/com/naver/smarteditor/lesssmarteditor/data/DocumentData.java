package com.naver.smarteditor.lesssmarteditor.data;

/**
 * Created by NAVER on 2017. 5. 25..
 */

public class DocumentData {
    private int _id;
    private String title;
    private String timeStemp;
    private String Components_json;

    public DocumentData(int _id,String title, String timeStemp, String components_json) {
        this._id = _id;
        this.title = title;
        this.timeStemp = timeStemp;
        this.Components_json = components_json;
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

    public String getComponents_json() {
        return Components_json;
    }

    public void setComponents_json(String components_json) {
        Components_json = components_json;
    }
}
