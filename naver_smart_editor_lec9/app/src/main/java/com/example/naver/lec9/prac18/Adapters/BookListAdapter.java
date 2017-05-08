package com.example.naver.lec9.prac18.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.naver.lec9.prac18.Objects.BookItem;
import com.example.naver.lec9.prac18.Views.BookItemView;

import java.util.ArrayList;

/**
 * Created by NAVER on 2017. 5. 8..
 */

public class BookListAdapter extends BaseAdapter{

    ArrayList<BookItem> bookItems;
    Context mContext;

    public BookListAdapter(Context context){
        mContext = context;
    }

    public void addItem(BookItem bookItem){
        bookItems.add(bookItem);
    }

    @Override
    public int getCount() {
        return bookItems.size();
    }

    @Override
    public Object getItem(int position) {
        return bookItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BookItemView view = new BookItemView(mContext);
        BookItem item = bookItems.get(position);

        view.setTitle(item.getTitle());
        view.setmAuthor(item.getAuthor());
        view.setmBookImg(item.getBookImg());
        return view;
    }
}
