package com.naver.smarteditor.lesssmarteditor.adpater.edit.holder;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.naver.smarteditor.lesssmarteditor.adpater.basic.holder.BasicViewHolder;
import com.naver.smarteditor.lesssmarteditor.listener.OnComponentMenuClickListener;

/**
 * Created by NAVER on 2017. 5. 22..
 */

public class MapComponentViewHolder extends ComponentViewHolder{
    private TextView placeTextInfo;
    private ImageView placeMapImg;
    private LinearLayout placeLinearLayout;

    public MapComponentViewHolder(View itemView) {
        super(itemView);
        this.placeLinearLayout = (LinearLayout) itemView;

        placeMapImg = (ImageView) placeLinearLayout.getChildAt(0);
        placeTextInfo = (TextView) placeLinearLayout.getChildAt(1);
    }

    @Override
    public void setOnComponentContextMenuClickListener(OnComponentMenuClickListener onComponentContextMenuClickListener) {
        this.onComponentMenuClickListener = onComponentContextMenuClickListener;
    }

    public ViewGroup getRootView(){
        return placeLinearLayout;
    }

    public ImageView getImageView(){
        return placeMapImg;
    }

    public TextView getTextView(){
        return placeTextInfo;
    }

    @Override
    public void setDataPositionOnAdapter(int position) {
        this.position = position;
    }

    @Override
    public int getDataPositionOnAdapter(){
        return this.position;
    }
}
