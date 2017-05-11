package com.example.naver.lec8.RSSFreeder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.naver.lec8.R;

/**
 * Created by NAVER on 2017. 4. 28..
 */

public class RSSNewsItemView extends LinearLayout{


    private ImageView mIcon;
    private TextView mText01;
    private TextView mText02;
    private TextView mText03;
    private WebView mWebView;




    public RSSNewsItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RSSNewsItemView(Context context, RSSNewsItem item) {
        super(context);
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.feeder_listitem, this, true);

        mIcon = (ImageView) findViewById(R.id.img_data_item01);
        mIcon.setImageDrawable(item.getIcon());

        mText01 = (TextView) findViewById(R.id.tv_data_item01);
        mText01.setText(item.getTitle());

        mText02 = (TextView) findViewById(R.id.tv_data_item02);
        mText02.setText(item.getTitle());

        mText03 = (TextView) findViewById(R.id.tv_data_item03);
        String category = item.getCategory();

        if(category != null){
            mText03.setText(category);
        }

        mWebView = (WebView) findViewById(R.id.wv_data_item01);
        setTextToWebView(item.getDescription());
    }



    public void setText(int index, String data){
        switch (index){
            case 0 :
                mText01.setText(data);
                break;
            case 1 :
                mText02.setText(data);
                break;
            case 2 :
                mText03.setText(data);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }


    public void setTextToWebView(String msg){

        String outData = msg;

        outData = outData.replace("\"//", "\"http://");

        mWebView.loadDataWithBaseURL("http://localhost/", outData, "text/html", "utf-8", null);
    }

    public void setmIcon(Drawable drawable){
        mIcon.setImageDrawable(drawable);
    }
}
