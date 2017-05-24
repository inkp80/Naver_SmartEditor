package com.naver.smarteditor.lesssmarteditor.data.edit.local;

import android.content.Context;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.naver.smarteditor.lesssmarteditor.MyApplication;
import com.naver.smarteditor.lesssmarteditor.data.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.ImgComponent;
import com.naver.smarteditor.lesssmarteditor.data.MapComponent;
import com.naver.smarteditor.lesssmarteditor.data.TextComponent;
import com.naver.smarteditor.lesssmarteditor.data.api.naver_map.PlaceItemPasser;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.utils.EditorDbHelper;
import com.naver.smarteditor.lesssmarteditor.data.to_json.TextData;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public class EditorComponentLocalDataSource implements EditorComponentDataSource {
    private final String TAG = "EditorComponentLocalDataSource";
    private boolean localLogPermission = true;
    private EditorDbHelper mDbHelper;
    private Context mContext;

    private Gson gson;



    ArrayList<BaseComponent> mComponents;

    public EditorComponentLocalDataSource(Context context){
        //singleton 구현
        //database open
        this.mContext = context;
        mDbHelper = new EditorDbHelper(context);
        mDbHelper.getWritableDatabase();
        mComponents = new ArrayList<>();
        gson = new Gson();
    }

    @Override
    public void setComponent(ArrayList<BaseComponent> components, LoadComponentCallBack loadComponentCallBack) {
        this.mComponents = components;
        if(loadComponentCallBack != null) {
            loadComponentCallBack.OnComponentLoaded(components);
        }
    }

    @Override
    public void getComponent(LoadComponentCallBack loadComponentCallBack) {
        //do something -

        if(loadComponentCallBack != null) {
            loadComponentCallBack.OnComponentLoaded(mComponents);
        }
    }

    @Override
    public void addComponent(BaseComponent.TypE type, Object componentData, LoadComponentCallBack loadComponentCallBack) {
        BaseComponent component = null;
        //TODO: check type then add to list


        if(type == BaseComponent.TypE.TEXT){
            TextComponent textComponent = new TextComponent(null);
            component = textComponent;
        }

        else if (type == BaseComponent.TypE.IMG){
            ImgComponent imgComponent = new ImgComponent((Uri)componentData);
            component = imgComponent;
        }

        else if(type == BaseComponent.TypE.MAP){
            PlaceItemPasser passer = (PlaceItemPasser) componentData;
            MapComponent mapComponent = new MapComponent(passer.getPlaceName(), passer.getPlaceAddress(), passer.getPlaceCoords(), passer.getPlaceUri());
            component = mapComponent;
        }

        try {
            if (component == null) {
                throw new FoolException();
            }
            mComponents.add(component);
        } catch (FoolException e){
            MyApplication.LogController.makeLog(TAG, "Fail to add Component", localLogPermission);
        }


        if(loadComponentCallBack != null) {
            loadComponentCallBack.OnComponentLoaded(mComponents);
        }

    }

    @Override
    public void editComponent(CharSequence s, int position, LoadComponentCallBack loadComponentCallBack) {

        ((TextComponent) mComponents.get(position)).setText(s.toString());
        if(loadComponentCallBack != null) {
            loadComponentCallBack.OnComponentLoaded(mComponents);
        }
    }

    @Override
    public void clearComponents() {
        mComponents.clear();
        mComponents = new ArrayList<>();
    }

    @Override
    public void saveDocument(SaveToDatabaseCallBack saveToDatabaseCallBack) {
        //do for( ~ ) save to db
        //before must parse Data to Json;
        MyApplication.LogController.makeLog(TAG, String.valueOf(mComponents.size()), localLogPermission);
        MyApplication.LogController.makeLog(TAG, String.valueOf(mComponents.get(0).getComponentType().getTypeValue()), localLogPermission);
        String jsonBuilder="";
        for(int i=0; i<mComponents.size(); i++){
            BaseComponent.TypE type =  mComponents.get(i).getComponentType();
            if(type == BaseComponent.TypE.TEXT){
                jsonBuilder += new GsonBuilder().serializeNulls().create().toJson((TextComponent)mComponents.get(i));

            } else if(type == BaseComponent.TypE.IMG){
                gson.toJson((ImgComponent)mComponents.get(i));
            } else if(type == BaseComponent.TypE.MAP){
                gson.toJson((MapComponent)mComponents.get(i));
            }
        }
        MyApplication.LogController.makeLog(TAG, jsonBuilder, localLogPermission);

        if(saveToDatabaseCallBack != null) {
            saveToDatabaseCallBack.OnSaveFinished();
        }
    }

    @Override
    public void loadDocument(LoadFromDatabaseCallBack loadFromDataBaseCallBack) {
        JsonObject jsonObject=null;

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(BaseComponent.class, new MyTypeModelDeserializer());
        Gson gson = gsonBuilder.create();

        BaseComponent myTypeModel = gson.fromJson(jsonObject, BaseComponent.class);
    }
}

class FoolException extends Exception {
    //Exception for test
}

class MyTypeModelDeserializer implements JsonDeserializer<BaseComponent> {

    @Override
    public BaseComponent deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement jsonType = jsonObject.get("componentType");
        String type = jsonType.getAsString();

        BaseComponent typeModel = null;

        if("TEXT".equals(type)) {
            typeModel = new TextComponent(null);
        } else if("IMG".equals(type)) {
            typeModel = new ImgComponent(null);
        }
        // TODO : set properties of type model

        return typeModel;
    }

}