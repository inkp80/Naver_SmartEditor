package com.naver.smarteditor.lesssmarteditor.adpater.edit.holder;


import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;

import com.naver.smarteditor.lesssmarteditor.R;
import com.naver.smarteditor.lesssmarteditor.adpater.basic.holder.BasicViewHolder;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.util.ComponentFocusListener;
import com.naver.smarteditor.lesssmarteditor.listener.OnComponentLongClickListener;


/**
 * Created by NAVER on 2017. 5. 29..
 */

abstract public class ComponentViewHolder extends BasicViewHolder {


    public ComponentViewHolder(final View itemView, final ComponentFocusListener componentFocusListener) {
        super(itemView);

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                componentFocusListener.OnComponentFocused(getAdapterPosition());
                return false;
            }
        });
//        int padding = Resources.getSystem().getInteger(R.integer.viewholder_padding);
//        itemView.setPadding(10,padding,padding,padding);

    }

    abstract public void bindView(Object object);


    public void setHighlight(){
        this.itemView.setBackgroundColor(Color.LTGRAY);
    }

    public void dismissHighlight(){
        this.itemView.setBackgroundColor(Color.parseColor("#FAFAFA"));
    }
}
