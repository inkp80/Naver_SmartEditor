package com.naver.smarteditor.lesssmarteditor.Objects;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by NAVER on 2017. 5. 17..
 */

public class PlaceRequestResult {
    private int total;
    @SerializedName("items")
    private List<PlaceItem> Places;

    public PlaceRequestResult(int total, List<PlaceItem> places) {
        this.total = total;
        Places = places;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<PlaceItem> getPlaces() {
        return Places;
    }

    public void setPlaces(List<PlaceItem> places) {
        Places = places;
    }
}
