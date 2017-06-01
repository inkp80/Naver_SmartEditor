package com.naver.smarteditor.lesssmarteditor.data.edit.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.naver.smarteditor.lesssmarteditor.LogController;
import com.naver.smarteditor.lesssmarteditor.data.Document;
import com.naver.smarteditor.lesssmarteditor.data.DocumentParcelable;
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

public class DocumentLocalDataSource implements DocumentDataSource {
    private final String TAG = "DocumentLocalDataSource";
    private boolean localLogPermission = true;
    private EditorDbHelper mDbHelper;
    private SQLiteDatabase db;


    private final String NEW_DOCUMENT = "new_document";
    private String currentDocumentId = NEW_DOCUMENT;

    List<BaseComponent> mComponents;

    public DocumentLocalDataSource(Context context){
        mComponents = new ArrayList<>();
        mDbHelper = new EditorDbHelper(context);
        db = mDbHelper.getWritableDatabase();
    }


    //components
    @Override
    public void replaceDocumentComponents(List<BaseComponent> components, LoadComponentCallBack loadComponentCallBack) {
        this.mComponents = components;
        if(loadComponentCallBack != null) {
            loadComponentCallBack.OnComponentLoaded(components);
        }
    }

    @Override
    public void addComponentToDocument(BaseComponent.Type type, Object componentData, LoadComponentCallBack loadComponentCallBack) {
        //TODO : Factory pattern
        BaseComponent component = null;

        if(type == BaseComponent.Type.TEXT){
            TextComponent textComponent = new TextComponent("");
            component = textComponent;
        } else if (type == BaseComponent.Type.IMG){
            ImgComponent imgComponent = new ImgComponent((String)componentData);
            component = imgComponent;
        } else if(type == BaseComponent.Type.MAP){
            PlaceItemParcelable passer = (PlaceItemParcelable) componentData;
            MapComponent mapComponent = new MapComponent(passer.getPlaceName(), passer.getPlaceAddress(), passer.getPlaceCoords(), passer.getPlaceUri());
            component = mapComponent;
        }

        try {
            if (component == null) {
                throw new EmptyComponentException();
            }
            mComponents.add(component);
        } catch (com.naver.smarteditor.lesssmarteditor.data.edit.local.utils.EmptyComponentException e){
            LogController.makeLog(TAG + e, "Fail to add Component", localLogPermission);
        }


        if(loadComponentCallBack != null) {
            loadComponentCallBack.OnComponentLoaded(mComponents);
        }
    }

    @Override
    public void updateEditTextComponent(CharSequence s, int position, LoadComponentCallBack loadComponentCallBack) {
        LogController.makeLog("TextWatcher Update ","pos" + String.valueOf(position), localLogPermission);

        ((TextComponent) mComponents.get(position)).setText(s.toString());
        if(loadComponentCallBack != null) {
            loadComponentCallBack.OnComponentLoaded(mComponents);
        }
    }

    @Override
    public void clearDocumentComponents() {
        mComponents.clear();
        mComponents = new ArrayList<>();
        currentDocumentId = NEW_DOCUMENT;
    }

    @Override
    public void deleteDocumentComponent(int position, LoadComponentCallBack loadComponentCallBack) {
        mComponents.remove(position);
        if(loadComponentCallBack != null){
            loadComponentCallBack.OnComponentLoaded(mComponents);
        }
    }


    @Override
    public void swapDocumentComponent(int from, int to, LoadComponentCallBack loadComponentCallBack) {
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
        LogController.makeLog(TAG, "DOC ID: "+currentDocumentId, localLogPermission);

        if (title.length() == 0) {
            title = "제목 없음";
        }
        if (checkDocumentComponentValidate() == false) {
            if (saveToDatabaseCallBack != null) {
                saveToDatabaseCallBack.OnSaveFailed();
            }
            return;
        }

        if(currentDocumentId == NEW_DOCUMENT) {
            //TODO : asynTask - save to database

            insertIntoDatabase(title, mComponents);
            if (saveToDatabaseCallBack != null) {
                saveToDatabaseCallBack.OnSaveFinished();
            }

            //TODO : set value inserted;
        } else {
            LogController.makeLog(TAG, "updating", localLogPermission);
            updateDatabase(title, currentDocumentId, mComponents);
            if (saveToDatabaseCallBack != null) {
                saveToDatabaseCallBack.OnSaveFinished();
            }
        }
    }

