package com.example.naver.prac12;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.FrameLayout;

import java.io.OutputStream;

/**
 * Created by NAVER on 2017. 4. 27..
 */

public class CustomView extends View {
    Context mContext;
    int lastX, lastY;

    Canvas mCanvas;
    Bitmap mBitmap;
    Paint mPaint;


    public CustomView(Context context) {
        super(context);
        mPaint = new Paint();

        mPaint.setColor(Color.WHITE);

        lastX = -1;
        lastY = -1;

    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Bitmap img = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(img);
        canvas.drawColor(Color.WHITE);

        mBitmap = img;
        mCanvas = canvas; //??
    } //새로운 캔버스

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        } //기존에 가지고 있는 메모리안에 있는 비트맵을 이용해 그린다, 파라미터로 받는 canvas는 뭐하는 값이지?
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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
