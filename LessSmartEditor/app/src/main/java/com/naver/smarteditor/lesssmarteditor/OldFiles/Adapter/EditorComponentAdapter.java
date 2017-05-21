package com.naver.smarteditor.lesssmarteditor.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.naver.smarteditor.lesssmarteditor.MyApplication;
import com.naver.smarteditor.lesssmarteditor.Objects.Comp;
import com.naver.smarteditor.lesssmarteditor.Objects.Component;
import com.naver.smarteditor.lesssmarteditor.Objects.ImageComponent;
import com.naver.smarteditor.lesssmarteditor.Objects.TextComponent;
import com.naver.smarteditor.lesssmarteditor.R;

import java.net.URI;
import java.util.List;

/**
 * Created by NAVER on 2017. 5. 11..
 */

public class EditorComponentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    String TAG = "EditorComponentAdapter";
    boolean localLogPermission = true;

    public RequestManager requestManager;

    Context mContext;
    List<Comp> mCompList;

    final int TXT_COMPONENT = 0;
    final int IMG_COMPONENT = 1;
    final int MAP_COMPONENT = 2;



    public EditorComponentAdapter(Context context, List<Comp> lists){
        MyApplication.LogController.makeLog(TAG, "Constructor", localLogPermission);
        requestManager = Glide.with(context);
        mContext = context;
        mCompList = lists;
    }

    public void setComponentList(List<Comp> list){
        mCompList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyApplication.LogController.makeLog(TAG, "onCreateViewHolder", localLogPermission);

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        if(viewType == TXT_COMPONENT){
            EditText tv = new EditText(mContext);
            tv.setLayoutParams(lp);
            return new TextComponentViewHolder(tv);
        }

        if(viewType == IMG_COMPONENT){
            ImageView imgView = new ImageView(mContext);
            imgView.setLayoutParams(lp);
            return new ImageComponentViewHolder(imgView);
        }

//        return new TextComponentViewHolder(tv);
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyApplication.LogController.makeLog(TAG, "onBindViewHolder", localLogPermission);
        switch (holder.getItemViewType()){
            case TXT_COMPONENT :
                TextComponentViewHolder textComponentViewHolder = (TextComponentViewHolder) holder;
//                textComponentViewHolder.mText.setText(((TextComponent)(mCompList.get(position).get())).getTextString());
                break;

            case IMG_COMPONENT:
                ImageComponentViewHolder imageComponentViewHolder = (ImageComponentViewHolder) holder;
//                String imgUrl = ((ImageComponent)(mComponents.get(position).getComponent())).getImageUri();
//                requestManager.load(imgUrl).into(((ImageComponentViewHolder) holder).mImage);
                break;
            case MAP_COMPONENT:

        }

    }

    @Override
    public int getItemCount() {
        return mCompList.size();
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
//        position;
        return position;
    }

    class TextComponentViewHolder extends RecyclerView.ViewHolder{

        public String mViewValue;
        public EditText mText;

        public TextComponentViewHolder(View itemView) {
            super(itemView);
            mText = (EditText) itemView;
        }
    }



    class ImageComponentViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImage;
        public ImageComponentViewHolder(View itemView){
            super(itemView);
            mImage = (ImageView) itemView;
        }
    }

    class MapComponentViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImage;

        public MapComponentViewHolder(View itemView){
            super(itemView);
            mImage = (ImageView) itemView;
        }

    }


}