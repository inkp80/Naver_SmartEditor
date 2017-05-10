package com.example.naver.lec9.prac18.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.naver.lec9.R;
import com.example.naver.lec9.prac18.Objects.BookItem;
import com.example.naver.lec9.prac18.Views.BookItemView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by NAVER on 2017. 5. 8..
 */

public class BookListAdapter extends CursorAdapter{

    final int TITLE = 1;
    final int AUTHOR = 2;
    final int DESCRIPTION = 3;
    final int IMG_RESOURCE = 4;


    public BookListAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.prac18_bookitem, parent, false);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        final ImageView imageView = (ImageView) view.findViewById(R.id.img_item_src);
        final TextView titleText = (TextView) view.findViewById(R.id.tv_item_title);
        final TextView authorText = (TextView) view.findViewById(R.id.tv_item_author);

        imageView.setImageResource(cursor.getInt(IMG_RESOURCE));
        titleText.setText(cursor.getString(TITLE));
        authorText.setText(cursor.getString(AUTHOR));
    }

//    ArrayList<BookItem> bookItems;
//    Context mContext;
//
//    public BookListAdapter(Context context){
//        mContext = context;
//    }
//
//    public void addItem(BookItem bookItem){
//        bookItems.add(bookItem);
//    }
//
//    @Override
//    public int getCount() {
//        return bookItems.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return bookItems.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        BookItemView view = new BookItemView(mContext);
//        BookItem item = bookItems.get(position);
//
//        view.setTitle(item.getTitle());
//        view.setmAuthor(item.getAuthor());
////        view.setmBookImg(item.getBookImg());
//        //이미지 리소스 아이디로 불러오기 실행
//        return view;
//    }
}
