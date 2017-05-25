package com.naver.smarteditor.lesssmarteditor.adpater.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.naver.smarteditor.lesssmarteditor.adpater.basic.holder.BasicViewHolder;
import com.naver.smarteditor.lesssmarteditor.adpater.main.holder.MainViewHolder;
import com.naver.smarteditor.lesssmarteditor.data.DocumentData;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.listener.OnDocumentClickedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NAVER on 2017. 5. 25..
 */

public class MainAdapter extends RecyclerView.Adapter<BasicViewHolder> implements MainAdapterContract.Model, MainAdapterContract.View {

    private Context mContext;
    private OnDocumentClickedListener onDocumentClickedListener;
    private List<DocumentData> mDocumentData;

    public MainAdapter(Context context){
        this.mContext = context;
        mDocumentData = new ArrayList<>();
    }

    @Override
    public BasicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView mTitle = new TextView(mContext);
        mTitle.setLayoutParams(lp);
        MainViewHolder mainViewHolder = new MainViewHolder(mTitle, onDocumentClickedListener);


        return mainViewHolder;
    }

    @Override
    public void onBindViewHolder(BasicViewHolder holder, int position) {
        MainViewHolder thisViewHolder = (MainViewHolder) holder;
        thisViewHolder.setTitleTextView(mDocumentData.get(position).getTitle());
        thisViewHolder.bindDocumentData(mDocumentData.get(position));
    }

    @Override
    public int getItemCount() {
        return mDocumentData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void notifyAdapter() {
        notifyDataSetChanged();
    }

    @Override
    public void setComponent(List<DocumentData> docs) {
        this.mDocumentData = docs;

    }

    @Override
    public void clearComponent() {
        this.mDocumentData.clear();
    }
}
