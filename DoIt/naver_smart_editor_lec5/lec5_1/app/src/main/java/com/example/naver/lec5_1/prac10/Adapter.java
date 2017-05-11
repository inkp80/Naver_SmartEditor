package com.example.naver.lec5_1.prac10;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by NAVER on 2017. 4. 25..
 */

public class Adapter extends BaseAdapter {

    ArrayList<ItemObject> items = new ArrayList<ItemObject>();
    Context mContext;

    public Adapter(Context context){
        mContext = context;
    }
    public void addItem(ItemObject obj){
        items.add(obj);
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
        ItemView view = new ItemView(mContext);
        ItemObject item = items.get(position);
        view.setName(item.getName());
        view.setPrice(item.getPrice());
        view.setRemarks(item.getRemarks());
        view.setImage(item.getResId());
        return view;
    }

}
