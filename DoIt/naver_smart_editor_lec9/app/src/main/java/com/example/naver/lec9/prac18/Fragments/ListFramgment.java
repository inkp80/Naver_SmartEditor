package com.example.naver.lec9.prac18.Fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.naver.lec9.R;
import com.example.naver.lec9.prac18.Adapters.BookListAdapter;
import com.example.naver.lec9.prac18.Objects.BookItem;

import java.util.ArrayList;

/**
 * Created by NAVER on 2017. 5. 8..
 */

public class ListFramgment extends Fragment {

    public static Cursor mCursor;
    public BookListAdapter mAdapter;

    ListView mBookListView;

    ListFragmentCallbacks mCallbacks;

    public interface ListFragmentCallbacks{
        Cursor passListItemToAdapter(Cursor cursor);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.prac18_main_list_fragment, container, false);
        mBookListView = (ListView) view.findViewById(R.id.listview);
        mAdapter = new BookListAdapter(getContext(), mCursor);
        mBookListView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        if (activity instanceof AddFragment.AddFragmentCallbacks) {
            mCallbacks = (ListFragmentCallbacks) activity;
        }
    }

    public void setCursor(Cursor cursor){
        mCursor = cursor;
        if(mAdapter != null)
            mAdapter.notifyDataSetChanged();

        //TODO: 왜 if문을 안넣으면 에러가 나오는거지??
    }

}
