package com.naver.smarteditor.lesssmarteditor.data.edit.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.naver.smarteditor.lesssmarteditor.MyApplication;
import com.naver.smarteditor.lesssmarteditor.data.DocumentData;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.component.ImgComponent;
import com.naver.smarteditor.lesssmarteditor.data.component.MapComponent;
import com.naver.smarteditor.lesssmarteditor.data.component.TextComponent;
import com.naver.smarteditor.lesssmarteditor.data.api.naver_map.PlaceItemParcelable;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.utils.EditorContract;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.utils.EditorDbHelper;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.utils.EmptyComponentException;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.utils.MyJsonDeserializer;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.*;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public class EditorComponentLocalDataSource implements EditorComponentDataSource {
    private final String TAG = "EditorComponentLocalDataSource";
    private boolean localLogPermission = true;
    private EditorDbHelper mDbHelper;
    private SQLiteDatabase db;
    private Context mContext;




    ArrayList<BaseComponent> mComponents;

    public EditorComponentLocalDataSource(Context context){

        mComponents = new ArrayList<>();

        this.mContext = context;
        mDbHelper = new EditorDbHelper(context);
        db = mDbHelper.getWritableDatabase();
    }


    //components
    @Override
    public void setComponents(ArrayList<BaseComponent> components, LoadComponentCallBack loadComponentCallBack) {
        this.mComponents = components;
        if(loadComponentCallBack != null) {
            loadComponentCallBack.OnComponentLoaded(components);
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
            ImgComponent imgComponent = new ImgComponent((String)componentData);
            component = imgComponent;
        }

        else if(type == BaseComponent.TypE.MAP){
            PlaceItemParcelable passer = (PlaceItemParcelable) componentData;
            MapComponent mapComponent = new MapComponent(passer.getPlaceName(), passer.getPlaceAddress(), passer.getPlaceCoords(), passer.getPlaceUri());
            component = mapComponent;
        }

        try {
            if (component == null) {
                throw new EmptyComponentException();
            }
            mComponents.add(component);
        } catch (EmptyComponentException e){
            MyApplication.LogController.makeLog(TAG + e, "Fail to add Component", localLogPermission);
        }


        if(loadComponentCallBack != null) {
            loadComponentCallBack.OnComponentLoaded(mComponents);
        }

    }

    @Override
    public void updateEditTextComponent(CharSequence s, int position, LoadComponentCallBack loadComponentCallBack) {

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
    public void deleteComponent(int position, LoadComponentCallBack loadComponentCallBack) {
        mComponents.remove(position);
        if(loadComponentCallBack != null){
            loadComponentCallBack.OnComponentLoaded(mComponents);
        }
    }


    @Override
    public void changeComponentOrder(int from, int to, LoadComponentCallBack loadComponentCallBack) {
        if (from < to) {
            for (int i = from; i < to; i++) {
                swap(mComponents, i, i + 1);
            }
        } else {
            for (int i = from; i > to; i--) {
                swap(mComponents, i, i - 1);
            }
        }

        if(loadComponentCallBack != null){
            loadComponentCallBack.OnComponentLoaded(mComponents);
        }
    }



    //database
    @Override
    public void saveDocumentToDatabase(String title, SaveToDatabaseCallBack saveToDatabaseCallBack) {
        //TODO : asynTask - save to database
        String jsonStr = new Gson().toJson(mComponents);

        String query = "INSERT INTO "+ EditorContract.ComponentEntry.TABLE_NAME + "(" + EditorContract.ComponentEntry.COLUMN_TITLE + "," +EditorContract.ComponentEntry.COLUMN_TIMESTAMP+
                ", "+EditorContract.ComponentEntry.COLUNM_COMPONENTS_JSON + ") values ('"+title+"', '"+Calendar.getInstance().toString()+"', '"+jsonStr+"');";
        try {
            db.execSQL(query);
            MyApplication.LogController.makeLog(TAG, "Database processing was Successfully done",localLogPermission);
        } catch (Exception e){
            MyApplication.LogController.makeLog(TAG, "Error : Database insert", localLogPermission);
        }

        if(saveToDatabaseCallBack != null) {
            saveToDatabaseCallBack.OnSaveFinished();
        }
    }

    @Override
    public void getDocumentsListFromDatabase(LoadFromDatabaseCallBack loadFromDatabaseCallBack) {

        // request data from database
        String query = "SELECT * FROM " + EditorContract.ComponentEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToNext();

        List<DocumentData> DocList = new ArrayList<>();

        while (cursor.moveToNext()) {
            int doc_id = cursor.getInt(EditorContract.COL_ID);
            String title = cursor.getString(EditorContract.COL_TITLE);
//            MyApplication.LogController.makeLog(TAG, title, localPermission);
            String timeStamp = cursor.getString(EditorContract.COL_TIMESTAMP);
            String jsonObject = cursor.getString(EditorContract.COL_COMPONENTS_JSON);
            DocumentData data = new DocumentData(doc_id, title,timeStamp, jsonObject);
            DocList.add(data);
        }
        if(loadFromDatabaseCallBack != null){
            loadFromDatabaseCallBack.OnLoadFinished(DocList);
        }
    }

    @Override
    public void convertJsonToComponents(String jsonComponents, LoadComponentCallBack loadComponentCallBack) {

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonComponents);
        } catch (Exception e){
            MyApplication.LogController.makeLog(TAG, "JSON Error : string to json-array", localLogPermission);
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(BaseComponent.class, new MyJsonDeserializer());
        Gson gson = gsonBuilder.create();

        for(int i=0; i < jsonArray.length(); i++){
            try{
                JSONObject object = jsonArray.getJSONObject(i);
                mComponents.add(gson.fromJson(object.toString(), BaseComponent.class));
            } catch (Exception e){
                MyApplication.LogController.makeLog(TAG, "FAIL : load components from json", localLogPermission);
            }
        }

        if(loadComponentCallBack != null){
            loadComponentCallBack.OnComponentLoaded(mComponents);
        }

    }


    @Override
    public void updateDocumentInDatabase(int doc_id, UpdateToDatabaseCallBack updateToDatabaseCallBack) {

        String jsonStr = new Gson().toJson(mComponents);
        String query = "UPDATE " + EditorContract.ComponentEntry.TABLE_NAME
                + " SET " + EditorContract.ComponentEntry.COLUMN_TIMESTAMP + "='" + Calendar.getInstance().toString() + "',"
                + EditorContract.ComponentEntry.COLUNM_COMPONENTS_JSON + "='" + jsonStr + "' where _id = " + String.valueOf(doc_id) + ";";

        try {
            db.execSQL(query);
        } catch (Exception e) {
            MyApplication.LogController.makeLog(TAG, "DB ERROR :" + e, localLogPermission);
        }

        if(updateToDatabaseCallBack != null){
            updateToDatabaseCallBack.OnUpdateFinished();
        }
    }
}