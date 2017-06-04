package com.naver.smarteditor.lesssmarteditor.adpater.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.TextView;

import com.naver.smarteditor.lesssmarteditor.adpater.basic.holder.BasicViewHolder;
import com.naver.smarteditor.lesssmarteditor.adpater.main.holder.DocumentListViewHolder;
import com.naver.smarteditor.lesssmarteditor.data.Document;
import com.naver.smarteditor.lesssmarteditor.listener.OnDocumentItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NAVER on 2017. 5. 25..
 */

public class DocumentListAdapter extends RecyclerView.Adapter<BasicViewHolder> implements DocumentListAdapterContract.Model, DocumentListAdapterContract.View {

    private Context mContext;
    private OnDocumentItemClickListener onDocumentItemClickListener;
    private List<Document> mDocument;

    public DocumentListAdapter(Context context){
        this.mContext = context;
        mDocument = new ArrayList<>();
    }

    @Override
    public BasicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView mTitle = new TextView(mContext);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
        mTitle.setPadding(10,10,10,10);
        mTitle.setLayoutParams(lp);
        DocumentListViewHolder documentListViewHolder = new DocumentListViewHolder(mTitle, onDocumentItemClickListener);
        return documentListViewHolder;
    }

    @Override
    public void onBindViewHolder(BasicViewHolder holder, int position) {
        DocumentListViewHolder thisViewHolder = (DocumentListViewHolder) holder;
        thisViewHolder.setTitleTextView(mDocument.get(position).getTitle());
        thisViewHolder.setDocumentId(mDocument.get(position).get_id());
    }

    @Override
    public int getItemCount() {
        return mDocument.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void notifyDataChange() {
        notifyDataSetChanged();
    }

    @Override
    public void initDocumentList(List<Document> docs) {
        this.mDocument = docs;
    }

    @Override
    public void setOnDocumentItemClickListener(OnDocumentItemClickListener onDocumentItemClickListener) {
        this.onDocumentItemClickListener = onDocumentItemClickListener;
    }

}
