package com.naver.smarteditor.lesssmarteditor.adpater.edit.holder;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.util.ComponentFocusListener;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.component.ImgComponent;
import com.naver.smarteditor.lesssmarteditor.listener.OnComponentLongClickListener;

/**
 * Created by NAVER on 2017. 5. 22..
 */

public class ImgComponentViewHolder extends ComponentViewHolder {

    private ImageView imageView;
    private RequestManager requestManager;

    public ImgComponentViewHolder(View itemView, final ComponentFocusListener componentFocusListener, RequestManager requestManager) {
        super(itemView, componentFocusListener);
        this.imageView = (ImageView) itemView;
        this.requestManager = requestManager;

    }


    @Override
    public void bindView(BaseComponent baseComponent) {
        ImgComponent thisImgComponent = (ImgComponent) baseComponent;
        requestManager.load(thisImgComponent.getImgUri())
                .override(600, 600)
                .into(this.imageView);
    }
}
