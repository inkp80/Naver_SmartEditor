package com.example.naver.prac11;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    PaintBoardView myView;

    Button mColorSelect;
    Button mStrokeSelect;
    int mPaintColor = Color.BLACK;
    int mStrokeType = 2;


    RadioButton mCapStyle_BUTT;
    RadioButton mCapStyle_ROUND;
    RadioButton mCapStyle_SQUARE;
    Paint.Cap mCapStyle = Paint.Cap.BUTT;


    Button mClearAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCapStyle_BUTT = (RadioButton) findViewById(R.id.radio_butt);
        mCapStyle_ROUND = (RadioButton) findViewById(R.id.radio_round);
        mCapStyle_SQUARE = (RadioButton) findViewById(R.id.radio_square);

        myView = new PaintBoardView(this);
        myView.setCapStyle(mCapStyle);


        final LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.board_place);

        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT);


        myView.setLayoutParams(params);
        mLinearLayout.addView(myView);

        ColorSelectDialog.listener = new OnColorSelectedListener() {
            @Override
            public void onColorSelected(int color) {
                mPaintColor = color;
                myView.setPaintAtts(mPaintColor, mStrokeType);
            }
        };

        StrokeSelectDialog.listener = new OnStrokeSelectedListener() {
            @Override
            public void onStrokeSelected(int stroke) {
                mStrokeType = stroke;
                myView.setPaintAtts(mPaintColor, mStrokeType);
            }
        };

        mColorSelect = (Button) findViewById(R.id.bt_sel_color);
        mColorSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ColorSelectDialog.class);
                startActivity(intent);
            }
        });

        mStrokeSelect = (Button) findViewById(R.id.bt_sel_stroke);
        mStrokeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StrokeSelectDialog.class);
                startActivity(intent);
            }
        });

        mCapStyle_SQUARE.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mCapStyle = Paint.Cap.SQUARE;
                    myView.setCapStyle(mCapStyle);
                }
            }
        });

        mCapStyle_ROUND.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mCapStyle = Paint.Cap.ROUND;
                    myView.setCapStyle(mCapStyle);
                }
            }
        });

        mCapStyle_BUTT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mCapStyle = Paint.Cap.BUTT;
                    myView.setCapStyle(mCapStyle);
                }
            }
        });

        mClearAll = (Button) findViewById(R.id.bt_sel_clear);
        mClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLinearLayout.removeView(myView);
                myView = new PaintBoardView(getApplicationContext());
                myView.setLayoutParams(params);
                mLinearLayout.addView(myView);
            }
        });
    }
}
