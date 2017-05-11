package com.example.naver.lec8.RSSFreeder;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.BundleCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.example.naver.lec8.R;

import java.util.ArrayList;

/**
 * Created by NAVER on 2017. 4. 28..
 */

public class MainActivity extends AppCompatActivity {


    private static String rssUrl = "http://api.sbs.co.kr/xml/news/rss.jsp?pmDiv=entertainment";

    ProgressDialog progressDialog;
    Handler handler = new Handler();

    RSSListView list;
    RSSListAdapter adpater;
    ArrayList<RSSNewsItem> newsItemArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feeder_main_activity);

        ViewGroup.LayoutParams params
                = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        list.setAdapter(adpater);


    }
}