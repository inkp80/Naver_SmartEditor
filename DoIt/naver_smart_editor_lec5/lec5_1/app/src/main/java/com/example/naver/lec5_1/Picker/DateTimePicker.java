package com.example.naver.lec5_1.Picker;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.example.naver.lec5_1.R;

import java.util.Calendar;

/**
 * Created by NAVER on 2017. 4. 25..
 */

public class DateTimePicker extends LinearLayout {

    public static interface OnDateTimeChangedListener{
        void onDateTimeChanged(DateTimePicker view, int year, int monthOfYear, int dayOfYear, int hourOfday, int minute);
    }

    private OnDateTimeChangedListener listener;
    private DatePicker mDatePicker;
    private TimePicker mTimePicker;
    private CheckBox mEnableTimeCheckBox;


    public DateTimePicker(Context context) {
        super(context);
        init(context);
    }

    public DateTimePicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.picker, this, true);

        Calendar calendar = Calendar.getInstance();

        final int curYear = calendar.get(Calendar.YEAR);
        final int curMonth = calendar.get(Calendar.MONTH);
        final int curDate = calendar.get(Calendar.DAY_OF_MONTH);

        final int curHour = calendar.get(Calendar.HOUR_OF_DAY);
        final int curMinute = calendar.get(Calendar.MINUTE);


        mDatePicker = (DatePicker) findViewById(R.id.picker_date_picker);
        mTimePicker = (TimePicker) findViewById(R.id.picker_time_picker);
        mEnableTimeCheckBox = (CheckBox) findViewById(R.id.picker_checkbox_enable_time);

        mDatePicker.init(curYear, curMonth, curDate, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if(listener != null) {
                    listener.onDateTimeChanged(
                            DateTimePicker.this, year, monthOfYear, dayOfMonth, mTimePicker.getCurrentHour(), mTimePicker.getCurrentMinute());
                }
            }
        });

        mEnableTimeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mTimePicker.setEnabled(isChecked);
                mTimePicker.setVisibility((mEnableTimeCheckBox).isChecked()?
                        View.VISIBLE:View.INVISIBLE);

            }
        });


        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if(listener!=null){
                    listener.onDateTimeChanged(
                            DateTimePicker.this, mDatePicker.getYear(), mDatePicker.getMonth(),
                            mDatePicker.getDayOfMonth(), hourOfDay, minute);
                }
            }
        });

        mTimePicker.setCurrentHour(curHour);
        mTimePicker.setCurrentMinute(curMinute);
        mTimePicker.setEnabled(mEnableTimeCheckBox.isChecked());
        mTimePicker.setVisibility((mEnableTimeCheckBox).isChecked()?
                View.VISIBLE:View.INVISIBLE);
    }

    public void setOnDateTimeChangedListener(OnDateTimeChangedListener dateTimeChangedListener){
        this.listener = dateTimeChangedListener;
    }

    public void updateDateTime(int year, int monthOfYear, int dayOfMonth, int currentHour, int currentMinute){
        mDatePicker.updateDate(year, monthOfYear, dayOfMonth);
        mTimePicker.setCurrentHour(currentHour);
        mTimePicker.setCurrentMinute(currentMinute);
    }

    public int getYear(){
        return mDatePicker.getYear();
    }
    public int getMonth(){
        return mDatePicker.getMonth();
    }
    public int getDate(){
        return mDatePicker.getDayOfMonth();
    }

    public int getHour(){
        return mTimePicker.getCurrentHour();
    }
    public int getMinute(){
        return mTimePicker.getCurrentMinute();
    }
}
