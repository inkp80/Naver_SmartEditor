package com.naver.smarteditor.lesssmarteditor.data.edit.local.utils;

import android.provider.BaseColumns;

/**
 * Created by NAVER on 2017. 5. 24..
 */

public final class EditorContract {

    public static final int COL_ID = 0;
    public static final int COL_TITLE = 1;
    public static final int COL_TIMESTAMP = 2;
    public static final int COL_COMPONENTS_JSON = 3;

    private EditorContract() {}



    /* Inner class that defines the table contents */

    public static abstract class ComponentEntry implements BaseColumns{
        public static final String TABLE_NAME = "documents";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUNM_COMPONENTS_JSON = "components_json";
    }
}
