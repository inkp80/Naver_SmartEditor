package com.naver.smarteditor.lesssmarteditor.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.naver.smarteditor.lesssmarteditor.MyApplication;

import java.util.List;

/**
 * Created by NAVER on 2017. 5. 11..
 */

public class EditorComponentAdapter extends RecyclerView.Adapter<EditorComponentAdapter.ComponentViewHolder>{
    String TAG = "EditorComponentAdapter";
    boolean localLogPermission = true;

    Context mContext;
    List<Integer> mListOfComponentType;


    public EditorComponentAdapter(Context context, List<Integer> list){
        MyApplication.LogController.makeLog(TAG, "Constructor", localLogPermission);
        mContext = context;
        mListOfComponentType = list;
    }

    public void setComponentList(List<Integer> list){
        mListOfComponentType = list;
    }

    @Override
    public ComponentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyApplication.LogController.makeLog(TAG, "onCreateViewHolder", localLogPermission);

        EditText tv = new EditText(mContext);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);

        return new ComponentViewHolder(tv);
    }

    @Override
    public void onBindViewHolder(ComponentViewHolder holder, int position) {
        MyApplication.LogController.makeLog(TAG, "onBindViewHolder", localLogPermission);
    }

    @Override
    public int getItemCount() {
        return mListOfComponentType.size();
    }

    class ComponentViewHolder extends RecyclerView.ViewHolder{

        public ComponentViewHolder(View itemView) {
            super(itemView);
        }
    }
}