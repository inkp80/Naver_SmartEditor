package com.example.naver.lec6.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * Created by NAVER on 2017. 4. 26..
 */

public class CustomView extends View {
    private Paint paint;


    public CustomView(Context context) {
        super(context);
        paint = new Paint();
        paint.setColor(Color.RED);
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawRect(100,200,300,300,paint);
    }

    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            Toast.makeText(super.getContext(), "MotionEvent.ACTION_DOWN", Toast.LENGTH_SHORT).show();
        }
        return super.onTouchEvent(event);
    }



    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
