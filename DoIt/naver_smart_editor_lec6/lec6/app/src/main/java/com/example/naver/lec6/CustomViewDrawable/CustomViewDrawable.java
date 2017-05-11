package com.example.naver.lec6.CustomViewDrawable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.example.naver.lec6.R;

/**
 * Created by NAVER on 2017. 4. 26..
 */

public class CustomViewDrawable extends View {
    private ShapeDrawable upperDrawable;
    private ShapeDrawable lowerDrawable;

    public CustomViewDrawable(Context context){
        super(context);

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point sizePoint = new Point();
        display.getSize(sizePoint);
        int width = sizePoint.x;
        int height = sizePoint.y;


        Resources curRes = getResources();
        int blackColor = curRes.getColor(R.color.color01);
        int grayColor = curRes.getColor(R.color.color02);
        int darkGray = curRes.getColor(R.color.color03);

        //
        upperDrawable = new ShapeDrawable();
        RectShape rectangle = new RectShape();
        rectangle.resize(width, height*(2/3));
        upperDrawable.setShape(rectangle);
        upperDrawable.setBounds(0, 0, width, height*2/3);

        LinearGradient gradient = new LinearGradient(0, 0, 0, height*2/3, grayColor, blackColor, Shader.TileMode.CLAMP);

        Paint paint = upperDrawable.getPaint();
        paint.setShader(gradient);

        lowerDrawable = new ShapeDrawable();
        RectShape rectShape2 = new RectShape();
        rectShape2.resize(width, height*1/3);
        lowerDrawable.setShape(rectShape2);
        lowerDrawable.setBounds(0, height*2/3, width, height);

        LinearGradient gradient2 = new LinearGradient(0,0,0,height*1/3, blackColor, darkGray, Shader.TileMode.CLAMP);

        Paint paint2 = lowerDrawable.getPaint();
        paint2.setShader(gradient2);

    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        upperDrawable.draw(canvas);
        lowerDrawable.draw(canvas);

        // Paint
        Paint pathPaint = new Paint();
        pathPaint.setAntiAlias(true);
        pathPaint.setColor(Color.YELLOW);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(16.0F);
        pathPaint.setStrokeCap(Paint.Cap.BUTT);
        pathPaint.setStrokeJoin(Paint.Join.MITER);

        // Path
        Path path = new Path();
        path.moveTo(20, 20);
        path.lineTo(120, 20);
        path.lineTo(160, 90);
        path.lineTo(180, 80);
        path.lineTo(200, 120);

        canvas.drawPath(path, pathPaint);

        pathPaint.setColor(Color.WHITE);
        pathPaint.setStrokeCap(Paint.Cap.ROUND);
        pathPaint.setStrokeJoin(Paint.Join.ROUND);

        path.offset(30, 120);
        canvas.drawPath(path, pathPaint);

        pathPaint.setColor(Color.CYAN);
        pathPaint.setStrokeCap(Paint.Cap.SQUARE);
        pathPaint.setStrokeJoin(Paint.Join.BEVEL);

        path.offset(30, 120);
        canvas.drawPath(path, pathPaint);

    }

}
