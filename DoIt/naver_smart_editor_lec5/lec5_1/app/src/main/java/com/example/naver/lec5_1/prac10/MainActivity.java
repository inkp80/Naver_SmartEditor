package com.example.naver.lec5_1.prac10;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.naver.lec5_1.R;

/**
 * Created by NAVER on 2017. 4. 25..
 */

public class MainActivity extends AppCompatActivity {

    GridView mGridView;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanteState){
        super.onCreate(savedInstanteState);
        setContentView(R.layout.prac10_activitiy_main);

        mGridView = (GridView) findViewById(R.id.prac10_main_gridview);
        adapter = new Adapter(getApplicationContext());


        ItemBuilder itemBuilder = new ItemBuilder();
        ItemObject item = itemBuilder.setName("빌더 패턴?!").setPrice("123$").setRemarks("sales").setResId(R.drawable.singer5).Build();
        adapter.addItem(item);
        item = itemBuilder.setName("asdb").setPrice("123123").setResId(R.drawable.singer3).Build();
        adapter.addItem(item);

//        adapter.addItem(new ItemObject(R.drawable.singer, "ab0", 123, "aaa1"));
//        adapter.addItem(new ItemObject(R.drawable.singer2, "ab1", 1234, "aaa2"));
//        adapter.addItem(new ItemObject(R.drawable.singer3, "ab2", 1235, "aaa3"));
//        adapter.addItem(new ItemObject(R.drawable.singer4, "ab3", 1236, "aaa4"));

        mGridView.setAdapter(adapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemObject item = (ItemObject) adapter.getItem(position);
                Toast.makeText(MainActivity.this, "상품명 : "+item.getName() + ", 가격 : "+item.getPrice(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
