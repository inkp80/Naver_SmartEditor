package com.example.naver.lec5_1.GridView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.naver.lec5_1.ListView.SingerItem;

import java.util.ArrayList;

/**
 * Created by NAVER on 2017. 4. 25..
 */

public class SingleAdatper extends BaseAdapter {
    ArrayList<SingleItem> items = new ArrayList<SingleItem>();
    Context mContext;
    public SingleAdatper(Context context){
        mContext=context;
    }

    public void addItem(SingleItem item){
        items.add(item);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SingleItemView view = new SingleItemView(mContext);
        SingleItem item = items.get(position);
        view.setName(item.getName());
        view.setMobile(item.getMobile());
        view.setImage(item.getResId());
        return view;
    }
}
