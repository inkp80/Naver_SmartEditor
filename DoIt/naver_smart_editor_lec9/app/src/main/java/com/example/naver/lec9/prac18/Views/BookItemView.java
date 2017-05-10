package com.example.naver.lec9.prac18.Views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.naver.lec9.R;

/**
 * Created by NAVER on 2017. 5. 8..
 */

public class BookItemView extends LinearLayout{

    TextView mTitle;
    TextView mAuthor;
    ImageView mBookImg;

    public BookItemView(Context context) {
        super(context);
        init(context);
    }

    public BookItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.prac18_bookitem, this, true);

        mTitle = (TextView) findViewById(R.id.tv_item_title);
        mAuthor = (TextView) findViewById(R.id.tv_item_author);
        mBookImg = (ImageView) findViewById(R.id.img_item_src);
    }


    public void setTitle(String title){
        mTitle.setText(title);
    }

    public void setmAuthor(String author){
        mAuthor.setText(author);
    }

    public void setmBookImg(Drawable drawable){
        mBookImg.setImageDrawable(drawable);
    }

}
