package com.example.naver.lec5_1.prac10;

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

public class ItemView extends LinearLayout {

    int mResId;
    TextView mTextName;
    TextView mTextPrice;
    TextView mTextRemarks;
    ImageView mImageSection;


    public ItemView(Context context) {
        super(context);
        init(context);
    }

    public ItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.prac10_item_view, this, true);

        mTextName = (TextView) findViewById(R.id.prac10_item_name);
        mTextPrice = (TextView) findViewById(R.id.prac10_item_price);
        mTextRemarks = (TextView) findViewById(R.id.prac10_item_remark);
        mImageSection = (ImageView) findViewById(R.id.prac10_imgview);
    }

    public void setName(String name){
        mTextName.setText(name);
    }
    public void setPrice(String price){
        mTextPrice.setText(price);
    }
    public void setRemarks(String remarks){
        mTextRemarks.setText(remarks);
    }
    public void setImage(int resId){
        mImageSection.setImageResource(resId);
    }
}
