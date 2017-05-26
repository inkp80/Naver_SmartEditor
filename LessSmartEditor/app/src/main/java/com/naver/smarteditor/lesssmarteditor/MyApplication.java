package com.naver.smarteditor.lesssmarteditor;

import android.app.Application;
import android.util.Log;

/**
 * Created by NAVER on 2017. 5. 11..
 */

public class MyApplication extends Application {
    //CODE NUMBERS (REQ, MODE, ... etc)

    public static final int REQ_CODE_UPDATE = 101;
    public static final int REQ_ADD_DOCUMENT = 102;
    public static final int REQ_EDIT_DOCUMENT = 103;

    public static final int REQ_CODE_MOV2_GALLERY = 104;
    public static final int REQ_CODE_MOV2_SEARCH_PLACE = 105;


    public static final int RETROFIT_FAIL400 = 400;

    public static final int EDIT_MODE = 13;
    public static final int ADD_MODE = 11;


    //INTENT KEY STRING
    public static final String MAPINFO_PARCEL = "mapinfoParcel";
    public static final String DOCUMENT_PARCEL = "documentParcel";

    public static final String MODE_FLAG = "mode_flag";

    private static boolean globalLogPermission = true;

    public static class LogController{
        public static void setGlobalPermission(boolean state){
            MyApplication.globalLogPermission = state;
        }

        public static boolean isGlobalPermissionVaild(){
            if(globalLogPermission){
                return true;
            } else {
                return false;
            }
        }

        public static void makeLog(String TAG, String msg, boolean localPermission){
            if(isGlobalPermissionVaild()){
                if(localPermission)
                    Log.d(TAG, msg);
                else
                    return;
            } else {
                return;
            }
        }
    }
}
