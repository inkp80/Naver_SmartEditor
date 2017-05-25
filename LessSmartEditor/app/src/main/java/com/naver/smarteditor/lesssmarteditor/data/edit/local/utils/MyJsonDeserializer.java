package com.naver.smarteditor.lesssmarteditor.data.edit.local.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.naver.smarteditor.lesssmarteditor.MyApplication;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.component.MapComponent;
import com.naver.smarteditor.lesssmarteditor.data.component.TextComponent;

import java.lang.reflect.Type;

/**
 * Created by NAVER on 2017. 5. 25..
 */

public class MyJsonDeserializer implements JsonDeserializer<BaseComponent> {

    @Override
    public BaseComponent deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement jsonType = jsonObject.get("componentType");
        String type = jsonType.getAsString();

        BaseComponent typeModel = null;

        if ("TEXT".equals(type)) {
            MyApplication.LogController.makeLog("Deserializer type", "TEXT", true);
            typeModel = new TextComponent(null);
            JsonElement json_text = jsonObject.get("text");
            ((TextComponent) typeModel).setText(json_text.getAsString());
        } else if ("IMG".equals(type)) {

        } else if ("MAP".equals(type)) {
            MyApplication.LogController.makeLog("Deserializer type", "Map", true);
            JsonElement json_name = jsonObject.get("placeName");
            typeModel = new MapComponent("1", "1", "1", json_name.getAsString());
        }
        // TODO : set properties of type model

        return typeModel;
    }
}
