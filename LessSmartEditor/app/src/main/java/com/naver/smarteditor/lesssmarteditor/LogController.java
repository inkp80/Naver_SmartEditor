package com.naver.smarteditor.lesssmarteditor;

import android.util.Log;

/**
 * Created by NAVER on 2017. 6. 1..
 */
public class LogController {
    private static boolean globalLogPermission = true;

    public static void setGlobalPermission(boolean state) {
        LogController.globalLogPermission = state;
    }

    private static boolean isGlobalPermissionVaild() {
        if (globalLogPermission) {
            return true;
        } else {
            return false;
        }
    }

    public static void makeLog(String TAG, String msg, boolean localPermission) {
        if (isGlobalPermissionVaild()) {
            if (localPermission)
                Log.d(TAG, msg);
            else
                return;
        } else {
            return;
        }
    }
}
