package com.naver.smarteditor.lesssmarteditor;

import android.app.Application;
import android.util.Log;

/**
 * Created by NAVER on 2017. 5. 11..
 */

public class MyApplication extends Application {
    //CODE NUMBERS (REQ, MODE, ... etc)

    public static final int REQ_UPDATE_DOCUMENT = 101;
    public static final int REQ_ADD_DOCUMENT = 102;

    public static final int REQ_MOV2_GALLERY = 104;
    public static final int REQ_MOV2_SEARCH_PLACE = 105;


    public static final int RETROFIT_FAIL400 = 400;
    public static final int RETROFIT_SUCCESS = 200;

    public static final int EDIT_DOCUMENT_MODE = 13;
    public static final int NEW_DOCUMENT_MODE = 11;


    //INTENT KEY STRING
    public static final String MAPINFO_PARCEL = "mapinfoParcel";
    public static final String DOCUMENT_PARCEL = "documentParcel";

    //EDITOR MODE
    public static final String EDITOR_MODE = "mode_flag";


    //COMPONENT MENU ID
    public static final int COMPONENT_MENU_DELETE = 19;
    public static final int COMPONENT_MENU_CANCEL = 21;


}
