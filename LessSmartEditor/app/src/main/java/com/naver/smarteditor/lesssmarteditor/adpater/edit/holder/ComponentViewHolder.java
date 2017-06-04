package com.naver.smarteditor.lesssmarteditor.adpater.edit.holder;


import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.View;

import com.naver.smarteditor.lesssmarteditor.R;
import com.naver.smarteditor.lesssmarteditor.adpater.basic.holder.BasicViewHolder;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.util.ComponentFocusListener;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.listener.OnComponentLongClickListener;


/**
 * Created by NAVER on 2017. 5. 29..
 */

abstract public class ComponentViewHolder extends BasicViewHolder {


    public ComponentViewHolder(final View itemView, final ComponentFocusListener componentFocusListener) {
        super(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rect outRect = new Rect();
                itemView.getGlobalVisibleRect(outRect);
                componentFocusListener.OnComponentFocused(outRect, getAdapterPosition());
            }
        });

    }

    abstract public void bindView(BaseComponent baseComponent);


    public void showHighlight(){
        this.itemView.setBackgroundColor(Color.LTGRAY);
    }

    public void dismissHighlight(){
        this.itemView.setBackgroundColor(Color.parseColor("#FAFAFA"));
    }


}
