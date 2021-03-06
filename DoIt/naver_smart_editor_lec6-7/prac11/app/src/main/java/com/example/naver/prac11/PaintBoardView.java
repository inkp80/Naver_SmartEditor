package com.example.naver.prac11;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.io.OutputStream;

/**
 * Created by NAVER on 2017. 4. 26..
 */

public class PaintBoardView extends View {

    Canvas mCanvas;
    Bitmap mBitmap;
    Paint mPaint;

    int lastX;
    int lastY;

    public PaintBoardView(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(2);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setAntiAlias(true);
        lastX = -1;
        lastY = -1;
    }

    public PaintBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(2);
        mPaint.setStrokeJoin(Paint.Join.ROUND);

        lastX = -1;
        lastY = -1;

    }


    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Bitmap img = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(img);
        canvas.drawColor(Color.BLUE);

        mBitmap = img;
        mCanvas = canvas;
    }

    protected void onDraw(Canvas canvas) {
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }

    public boolean onTouchEvent(MotionEvent event){

        int action = event.getAction();
        int X = (int) event.getX();
        int Y = (int) event.getY();


        switch (action){
            case MotionEvent.ACTION_UP :
                Log.d("TouchState : ", "UP");
                Log.d("UP ( x, y ) =", String.valueOf(lastX)+","+String.valueOf(lastY));
                lastX = -1;
                lastY = -1;
                break;
            case MotionEvent.ACTION_DOWN:
                Log.d("TouchState : ", "DONW");
                Log.d("DOWN ( x, y ) =", String.valueOf(lastX)+","+String.valueOf(lastY));
                // draw line with the coordinates
                if (lastX != -1) {
                    if (X != lastX || Y != lastY) {
                        mCanvas.drawLine(lastX, lastY, X, Y, mPaint);

                    }
                }
                lastX = X;
                lastY = Y;
                break;
            case MotionEvent.ACTION_MOVE :
                Log.d("TouchState : ", "MOV");
                Log.d("MOV ( x, y ) =", String.valueOf(lastX)+","+String.valueOf(lastY));
                if(lastX != -1) {
                    mCanvas.drawLine(lastX,lastY,X,Y,mPaint);
                }
                lastX = X;
                lastY = Y;
                break;
            default:
                break;

        }
        invalidate();

        return true;
    }


    public void setPaintAtts(int colorVal, float strokeWidth){
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setColor(colorVal);
        FrameLayout
    }

    public void setCapStyle(Paint.Cap styleVal){
        mPaint.setStrokeCap(styleVal);
    }

    public boolean Save(OutputStream outstream) {
        try {
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
            invalidate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
