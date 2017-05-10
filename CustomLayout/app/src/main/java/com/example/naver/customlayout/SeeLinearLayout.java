package com.example.naver.customlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * Created by NAVER on 2017. 5. 4..
 */

public class SeeLinearLayout extends LinearLayout {

    String TAG = "LINEAR-LAYOUT";


    public SeeLinearLayout(Context context) {
        super(context);
    }

    public SeeLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void requestLayout() {
        super.requestLayout();
        Log.d(TAG, "requestLayout()");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure(), width : "+String.valueOf(MeasureSpec.getSize(widthMeasureSpec)) +"; height : " + String.valueOf(MeasureSpec.getSize(heightMeasureSpec)) +";");
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
