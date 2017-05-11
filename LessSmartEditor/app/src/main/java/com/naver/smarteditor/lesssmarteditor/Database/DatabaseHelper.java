package com.naver.smarteditor.lesssmarteditor.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by NAVER on 2017. 5. 11..
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    final String TAG = "Database Helper";
    public final static String DATABASE_NAME = "LessSmartEditor";
    public final static int DATABASE_VERSION = 1;
    public final static String TABLE_NAME = "documents";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG,"creating table [" + TABLE_NAME + "].");

        try {
            String DROP_SQL = "drop table if exists " + TABLE_NAME;
            db.execSQL(DROP_SQL);
        } catch(Exception ex) {
            Log.e(TAG, "Exception in DROP_SQL", ex);
        }

        String CREATE_SQL = "create table " + TABLE_NAME + "("
                + " _id integer PRIMARY KEY autoincrement, "
                + " title text, "
                + " author text, "
                + " description text, "
                + " img_resID long)";

        try {
            db.execSQL(CREATE_SQL);
        } catch(Exception ex) {
            Log.e(TAG, "Exception in CREATE_SQL", ex);
        }

    }

    public void onOpen(SQLiteDatabase db) {
        Log.d(TAG, "opened database [" + DATABASE_NAME + "].");

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ".");

    }
}
