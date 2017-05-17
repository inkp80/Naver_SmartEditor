package com.naver.smarteditor.map_app;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NAVER on 2017. 5. 15..
 */

public class Places {
//    String errorMessage;
    @SerializedName("total")
    int total;
    @SerializedName("items")
    ArrayList<item> item;

    @Override
    public String toString() {
        return total + "/" +item.get(0).getTitle() + "/" + item.get(0).getMapx() + "/" + item.get(0).getMapy();
    }
}