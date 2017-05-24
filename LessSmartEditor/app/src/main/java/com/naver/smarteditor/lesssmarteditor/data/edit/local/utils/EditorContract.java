package com.naver.smarteditor.lesssmarteditor.data.edit.local.utils;

import android.provider.BaseColumns;

/**
 * Created by NAVER on 2017. 5. 24..
 */

public final class EditorContract {

    private EditorContract() {}

    /* Inner class that defines the table contents */

    public static abstract class ComponentEntry implements BaseColumns{
        public static final String TABLE_NAME = "documents";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUNM_DESCRIPTION_JSON = "components_json";
    }
}