    private boolean checkDocumentComponentValidate(){
        List<BaseComponent> components = new ArrayList<>();
        LogController.makeLog(TAG, String.valueOf(mComponents.size()), localLogPermission);
        for(BaseComponent baseComponent : mComponents){
            BaseComponent.Type type = baseComponent.getComponentType();
            switch(type){
                case TEXT:
                    TextComponent thisTexComponent = (TextComponent) baseComponent;
                    if(thisTexComponent.getText().length() == 0){
                        break;
                    }
                    components.add(baseComponent);
                    break;
                case IMG:
                    components.add(baseComponent);
                    break;
                case MAP:
                    components.add(baseComponent);
                    break;
                default:
                    break;
            }
        }
        if(components.size() == 0){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void convertParcelToComponents(DocumentParcelable documentParcelable, LoadComponentCallBack loadComponentCallBack) {

        currentDocumentId = String.valueOf(documentParcelable.getDoc_id());
        String jsonComponents = documentParcelable.getComponentsJson();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonComponents);
        } catch (Exception e){
            LogController.makeLog(TAG, "JSON Error : string to json-array", localLogPermission);
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(BaseComponent.class, new MyJsonDeserializer());
        Gson gson = gsonBuilder.create();

        for(int i=0; i < jsonArray.length(); i++){
            try{
                JSONObject object = jsonArray.getJSONObject(i);
                mComponents.add(gson.fromJson(object.toString(), BaseComponent.class));
            } catch (Exception e){
                LogController.makeLog(TAG, "FAIL : load components from json", localLogPermission);
            }
        }

        if(loadComponentCallBack != null){
            loadComponentCallBack.OnComponentLoaded(mComponents);
        }

    }


    @Override
    public void getDocumentsListFromDatabase(LoadFromDatabaseCallBack loadFromDatabaseCallBack) {

        List<Document> docList = converCursorToList(readFromLocalDatabase());
        Collections.reverse(docList);

        if(loadFromDatabaseCallBack != null){
            loadFromDatabaseCallBack.OnLoadFinished(docList);
        }
    }


    //database
    @Override
    public void updateDocumentFromDatabase(String title, int doc_id, UpdateToDatabaseCallBack updateToDatabaseCallBack) {
//        updateDatabase(title, doc_id, mComponents);
        if(updateToDatabaseCallBack != null){
            updateToDatabaseCallBack.OnUpdateFinished();
        }
    }

    @Override
    public void deleteDocumentFromDatabase(int doc_id, LoadFromDatabaseCallBack loadFromDatabaseCallBack) {
        if(String.valueOf(doc_id) == currentDocumentId){
            currentDocumentId = NEW_DOCUMENT;
        }
        deleteFromLocalDatabase(doc_id);
        List<Document> docList = converCursorToList(readFromLocalDatabase());
        Collections.reverse(docList);

        if(loadFromDatabaseCallBack != null){
            loadFromDatabaseCallBack.OnLoadFinished(docList);
        }
    }


    //DB CRUD
    private void insertIntoDatabase(String title, List<BaseComponent> components){
        String jsonStr = new Gson().toJson(components);

        String query = "INSERT INTO "+ EditorContract.ComponentEntry.TABLE_NAME + "(" + EditorContract.ComponentEntry.COLUMN_TITLE + "," +EditorContract.ComponentEntry.COLUMN_TIMESTAMP+
                ", "+EditorContract.ComponentEntry.COLUNM_COMPONENTS_JSON + ") values ('"+title+"', '"+ Calendar.getInstance().getTimeInMillis() + "', '"+jsonStr+"');";
        try {
            db.execSQL(query);
            Cursor cursor = db.rawQuery("SELECT * FROM documents ORDER BY _id DESC LIMIT 1;", null);
            cursor.moveToNext();
            String documentId = cursor.getString(EditorContract.COL_ID);
            currentDocumentId = documentId;
            LogController.makeLog(TAG, "Database processing was Successfully done",localLogPermission);
        } catch (Exception e){
            LogController.makeLog(TAG, "Error : Database insert, " + e, localLogPermission);
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
            LogController.makeLog(TAG, "Error : Database delete", localLogPermission);
        }
    }

    private void updateDatabase(String title, String docId, List<BaseComponent> components){
        String jsonStr = new Gson().toJson(components);
        String query = "UPDATE " + EditorContract.ComponentEntry.TABLE_NAME
                + " SET " + EditorContract.ComponentEntry.COLUMN_TITLE + "='" + title + "'," + EditorContract.ComponentEntry.COLUMN_TIMESTAMP + "='" + Calendar.getInstance().getTimeInMillis() + "',"
                + EditorContract.ComponentEntry.COLUNM_COMPONENTS_JSON + "='" + jsonStr + "' where _id = '" + docId + "';";

        try {
            db.execSQL(query);
        } catch (Exception e) {
            LogController.makeLog(TAG, "DB ERROR :" + e, localLogPermission);
        }
    }

    private List<Document> converCursorToList(Cursor cursor){
        List<Document> DocList = new ArrayList<>();

        while (cursor.moveToNext()) {
            int _id = cursor.getInt(EditorContract.COL_ID);
            String title = cursor.getString(EditorContract.COL_TITLE);
            String timeStamp = cursor.getString(EditorContract.COL_TIMESTAMP);
            String jsonObject = cursor.getString(EditorContract.COL_COMPONENTS_JSON);
            Document data = new Document(_id, title,timeStamp, jsonObject);
            DocList.add(data);
        }
        return DocList;
    }

}