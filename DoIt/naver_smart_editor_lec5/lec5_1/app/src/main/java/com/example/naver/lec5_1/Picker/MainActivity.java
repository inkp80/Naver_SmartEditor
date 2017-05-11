package com.example.naver.lec5_1.Picker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.naver.lec5_1.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by NAVER on 2017. 4. 25..
 */

public class MainActivity extends AppCompatActivity {

    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");

    TextView mTextView;
    DateTimePicker mPicker;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picker_activity_main);

        mTextView = (TextView) findViewById(R.id.picker_show_datetime);
        mPicker = (DateTimePicker) findViewById(R.id.picker_picker_body);
        mPicker.setOnDateTimeChangedListener(new DateTimePicker.OnDateTimeChangedListener() {
            @Override
            public void onDateTimeChanged(DateTimePicker view, int year, int monthOfYear, int dayOfYear, int hourOfday, int minute) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfYear, hourOfday, minute);
                mTextView.setText(dateFormat.format(calendar.getTime()));
            }
        });

        Calendar calendar = Calendar.getInstance();
        calendar.set(mPicker.getYear(), mPicker.getMonth(), mPicker.getDate(), mPicker.getHour(), mPicker.getMinute());
        mTextView.setText(dateFormat.format(calendar.getTime()));
    }
}
