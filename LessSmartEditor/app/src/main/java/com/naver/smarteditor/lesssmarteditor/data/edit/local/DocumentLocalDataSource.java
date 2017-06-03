package com.naver.smarteditor.lesssmarteditor.data.edit.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

import com.naver.smarteditor.lesssmarteditor.LogController;
import com.naver.smarteditor.lesssmarteditor.data.Document;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.component.TitleComponent;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.utils.EditorContract;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.utils.EditorDbHelper;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.utils.WrongComponentException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public class DocumentLocalDataSource implements DocumentDataSource.DocumentLocalDatabase {
    private final String TAG = "DocumentLocalDataSource";
    private boolean localLogPermission = true;
    private EditorDbHelper mDbHelper;
    private SQLiteDatabase db;


    private final String NEW_DOCUMENT = "new_document";
    private String currentDocumentId = NEW_DOCUMENT;

    List<BaseComponent> mComponents;

    public DocumentLocalDataSource(Context context) {
        mComponents = new ArrayList<>();
        mDbHelper = new EditorDbHelper(context);
        db = mDbHelper.getWritableDatabase();
    }


    @Override
    public void updateDocumentData(List<BaseComponent> documentData, DocumentDataSource.DatabaseUpdateCallback databaseUpdateCallback) {
        if (currentDocumentId == NEW_DOCUMENT) {
            if (databaseUpdateCallback != null) {
                try {
                    insertLocalDatabase(documentData);
                    databaseUpdateCallback.OnSuccess();
                } catch (Exception e){
                    databaseUpdateCallback.OnFail();
                }
            }
        } else {
            if(databaseUpdateCallback != null) {
                try{
                    updateLocalDatabase(currentDocumentId, documentData);
                    databaseUpdateCallback.OnSuccess();
                } catch (Exception e){
                    databaseUpdateCallback.OnFail();
                }
            }
        }
    }

    @Override
    public void deleteDocumentData(int documentId, DocumentDataSource.DatabaseUpdateCallback databaseUpdateCallback) {
        if(databaseUpdateCallback != null){
            try{
                deleteDocument(documentId);
                databaseUpdateCallback.OnSuccess();
            } catch (Exception e){
                databaseUpdateCallback.OnFail();
            }
        }
    }


    @Override
    public void readDocumentData(DocumentDataSource.DatabaseReadCallback databaseReadCallback) {
        if(databaseReadCallback != null){
            try {
                List<Document> documentList = readLocalDatabase();
                databaseReadCallback.OnSuccess(documentList);
            } catch (Exception e){
                databaseReadCallback.OnFail();
            }
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////
    //DB CRUD

    private void insertLocalDatabase(List<BaseComponent> components) {

        String title = "";

        try {
            title = ((TitleComponent) components.get(0)).getTitle();
            throw new WrongComponentException();
        } catch (Exception e) {
            LogController.makeLog(TAG, e.toString(), localLogPermission);
        }

        String jsonStr = new Gson().toJson(components);

        String query = "INSERT INTO " + EditorContract.ComponentEntry.TABLE_NAME + "(" + EditorContract.ComponentEntry.COLUMN_TITLE + "," + EditorContract.ComponentEntry.COLUMN_TIMESTAMP +
                ", " + EditorContract.ComponentEntry.COLUNM_COMPONENTS_JSON + ") values ('" + title + "', '" + Calendar.getInstance().getTimeInMillis() + "', '" + jsonStr + "');";
        try {
            db.execSQL(query);
            Cursor cursor = db.rawQuery("SELECT * FROM documents ORDER BY _id DESC LIMIT 1;", null);
            cursor.moveToNext();
            String documentId = cursor.getString(EditorContract.COL_ID);
            currentDocumentId = documentId;
            LogController.makeLog(TAG, "Database processing was Successfully done", localLogPermission);
        } catch (Exception e) {
            LogController.makeLog(TAG, "Error : Database insert, " + e, localLogPermission);
        }
    }


    //Document List를 넘긴다.
    private List<Document> readLocalDatabase() {
        String querySelete = "SELECT * " +
                "FROM " + EditorContract.ComponentEntry.TABLE_NAME
                + " order by (" + EditorContract.ComponentEntry.COLUMN_TIMESTAMP + ")";

        Cursor cursor = db.rawQuery(querySelete, null);
        return converCursorToList(cursor);
    }

    private void deleteDocument(int docId) {
        String query = "DELETE FROM " + EditorContract.ComponentEntry.TABLE_NAME + " WHERE _id ='" + String.valueOf(docId) + "'";
        try {
            db.execSQL(query);
        } catch (Exception e) {
            LogController.makeLog(TAG, "Error : Database delete", localLogPermission);
        }
    }

    private void updateLocalDatabase(String docId, List<BaseComponent> components) {
        String title = "";
        String jsonStr = new Gson().toJson(components);
        String query = "UPDATE " + EditorContract.ComponentEntry.TABLE_NAME
                + " SET " + EditorContract.ComponentEntry.COLUMN_TITLE + "='" + title + "'," + EditorContract.ComponentEntry.COLUMN_TIMESTAMP + "='" + Calendar.getInstance().getTimeInMillis() + "',"
                + EditorContract.ComponentEntry.COLUNM_COMPONENTS_JSON + "='" + jsonStr + "' where _id = '" + docId + "';";

        try {
            db.execSQL(query);
        } catch (Exception e) {
            LogController.makeLog(TAG, "DB ERROR :" + e, localLogPermission);
            throw e;
        }
    }

    private List<Document> converCursorToList(Cursor cursor) {
        List<Document> DocList = new ArrayList<>();

        while (cursor.moveToNext()) {
            int _id = cursor.getInt(EditorContract.COL_ID);
            String title = cursor.getString(EditorContract.COL_TITLE);
            String timeStamp = cursor.getString(EditorContract.COL_TIMESTAMP);
            String jsonObject = cursor.getString(EditorContract.COL_COMPONENTS_JSON);
            Document data = new Document(_id, title, timeStamp, jsonObject);
            DocList.add(data);
        }
        return DocList;
    }


}