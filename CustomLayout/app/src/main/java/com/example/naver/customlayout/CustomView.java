package com.example.naver.customlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * Created by NAVER on 2017. 5. 4..
 */

public class CustomView extends ViewGroup {
    String TAG = "CUSTOM-VIEW";

    public CustomView(Context context) {
        super(context);
        Log.d(TAG, "Constructor");
    }

    public CustomView(Context context, AttributeSet attrs){
        super(context, attrs);
        Log.d(TAG, "Constructor");
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
        Log.d(TAG, "requestLayout()");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure()");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout()");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChagned()");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "onAttachedToWindow()");
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Log.d(TAG, "dispatchDraw()");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw()");
    }


    @Override
    public void invalidate() {
        super.invalidate();
        Log.d(TAG, "Invalidate()");
    }
}
