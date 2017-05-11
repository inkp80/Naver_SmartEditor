package com.example.naver.lec5_1.CalendarEx;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by NAVER on 2017. 4. 25..
 */

public class MonthItemView extends AppCompatTextView {

    private MonthItem item;

    public MonthItemView(Context context) {
        super(context);
        init();
    }

    public MonthItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        setBackgroundColor(Color.WHITE);
    }

    public MonthItem getItem() {
        return item;
    }

    public void setItem(MonthItem item) {
        this.item = item;

        int day = item.getDayValue();
        if (day != 0) {
            setText(String.valueOf(day));
        } else {
            setText("");
        }
    }
}
