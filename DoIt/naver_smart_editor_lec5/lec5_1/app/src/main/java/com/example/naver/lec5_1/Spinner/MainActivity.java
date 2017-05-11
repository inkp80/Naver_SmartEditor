package com.example.naver.lec5_1.Spinner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.naver.lec5_1.R;

import java.util.ArrayList;

/**
 * Created by NAVER on 2017. 4. 25..
 */

public class MainActivity extends AppCompatActivity{
    TextView mTextView;
    String[] items = {"abc", "def", "hij", "klm"};
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spinner_activity_main);

        mTextView = (TextView) findViewById(R.id.spinner_title);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTextView.setText(items[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mTextView.setText("");
            }
        });
    }
}
