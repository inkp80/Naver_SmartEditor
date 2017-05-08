package com.example.naver.lec9.prac18.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.naver.lec9.R;

/**
 * Created by NAVER on 2017. 5. 8..
 */

public class ListFramgment extends Fragment {

    ListView mBookListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.prac18_main_list_fragment, container, false);
    }
}
