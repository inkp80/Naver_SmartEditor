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
    public void addComponentToDocument(BaseComponent.TypE type, Object componentData, LoadComponentCallBack loadComponentCallBack) {
        BaseComponent component = null;

        if(type == BaseComponent.TypE.TEXT){
            TextComponent textComponent = new TextComponent(null);
            component = textComponent;
        } else if (type == BaseComponent.TypE.IMG){
            ImgComponent imgComponent = new ImgComponent((String)componentData);
            component = imgComponent;
        } else if(type == BaseComponent.TypE.MAP){
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

        insertIntoDatabase(title, mComponents);

        if(saveToDatabaseCallBack != null) {
            saveToDatabaseCallBack.OnSaveFinished();
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
    public void getDocumentsListFromDatabase(LoadFromDatabaseCallBack loadFromDatabaseCallBack) {

        List<DocumentData> docList = converCursorToList(readFromLocalDatabase());
        Collections.reverse(docList);

        if(loadFromDatabaseCallBack != null){
            loadFromDatabaseCallBack.OnLoadFinished(docList);
        }
    }


    @Override
    public void updateDocumentInDatabase(String title, int doc_id, UpdateToDatabaseCallBack updateToDatabaseCallBack) {
        updateDatabase(title, doc_id, mComponents);
        if(updateToDatabaseCallBack != null){
            updateToDatabaseCallBack.OnUpdateFinished();
        }
    }

    @Override
    public void deleteDocumentInDatabase(int doc_id, LoadFromDatabaseCallBack loadFromDatabaseCallBack) {
        deleteFromLocalDatabase(doc_id);
        List<DocumentData> docList = converCursorToList(readFromLocalDatabase());
        Collections.reverse(docList);

        if(loadFromDatabaseCallBack != null){
            loadFromDatabaseCallBack.OnLoadFinished(docList);
        }
    }





    private void insertIntoDatabase(String title, List<BaseComponent> components){
        String jsonStr = new Gson().toJson(components);

        String query = "INSERT INTO "+ EditorContract.ComponentEntry.TABLE_NAME + "(" + EditorContract.ComponentEntry.COLUMN_TITLE + "," +EditorContract.ComponentEntry.COLUMN_TIMESTAMP+
                ", "+EditorContract.ComponentEntry.COLUNM_COMPONENTS_JSON + ") values ('"+title+"', '"+ Calendar.getInstance().getTimeInMillis() + "', '"+jsonStr+"');";
        try {
            db.execSQL(query);
            MyApplication.LogController.makeLog(TAG, "Database processing was Successfully done",localLogPermission);
        } catch (Exception e){
            MyApplication.LogController.makeLog(TAG, "Error : Database insert", localLogPermission);
        }
    }

    private Cursor readFromLocalDatabase() {
        String querySelete = "SELECT * " +
                "FROM " + EditorContract.ComponentEntry.TABLE_NAME
                + " order by (" + EditorContract.ComponentEntry.COLUMN_TIMESTAMP + ")";

        Cursor cursor = db.rawQuery(querySelete, null);
        return cursor;
    }

    private void deleteFromLocalDatabase(int docId){
        String query = "DELETE FROM " + EditorContract.ComponentEntry.TABLE_NAME + " WHERE _id ='" + String.valueOf(docId) +"'";
        try{
            db.execSQL(query);
        } catch (Exception e){
            MyApplication.LogController.makeLog(TAG, "Error : Database delete", localLogPermission);
        }
    }

    private void updateDatabase(String title, int docId, List<BaseComponent> components){
        String jsonStr = new Gson().toJson(components);
        String query = "UPDATE " + EditorContract.ComponentEntry.TABLE_NAME
                + " SET " + EditorContract.ComponentEntry.COLUMN_TITLE + "='" + title + "'," + EditorContract.ComponentEntry.COLUMN_TIMESTAMP + "='" + Calendar.getInstance().getTimeInMillis() + "',"
                + EditorContract.ComponentEntry.COLUNM_COMPONENTS_JSON + "='" + jsonStr + "' where _id = " + String.valueOf(docId) + ";";

        try {
            db.execSQL(query);
        } catch (Exception e) {
            MyApplication.LogController.makeLog(TAG, "DB ERROR :" + e, localLogPermission);
        }
    }


    private List<DocumentData> converCursorToList(Cursor cursor){
        List<DocumentData> DocList = new ArrayList<>();

        while (cursor.moveToNext()) {
            int _id = cursor.getInt(EditorContract.COL_ID);
            String title = cursor.getString(EditorContract.COL_TITLE);
            String timeStamp = cursor.getString(EditorContract.COL_TIMESTAMP);
            String jsonObject = cursor.getString(EditorContract.COL_COMPONENTS_JSON);
            DocumentData data = new DocumentData(_id, title,timeStamp, jsonObject);
            DocList.add(data);
        }
        return DocList;
    }

}