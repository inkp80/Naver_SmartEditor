package com.naver.smarteditor.lesssmarteditor.adpater.edit.holder;


import android.graphics.Color;
import android.view.View;

import com.naver.smarteditor.lesssmarteditor.adpater.basic.holder.BasicViewHolder;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;


/**
 * Created by NAVER on 2017. 5. 29..
 */

abstract public class ComponentViewHolder extends BasicViewHolder {


    public ComponentViewHolder(final View itemView) {
        super(itemView);
    }

    abstract public void bindView(BaseComponent baseComponent);


    public void setMark(){
        this.itemView.setBackgroundColor(Color.LTGRAY);
    }

    public void removeMark(){
        this.itemView.setBackgroundColor(Color.parseColor("#FAFAFA"));
    }
    
    public void initFocusing(int focusPosition){
        if(focusPosition == getAdapterPosition()){
            setMark();
        } else{
            removeMark();
        }
    }

    public View getItemView(){
        return this.itemView;
    }


}
