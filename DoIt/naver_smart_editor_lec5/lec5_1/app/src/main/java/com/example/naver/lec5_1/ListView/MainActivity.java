package com.example.naver.lec5_1.ListView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.naver.lec5_1.R;

import java.util.ArrayList;

/**
 * Created by NAVER on 2017. 4. 25..
 */

public class MainActivity extends AppCompatActivity {
    ListView listView;
    SingerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_activity_main);

        listView = (ListView) findViewById(R.id.listview);

        adapter = new SingerAdapter();
        adapter.addItem(new SingerItem("소녀시대", "000-0000-0000", 20, R.drawable.singer));
        adapter.addItem(new SingerItem("r","0000021313",22, R.drawable.singer5));

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SingerItem item = (SingerItem) adapter.getItem(position);
                Toast.makeText(MainActivity.this, "hee", Toast.LENGTH_SHORT).show();
            }
        });
    }


    class SingerAdapter extends BaseAdapter {
        ArrayList<SingerItem> items = new ArrayList<SingerItem>();

        @Override
        public int getCount(){
            return items.size();
        }

        public void addItem(SingerItem item){
            items.add(item);
        }

        @Override
        public Object getItem(int pos){
            return items.get(pos);
        }

        @Override
        public long getItemId(int pos){
            return pos;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup viewGroup){
            SingerItemView view = new SingerItemView(getApplicationContext());
            SingerItem item = items.get(pos);
            view.setName(item.getName());
            view.setMobile(item.getMobile());
            view.setImage(item.getResId());
            return view;
        }
    }
}
