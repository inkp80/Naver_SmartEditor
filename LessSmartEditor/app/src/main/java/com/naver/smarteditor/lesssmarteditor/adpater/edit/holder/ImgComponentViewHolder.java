package com.naver.smarteditor.lesssmarteditor.adpater.edit.holder;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.naver.smarteditor.lesssmarteditor.adpater.basic.holder.BasicViewHolder;
import com.naver.smarteditor.lesssmarteditor.data.component.ImgComponent;
import com.naver.smarteditor.lesssmarteditor.listener.OnComponentLongClickListener;

/**
 * Created by NAVER on 2017. 5. 22..
 */

public class ImgComponentViewHolder extends ComponentViewHolder {

    private ImageView imageView;
    private RequestManager requestManager;

    public ImgComponentViewHolder(View itemView, RequestManager requestManager) {
        super(itemView);
        this.imageView = (ImageView) itemView;
        this.requestManager = requestManager;
    }

    @Override
    public void setDataPositionOnAdapter(int position) {
        this.position = position;
    }


    @Override
    public int getDataPositionOnAdapter(){
        return this.position;
    }


    @Override
    public void setOnComponentLongClickListener(OnComponentLongClickListener onComponentLongClickListener) {
        this.onComponentLongClickListener = onComponentLongClickListener;
    }

    @Override
    public void bindView(Object object) {
        ImgComponent thisImgComponent = (ImgComponent) object;
        requestManager.load(thisImgComponent.getImgUri())
                .override(600, 600)
                .into(this.imageView);
    }
}
