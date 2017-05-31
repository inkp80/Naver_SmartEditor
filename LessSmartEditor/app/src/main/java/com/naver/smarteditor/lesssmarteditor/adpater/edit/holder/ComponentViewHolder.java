package com.naver.smarteditor.lesssmarteditor.adpater.edit.holder;

import android.content.Context;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.naver.smarteditor.lesssmarteditor.MyApplication;
import com.naver.smarteditor.lesssmarteditor.adpater.basic.holder.BasicViewHolder;
import com.naver.smarteditor.lesssmarteditor.listener.OnComponentLongClickListener;

import static com.naver.smarteditor.lesssmarteditor.MyApplication.COMPONENT_MENU_CANCEL;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.COMPONENT_MENU_DELETE;

/**
 * Created by NAVER on 2017. 5. 29..
 */

abstract public class ComponentViewHolder extends BasicViewHolder {

    public OnComponentLongClickListener onComponentLongClickListener;

    public ComponentViewHolder(final View itemView) {
        super(itemView);
        itemView.setPadding(10,10,10,10);
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onComponentLongClickListener.OnComponentLongClick(getDataPositionOnAdapter(), itemView);
                return false;
            }
        });
    }

    @Override
    public int getDataPositionOnAdapter() {
        return 0;
    }

    @Override
    public void setDataPositionOnAdapter(int position) {

    }

    abstract public void bindView(Object object);

    abstract public void setOnComponentLongClickListener(OnComponentLongClickListener onComponentLongClickListener);
}
