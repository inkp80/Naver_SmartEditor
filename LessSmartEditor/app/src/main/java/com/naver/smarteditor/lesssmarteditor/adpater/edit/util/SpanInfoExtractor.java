package com.naver.smarteditor.lesssmarteditor.adpater.edit.util;

import android.text.Spannable;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.naver.smarteditor.lesssmarteditor.LogController;
import com.naver.smarteditor.lesssmarteditor.data.SpanInfo;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import static com.naver.smarteditor.lesssmarteditor.views.edit.SmartEditText.Typeface_Underline;

/**
 * Created by NAVER on 2017. 6. 7..
 */

public class SpanInfoExtractor {
    public static List<SpanInfo> extractSpanInformation(Spannable spannable){
        List<SpanInfo> spanInfos = new ArrayList<>();
        StyleSpan typeSpan[];
        typeSpan = spannable.getSpans(0, spannable.length(), StyleSpan.class);
        for (StyleSpan span : typeSpan) {
            spanInfos.add(new SpanInfo(spannable.getSpanStart(span), spannable.getSpanEnd(span), span.getStyle()));
        }


        //check Underline spans
        UnderlineSpan underlineSpans[];
        underlineSpans = spannable.getSpans(0, spannable.length(), UnderlineSpan.class);
        for(UnderlineSpan underlineSpan : underlineSpans){
            spanInfos.add(new SpanInfo(spannable.getSpanStart(underlineSpan), spannable.getSpanEnd(underlineSpan), Typeface_Underline));
        }
        return spanInfos;
    }

    public static void setSpan(Spannable spannable, String json){
        //TODO json deserializer
        try {
            JSONArray jsonArray = new JSONArray(json);
        } catch (Exception e){
            LogController.makeLog("EEER", "JSON Error : string to json-array", true);
        }

        List<SpanInfo> components = new ArrayList<>();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(SpanInfo.class);
        Gson gson = gsonBuilder.create();

        JSONObject object = jsonArray.getJSONObject(i);
                gson.fromJson(object.toString(), BaseComponent.class);
                components.add(gson.fromJson(object.toString(), BaseComponent.class));

    }
}
