package com.naver.smarteditor.lesssmarteditor.adpater.basic.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by NAVER on 2017. 5. 21..
 */

abstract public class BasicViewHolder extends RecyclerView.ViewHolder{
    public int position;

    public BasicViewHolder(View itemView) {
        super(itemView);
    }

    abstract public int getDataPositionOnAdapter();
    abstract public void setDataPositionOnAdapter(int position);

}

