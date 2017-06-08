package com.naver.smarteditor.lesssmarteditor.adpater.edit.util;

import android.text.Spannable;
import android.text.style.StyleSpan;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.naver.smarteditor.lesssmarteditor.LogController;
import com.naver.smarteditor.lesssmarteditor.data.SpanInfo;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.UnderlineCustom;
import com.naver.smarteditor.lesssmarteditor.views.edit.SpanClassGenerator;
import com.naver.smarteditor.lesssmarteditor.views.edit.SmartEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.naver.smarteditor.lesssmarteditor.views.edit.SmartEditText.TYPE_UNDERLINE;

/**
 * Created by NAVER on 2017. 6. 7..
 */

public class SpanInfoExtractor {
    public static List<SpanInfo> extractSpansFromSpannable(Spannable spannable){
        List<SpanInfo> spanInfos = new ArrayList<>();
        StyleSpan typeSpan[];
        typeSpan = spannable.getSpans(0, spannable.length(), StyleSpan.class);
        for (StyleSpan span : typeSpan) {
            spanInfos.add(new SpanInfo(spannable.getSpanStart(span), spannable.getSpanEnd(span), span.getStyle()));
        }


        //check Underline spans
        UnderlineCustom[] underlineSpans;
        underlineSpans = spannable.getSpans(0, spannable.length(), UnderlineCustom.class);
        for(UnderlineCustom underlineSpan : underlineSpans){
            spanInfos.add(new SpanInfo(spannable.getSpanStart(underlineSpan), spannable.getSpanEnd(underlineSpan), TYPE_UNDERLINE));
        }
        return spanInfos;
    }

    public static List<SpanInfo> spanJsonDeserializer(String json){
        LogController.makeLog("spanJsonDeserializer", json, true);
        //TODO json deserializer
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(json);
        } catch (Exception e){
            LogController.makeLog("EEER", "JSON Error : string to json-array", true);
        }


        List<SpanInfo> spans = new ArrayList<>();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();


        for(int i = 0; i < jsonArray.length(); i++){
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                spans.add(gson.fromJson(jsonObject.toString(), SpanInfo.class));
            } catch (Exception e){

            }
        }
        return spans;
    }

    public static <T> void setSpansIntoSpannable(List<SpanInfo> spanInfoLists, SmartEditText target){
        Spannable spannable = target.getText();

        for(int i=0; i<spanInfoLists.size(); i++){
            int typeValue = spanInfoLists.get(i).getSpanType();
            int spanStart = spanInfoLists.get(i).getSpanStart();
            int spanEnd = spanInfoLists.get(i).getSpanEnd();
            SpanClassGenerator<T> spanClassGenerator = new SpanClassGenerator<>(ClassSetter(typeValue));

            Log.d("sadasd",String.valueOf(spanClassGenerator));
            spannable.setSpan(spanClassGenerator.get(typeValue), spanStart, spanEnd, typeValue);
        }

    }
//
//    public String spansToJson(){
//
//    }

    public static <T> Class ClassSetter(int type){
        if(type == 1){
            return StyleSpan.class;
        } else if(type == 2){
            return StyleSpan.class;
        } else {
            return UnderlineCustom.class;
        }
    }
}
