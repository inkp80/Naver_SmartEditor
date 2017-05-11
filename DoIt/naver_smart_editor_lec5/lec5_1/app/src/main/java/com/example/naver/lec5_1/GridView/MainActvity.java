package com.example.naver.lec5_1.GridView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.naver.lec5_1.ListView.SingerItem;
import com.example.naver.lec5_1.R;

/**
 * Created by NAVER on 2017. 4. 25..
 */

public class MainActvity extends AppCompatActivity {

    EditText mEditText;
    GridView mGridView;
    SingleAdatper adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview_activitiy_main);

        mGridView = (GridView) findViewById(R.id.gridview_gridview);
        adapter = new SingleAdatper(getApplicationContext());

        adapter.addItem(new SingleItem("abc", "123-123-123", 20, R.drawable.singer2));
        adapter.addItem(new SingleItem("cba", "123-123-123", 21, R.drawable.singer));
        adapter.addItem(new SingleItem("def", "123-123-123", 22, R.drawable.singer3));
        adapter.addItem(new SingleItem("ght", "123-123-123", 23, R.drawable.singer5));

        mGridView.setAdapter(adapter);
        mEditText = (EditText) findViewById(R.id.gridview_edit_text);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SingleItem item = (SingleItem) adapter.getItem(position);
                Toast.makeText(MainActvity.this, item.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}



