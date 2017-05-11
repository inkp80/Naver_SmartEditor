package com.example.naver.lec6.CustomViewStyle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by NAVER on 2017. 4. 26..
 */

public class CustomViewStyle extends View{

    private Paint paint;


    public CustomViewStyle(Context context) {
        super(context);
        paint = new Paint();
    }

    public CustomViewStyle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.YELLOW);
        canvas.drawRect(10,10,100,100,paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2.0F);
        paint.setColor(Color.RED);
        canvas.drawRect(10,10,100,100,paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setARGB(128,0,0,255);
        canvas.drawRect(120,10,210,100,paint);

//        DashPathEffect dashPathEffect = new DashPathEffect(new float[]{5,5},1);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(3.0F);
//        paint.setPathEffect(dashPathEffect);
//        paint.setColor(Color.GREEN);
//        canvas.drawRect(120,10,210,100,paint);

        paint = new Paint();

        paint.setColor(Color.MAGENTA);
        canvas.drawCircle(50,160,40,paint);

        paint.setAntiAlias(true);
        canvas.drawCircle(160,160,40,paint);

//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(1);
//        paint.setColor(Color.MAGENTA);
//        paint.setTextSize(30);
//        canvas.drawText("TEXT (STROKE)", 20, 260, paint);
//
//        paint.setStyle(Paint.Style.FILL);
//        paint.setTextSize(30);
//        canvas.drawText("Text (,", 20, 320, paint);
//
//        canvas.clipRect(220, 240, 250, 270, Region.Op.REPLACE);
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(Color.RED);
//        canvas.drawRect(220, 240, 320, 340, paint);


        //------
        // Text #1 Stroke
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(Color.MAGENTA);
        paint.setTextSize(30);
        canvas.drawText("Text (Stroke)", 20, 260, paint);

        // Text #2 Fill
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(30);
        canvas.drawText("Text (채우기)", 20, 320, paint);

        // Clipping
        canvas.clipRect(220, 240, 250, 270, Region.Op.REPLACE);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawRect(220, 240, 320, 340, paint);


//        ---

//        super.onDraw(canvas);
//
//        // Rectangle #1 Stroke
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(Color.RED);
//        canvas.drawRect(10, 10, 100, 100, paint);
//
//        // Rectangle #1 Fill
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(2.0F);
//        paint.setColor(Color.GREEN);
//        canvas.drawRect(10, 10, 100, 100, paint);
//
//        // Rectangle #2 Stroke
//        paint.setStyle(Paint.Style.FILL);
//        paint.setARGB(128, 0, 0, 255);
//        canvas.drawRect(120, 10, 210, 100, paint);
//
//        // Rectangle #2 Fill
//        DashPathEffect dashEffect = new DashPathEffect(new float[]{5,5}, 1);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(3.0F);
//        paint.setPathEffect(dashEffect);
//        paint.setColor(Color.GREEN);
//        canvas.drawRect(120, 10, 210, 100, paint);
//
//        paint = new Paint();
//
//        // Circle #1 Stroke
//        paint.setColor(Color.MAGENTA);
//        canvas.drawCircle(50, 160, 40, paint);
//
//        // Circle #2 Stroke
//        paint.setAntiAlias(true);
//        canvas.drawCircle(160, 160, 40, paint);
//
//        // Text #1 Stroke
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(1);
//        paint.setColor(Color.MAGENTA);
//        paint.setTextSize(30);
//        canvas.drawText("Text (Stroke)", 20, 260, paint);
//
//        // Text #2 Fill
//        paint.setStyle(Paint.Style.FILL);
//        paint.setTextSize(30);
//        canvas.drawText("Text (채우기)", 20, 320, paint);
//
//        // Clipping
//        canvas.clipRect(220, 240, 250, 270, Region.Op.REPLACE);
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(Color.RED);
//        canvas.drawRect(220, 240, 320, 340, paint);
    }

}
