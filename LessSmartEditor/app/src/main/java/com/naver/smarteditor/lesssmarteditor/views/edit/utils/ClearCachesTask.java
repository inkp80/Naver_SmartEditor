package com.naver.smarteditor.lesssmarteditor.views.edit.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;
import com.naver.smarteditor.lesssmarteditor.LogController;

/**
 * Created by NAVER on 2017. 5. 30..
 */

public class ClearCachesTask extends AsyncTask<Void, Void, Void> {
    private final String TAG = "ClearCachesTask";
    private boolean localLogPermission = true;

    private final Context context;
    private final boolean clearMemory;
    private final boolean clearDisk;

    public ClearCachesTask(Context context, boolean clearMemory, boolean clearDisk) {
        this.context = context.getApplicationContext();
        this.clearMemory = clearMemory;
        this.clearDisk = clearDisk;
    }

    @Override protected void onPreExecute() {
        if (clearMemory) {
            LogController.makeLog(TAG, "Clearing memory cache", localLogPermission);
            Glide.get(context).clearMemory();
            LogController.makeLog(TAG, "Clearing memory cache finished", localLogPermission);
        }
    }

    @Override protected Void doInBackground(Void[] params) {
        if (clearDisk) {
            LogController.makeLog(TAG, "Clearing disk cache", localLogPermission);
            Glide.get(context).clearDiskCache();
            LogController.makeLog(TAG, "Clearing disk cache finished", localLogPermission);
        }
        return null;
    }

    @Override protected void onPostExecute(Void result) {
        //if needed, post result to UI
    }

    private String getClearMessage() {
        if (clearMemory && clearDisk) {
            return "Caches";
        } else if (clearMemory) {
            return "Memory Cache";
        } else if (clearDisk) {
            return "Disk Cache";
        } else {
            return "Nothing";
        }
    }
}