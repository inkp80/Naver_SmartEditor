package com.example.naver.lec5_1.GridView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.naver.lec5_1.R;

/**
 * Created by NAVER on 2017. 4. 25..
 */

public class SingleItemView extends LinearLayout{
    TextView mTv1;
    TextView mTv2;
    TextView mTv3;

    ImageView mImageView;


    public SingleItemView(Context context) {
        super(context);
        init(context);
    }

    public SingleItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }



    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.listview_item, this, true);

        mTv1 = (TextView) findViewById(R.id.list_item_tv);
        mTv2 = (TextView) findViewById(R.id.list_item_tv2);
        mTv3 = (TextView) findViewById(R.id.list_item_tv3);
        mImageView = (ImageView) findViewById(R.id.list_item_img);
    }

    public void setAge(int age) {mTv3.setText(String.valueOf(age));}
    public void setName(String name){
        mTv1.setText(name);
    }
    public void setMobile(String mobile){
        mTv2.setText(mobile);
    }
    public void setImage(int resId){
        mImageView.setImageResource(resId);
    }
}
