package com.naver.smarteditor.lesssmarteditor.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.naver.smarteditor.lesssmarteditor.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NAVER on 2017. 5. 11..
 */

public class EditorComponentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    String TAG = "EditorComponentAdapter";
    boolean localLogPermission = true;

    Context mContext;
    List<Integer> mListOfComponentType;
    public Object[] mComponent;



    public EditorComponentAdapter(Context context, List<Integer> list){
        MyApplication.LogController.makeLog(TAG, "Constructor", localLogPermission);
        mContext = context;
        mListOfComponentType = list;
    }

    public void setComponentList(List<Integer> list){
        mListOfComponentType = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyApplication.LogController.makeLog(TAG, "onCreateViewHolder", localLogPermission);

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

//        View tv = new View(mContext);
        if(viewType == 0){
            EditText tv = new EditText(mContext);
            tv.setLayoutParams(lp);
            return new TextComponentViewHolder(tv);
        }

//        return new TextComponentViewHolder(tv);
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyApplication.LogController.makeLog(TAG, "onBindViewHolder", localLogPermission);

//        switch (holder.getItemViewType()) {
//            case 0:
//                ViewHolder0 viewHolder0 = (ViewHolder0)holder;
//                ...
//                break;
//
//            case 2:
//                ViewHolder2 viewHolder2 = (ViewHolder2)holder;
//                ...
//                break;
//        }

    }

    @Override
    public int getItemCount() {
        return mListOfComponentType.size();
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
//        position;
        return mListOfComponentType.get(position);
    }

    class TextComponentViewHolder extends RecyclerView.ViewHolder{

        public String mViewValue;
        public EditText itemView;

        public TextComponentViewHolder(View itemView) {
            super(itemView);
        }
    }



    class ImgComponentViewHolder extends RecyclerView.ViewHolder{

        public View itemView;

        public ImgComponentViewHolder(View itemView){
            super(itemView);
            this.itemView = itemView;
        }
    }


}