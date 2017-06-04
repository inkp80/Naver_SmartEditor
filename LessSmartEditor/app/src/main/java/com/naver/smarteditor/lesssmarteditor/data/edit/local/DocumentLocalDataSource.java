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
import java.util.Collections;
import java.util.List;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public class DocumentLocalDataSource implements DocumentDataSource.DocumentLocalDatabase {
    private final String TAG = "DocumentLocalDataSource";
    private boolean localLogPermission = true;
    private EditorDbHelper mDbHelper;
    private SQLiteDatabase db;


    private final int NEW_DOCUMENT = -1;
    private int currentDocumentId = NEW_DOCUMENT;

    List<BaseComponent> mComponents;

    public DocumentLocalDataSource(Context context) {
        mComponents = new ArrayList<>();
        mDbHelper = new EditorDbHelper(context);
        db = mDbHelper.getWritableDatabase();
    }


    @Override
    public void updateDocumentData(List<BaseComponent> documentData, DocumentDataSource.DatabaseUpdateCallback databaseUpdateCallback) {
        LogController.makeLog(TAG, String.valueOf(currentDocumentId), localLogPermission);
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
            LogController.makeLog(TAG, String.valueOf(currentDocumentId), localLogPermission);
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
    private final int TITLE_COMPONENT = 0;
    private void insertLocalDatabase(List<BaseComponent> components) {
        String title = ((TitleComponent) components.get(TITLE_COMPONENT)).getTitle();

        String jsonStr = new Gson().toJson(components);
        LogController.makeLog("save into local", jsonStr, true);


        String query = "INSERT INTO " + EditorContract.ComponentEntry.TABLE_NAME + "(" + EditorContract.ComponentEntry.COLUMN_TITLE + "," + EditorContract.ComponentEntry.COLUMN_TIMESTAMP +
                ", " + EditorContract.ComponentEntry.COLUNM_COMPONENTS_JSON + ") values ('" + title + "', '" + Calendar.getInstance().getTimeInMillis() + "', '" + jsonStr + "');";
        try {
            db.execSQL(query);
            Cursor cursor = db.rawQuery("SELECT * FROM documents ORDER BY _id DESC LIMIT 1;", null);
            cursor.moveToNext();
            int documentId = cursor.getInt(EditorContract.COL_ID);
            LogController.makeLog(TAG, "afterInsert Id" + documentId, localLogPermission);
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

    private void updateLocalDatabase(int docId, List<BaseComponent> components) {
        String title = ((TitleComponent) components.get(TITLE_COMPONENT)).getTitle();
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
        Collections.reverse(DocList);
        return DocList;
    }


    @Override
    public void clearDocumentInfo() {
        currentDocumentId = NEW_DOCUMENT;
    }

    @Override
    public void setDocumentInfo(int documentId) {
        currentDocumentId = documentId;
    }
}