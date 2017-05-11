package com.example.naver.lec5_1.CalendarEx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.naver.lec5_1.R;

import java.util.Calendar;

/**
 * Created by NAVER on 2017. 4. 25..
 */

public class MainActivity extends AppCompatActivity{
    GridView monthView;

    CalendarAdapter monthViewAdapter;

    TextView monthText;

    int curYear;
    int curMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_body);

        // 월별 캘린더 뷰 객체 참조
        monthView = (GridView) findViewById(R.id.cal_month_body);
        monthViewAdapter = new CalendarAdapter(getApplicationContext());
        monthView.setAdapter(monthViewAdapter);

        // 리스너 설정
        monthView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // 현재 선택한 일자 정보 표시
                MonthItem curItem = (MonthItem) monthViewAdapter.getItem(position);
                int day = curItem.getDayValue();

                Log.d("MainActivity", "Selected : " + day);
            }
        });


        monthText = (TextView) findViewById(R.id.cal_month_text);
        setMonthText();

        // 이전 월로 넘어가는 이벤트 처리
        Button monthPrevious = (Button) findViewById(R.id.cal_prev_month);
        monthPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                monthViewAdapter.setPreviousMonth();
                monthViewAdapter.notifyDataSetChanged();

                setMonthText();
            }
        });

        // 다음 월로 넘어가는 이벤트 처리
        Button monthNext = (Button) findViewById(R.id.cal_next_month);
        monthNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                monthViewAdapter.setNextMonth();
                monthViewAdapter.notifyDataSetChanged();

                setMonthText();
            }
        });

    }

    private void setMonthText() {
        curYear = monthViewAdapter.getCurYear();
        curMonth = monthViewAdapter.getCurMonth();

        monthText.setText(curYear + "년 " + (curMonth + 1) + "월");
    }

}
