package com.naver.smarteditor.lesssmarteditor.data.edit.local.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.naver.smarteditor.lesssmarteditor.LogController;

/**
 * Created by NAVER on 2017. 5. 24..
 */

public class EditorDbHelper extends SQLiteOpenHelper {

    private final String TAG = "Database Helper";
    private boolean localLogPermission = true;

    public final static String DATABASE_NAME = "LessSmartEditor";
    public final static int DATABASE_VERSION = 1;
    public final static String TABLE_NAME = "documents";

    public EditorDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void onCreate(SQLiteDatabase db) {
        LogController.makeLog(TAG,"creating table [" + TABLE_NAME + "].", localLogPermission);

        try {
            String DROP_SQL = "drop table if exists " + TABLE_NAME;
            db.execSQL(DROP_SQL);
        } catch(Exception ex) {
            LogController.makeLog(TAG, "Exception in DROP_SQL, " + ex, localLogPermission);
        }

        String CREATE_SQL = "create table " + TABLE_NAME + "("
                + " _id integer PRIMARY KEY autoincrement, "
                + EditorContract.ComponentEntry.COLUMN_TITLE + " text, "
                + EditorContract.ComponentEntry.COLUMN_TIMESTAMP + " long, "
                + EditorContract.ComponentEntry.COLUNM_COMPONENTS_JSON + " text)";

        try {
            db.execSQL(CREATE_SQL);
        } catch(Exception ex) {
            LogController.makeLog(TAG, "Exception in CREATE_SQL, "+ex, localLogPermission);
        }

    }

    public void onOpen(SQLiteDatabase db) {
        LogController.makeLog(TAG, "opened database [" + DATABASE_NAME + "].", localLogPermission);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogController.makeLog(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ".", localLogPermission);

    }
}
