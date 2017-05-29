package com.naver.smarteditor.lesssmarteditor.adpater.edit.holder;

import android.view.View;
import android.widget.ImageView;

import com.naver.smarteditor.lesssmarteditor.adpater.basic.holder.BasicViewHolder;
import com.naver.smarteditor.lesssmarteditor.listener.OnComponentMenuClickListener;

/**
 * Created by NAVER on 2017. 5. 22..
 */

public class ImgComponentViewHolder extends ComponentViewHolder {

    private ImageView imageView;

    public ImgComponentViewHolder(View itemView) {
        super(itemView);
        this.imageView = (ImageView) itemView;
    }

    public ImageView getImageView(){
        return imageView;
    }

    @Override
    public void setDataPositionOnAdapter(int position) {
        this.position = position;
    }

    @Override
    public void setOnComponentContextMenuClickListener(OnComponentMenuClickListener onComponentContextMenuClickListener) {
        this.onComponentMenuClickListener = onComponentContextMenuClickListener;
    }

    @Override
    public int getDataPositionOnAdapter(){
        return this.position;
    }
}
