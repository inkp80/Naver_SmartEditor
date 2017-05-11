package com.example.naver.lec6.SurfaceView;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by NAVER on 2017. 4. 26..
 */

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder mHolder;

    public MySurfaceView(Context context){
        super(context);
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h){

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
