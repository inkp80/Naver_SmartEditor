package com.naver.smarteditor.lesssmarteditor.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.naver.smarteditor.lesssmarteditor.R;

/**
 * Created by NAVER on 2017. 5. 11..
 */

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.DocumentListViewHolder> {

    Cursor mDocListCursor;
    Context mContext;

    public MainRecyclerViewAdapter(Context context, Cursor cursor){
        mContext = context;
        mDocListCursor = cursor;
    }


    @Override
    public MainRecyclerViewAdapter.DocumentListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.document_view, parent, false);

        return null;
    }

    @Override
    public void onBindViewHolder(MainRecyclerViewAdapter.DocumentListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class DocumentListViewHolder extends RecyclerView.ViewHolder{

        public DocumentListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
