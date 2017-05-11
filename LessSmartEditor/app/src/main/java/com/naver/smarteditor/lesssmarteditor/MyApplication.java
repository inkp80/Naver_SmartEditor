package com.naver.smarteditor.lesssmarteditor;

import android.app.Application;
import android.util.Log;

/**
 * Created by NAVER on 2017. 5. 11..
 */

public class MyApplication extends Application {
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
