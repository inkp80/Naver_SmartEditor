package com.naver.smarteditor.lesssmarteditor.data.edit.local;

import android.content.Context;
import android.net.Uri;

import com.google.gson.Gson;
import com.naver.smarteditor.lesssmarteditor.MyApplication;
import com.naver.smarteditor.lesssmarteditor.data.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.ImgComponent;
import com.naver.smarteditor.lesssmarteditor.data.MapComponent;
import com.naver.smarteditor.lesssmarteditor.data.TextComponent;
import com.naver.smarteditor.lesssmarteditor.data.api.naver_map.PlaceItemPasser;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.utils.EditorDbHelper;

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
        for(int i=0; i<mComponents.size(); i++){
            BaseComponent.TypE type =  mComponents.get(i).getComponentType();
            if(type == BaseComponent.TypE.TEXT){
                gson.toJson((TextComponent)mComponents.get(i));
            } else if(type == BaseComponent.TypE.IMG){
                gson.toJson((ImgComponent)mComponents.get(i));
            } else if(type == BaseComponent.TypE.MAP){
                gson.toJson((MapComponent)mComponents.get(i));
            }
        }
        MyApplication.LogController.makeLog(TAG, gson.toString(), localLogPermission);
    }

    @Override
    public void loadDocument(LoadFromDatabaseCallBack loadFromDataBaseCallBack) {

    }
}

class FoolException extends Exception {
    //Exception for test
}
