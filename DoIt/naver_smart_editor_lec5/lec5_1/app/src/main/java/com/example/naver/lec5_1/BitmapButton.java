package com.example.naver.lec5_1;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by NAVER on 2017. 4. 25..
 */

public class BitmapButton extends AppCompatButton {
    int iconNormal = R.drawable.bitmap_button_normal;
    int iconClicked = R.drawable.bitmap_button_clicked;

    public static int STATUS_NORMAL = 0;
    public static int STATUS_CLICKED = 1;

    int iconStatus = STATUS_NORMAL;
    public BitmapButton(Context context) {
        super(context);
        init();
    }

    public BitmapButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

//    public BitmapButton(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }

    public void init(){
        setBackgroundResource(iconNormal);

        int defaultTextColor = Color.WHITE;
        float defaultTextSize = getResources().getDimension(R.dimen.text_size);
        Typeface defaultTpyeface = Typeface.DEFAULT_BOLD;

        setTextColor(defaultTextColor);
        setTextSize(defaultTextSize);
        setTypeface(defaultTpyeface);
    }

    public void setIcon(int iconNormal, int iconClicked){
        this.iconNormal = iconNormal;
        this.iconClicked = iconClicked;
    }

    public boolean onTouchEvent(MotionEvent event){
        super.onTouchEvent(event);

        int action = event.getAction();

        switch(action){
            case MotionEvent.ACTION_DOWN:
                setBackgroundResource(this.iconClicked);
                iconStatus = STATUS_CLICKED;
                break;
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                setBackgroundResource(this.iconNormal);
                iconStatus = STATUS_NORMAL;
                break;
        }
        invalidate();

        return true;
    }
}
