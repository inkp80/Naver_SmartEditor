package com.example.naver.prac11;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    Button mColorSelect;
    Button mStrokeSelect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PaintBoardView myView = new PaintBoardView(this);


        LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.board_place);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT);


        myView.setLayoutParams(params);
        mLinearLayout.addView(myView);

        mColorSelect = (Button) findViewById(R.id.bt_sel_color);
        mColorSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ColorSelectDialog.class);
                startActivity(intent);
            }
        });

    }
}
