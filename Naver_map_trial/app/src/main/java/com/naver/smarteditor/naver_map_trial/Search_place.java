package com.naver.smarteditor.naver_map_trial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by NAVER on 2017. 5. 15..
 */

public class Search_place extends AppCompatActivity {

    TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_search);
        mTextView = (TextView) findViewById(R.id.textview);

    }
}
