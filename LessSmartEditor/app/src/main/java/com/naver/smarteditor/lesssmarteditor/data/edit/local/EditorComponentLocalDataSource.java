package com.naver.smarteditor.lesssmarteditor.data.edit.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

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
import java.util.List;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public class EditorComponentLocalDataSource implements EditorComponentDataSource {
    private final String TAG = "EditorComponentLocalDataSource";
    private boolean localLogPermission = true;
    private EditorDbHelper mDbHelper;
    private SQLiteDatabase db;
    private Context mContext;

    private Gson gson;



    ArrayList<BaseComponent> mComponents;

    public EditorComponentLocalDataSource(Context context){
        //singleton 구현
        //database open
        mComponents = new ArrayList<>();


        this.mContext = context;
        gson = new Gson();
        mDbHelper = new EditorDbHelper(context);
        db = mDbHelper.getWritableDatabase();
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

    String jsonStr = null;
    @Override
    public void saveDocument(String title, SaveToDatabaseCallBack saveToDatabaseCallBack) {
        //TODO : asynTask - save to database
        jsonStr = new Gson().toJson(mComponents);

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
    public void loadDocument(int _id, LoadFromDatabaseCallBack loadFromDataBaseCallBack) {

        //TODO : asycTask - load from database
//        JsonObject obj = new JsonParser().parse(jsonStr).getAsJsonObject();

        String query = "SELECT * FROM " + EditorContract.ComponentEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToNext();

        MyApplication.LogController.makeLog(TAG, "size :"+String.valueOf(cursor.getCount()),localLogPermission);
        MyApplication.LogController.makeLog(TAG, "inner :"+cursor.getString(EditorContract.COL_COMPONENTS_JSON), localLogPermission);



        String json_str = cursor.getString(EditorContract.COL_COMPONENTS_JSON);
        MyApplication.LogController.makeLog(TAG,"sec",localLogPermission);

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(json_str);
            MyApplication.LogController.makeLog("load from db", jsonArray.toString(), localLogPermission);
        } catch (Exception e){
            MyApplication.LogController.makeLog(TAG, "Error : json Processing", localLogPermission);
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(BaseComponent.class, new MyJsonDeserializer());
        Gson gson = gsonBuilder.create();


//        BaseComponent myTypeModel = gson.fromJson(jsonStr, BaseComponent.class);
//        MyApplication.LogController.makeLog(TAG, String.valueOf(myTypeModel.getComponentType().getTypeValue()), localLogPermission);

//        JSONArray jsonArray = jsnobject.getJSONArray("locations");
//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONObject explrObject = jsonArray.getJSONObject(i);
//        }



        for(int i=0; i < jsonArray.length(); i++){
            try{
                JSONObject object = jsonArray.getJSONObject(i);
                mComponents.add(gson.fromJson(object.toString(), BaseComponent.class));

                //Test field
                BaseComponent.TypE type = mComponents.get(i).getComponentType();
                if(type == BaseComponent.TypE.TEXT){
                    String textVal = ((TextComponent)mComponents.get(i)).getText();
                    MyApplication.LogController.makeLog("Json result", textVal, localLogPermission);
                } else if(type == BaseComponent.TypE.IMG) {
                    String uri = ((ImgComponent)mComponents.get(i)).getImgUri().toString();
                    MyApplication.LogController.makeLog("Json result", uri, localLogPermission);
                } else if(type == BaseComponent.TypE.MAP){
                    String placeVal = ((MapComponent)mComponents.get(i)).getPlaceName();
                    MyApplication.LogController.makeLog("Json result", placeVal, localLogPermission);
                }

            } catch (Exception e){

            }

//            lists.add(gson.fromJson(jsonArray.getJSONObject(i), BaseComponent));
        }

//        Type listType = new TypeToken<List<BaseComponent>>(){}.getType();
//        //In this test code i just shove the JSON here as string.
//        List<BaseComponent> asd = gson.fromJson("[{'componentType':\"TEXT\"}, {'component':\"MAP\"}]", listType);
//        MyApplication.LogController.makeLog("heel", String.valueOf(asd.get(0).getComponentType().getTypeValue()), true);
//        gson.fromJson(jsonStr, listType);
//        MyApplication.LogController.makeLog("deserialize", gson.toString(), true);

    }

    @Override
    public void requestDocuments(LoadFromDatabaseCallBack loadFromDatabaseCallBack) {
        String query = "SELECT * FROM " + EditorContract.ComponentEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToNext();

        List<DocumentData> DocList = new ArrayList<>();

        while (cursor.moveToNext()) {
            int doc_id = cursor.getInt(EditorContract.COL_ID);
            String title = cursor.getString(EditorContract.COL_TITLE);
            MyApplication.LogController.makeLog(TAG, title, localLogPermission);
            String timeStamp = cursor.getString(EditorContract.COL_TIMESTAMP);
            String jsonObject = cursor.getString(EditorContract.COL_COMPONENTS_JSON);
            DocumentData data = new DocumentData(doc_id, title,timeStamp, jsonObject);
            DocList.add(data);
        }
        if(loadFromDatabaseCallBack != null){
            loadFromDatabaseCallBack.OnLoadFinished(DocList);
        }
    }
}