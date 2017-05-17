package com.naver.smarteditor.lesssmarteditor.Objects;

import java.util.List;

/**
 * Created by NAVER on 2017. 5. 17..
 */

public class PlaceRequestResult {
    private int total;
    private List<Place> Places;

    public PlaceRequestResult(int total, List<Place> places) {
        this.total = total;
        Places = places;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Place> getPlaces() {
        return Places;
    }

    public void setPlaces(List<Place> places) {
        Places = places;
    }
}
