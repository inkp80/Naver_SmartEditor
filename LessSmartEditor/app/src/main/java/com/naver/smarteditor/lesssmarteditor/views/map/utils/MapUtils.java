package com.naver.smarteditor.lesssmarteditor.views.map.utils;

import android.net.Uri;

/**
 * Created by NAVER on 2017. 5. 23..
 */

public class MapUtils {
    public static String buildStaticMapUrlWithCoords(int x, int y){

        String coords = buildCoords(x,y);

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("openapi.naver.com")
                .appendPath("v1").appendPath("map").appendPath("staticmap.bin")
                .appendQueryParameter("clientId", "gqPejUpvUeMl9HFXQ7h7")
                .appendQueryParameter("url", "com.naver.smarteditor.map_app")
                .appendQueryParameter("crs", "NHN:128")
                .appendQueryParameter("center", coords)
                .appendQueryParameter("level","11")
                .appendQueryParameter("w","160")
                .appendQueryParameter("h","160")
                .appendQueryParameter("baselayer","default")
                .appendQueryParameter("format","png")
                .appendQueryParameter("markers", coords);

        return builder.toString();
    }

    public static String buildCoords(int x, int y){
        return String.valueOf(x) + "," + String.valueOf(y);
    }
}
