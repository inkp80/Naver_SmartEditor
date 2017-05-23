package com.naver.smarteditor.lesssmarteditor.adpater.edit.holder;

import android.view.View;
import android.widget.ImageView;

import com.naver.smarteditor.lesssmarteditor.adpater.basic.holder.BasicViewHolder;

/**
 * Created by NAVER on 2017. 5. 22..
 */

public class ImgComponentViewHolder extends BasicViewHolder {

    private ImageView imageView;

    public ImgComponentViewHolder(View itemView) {
        super(itemView);
        this.imageView = (ImageView) itemView;
    }

    public ImageView getImageView(){
        return imageView;
    }
}
