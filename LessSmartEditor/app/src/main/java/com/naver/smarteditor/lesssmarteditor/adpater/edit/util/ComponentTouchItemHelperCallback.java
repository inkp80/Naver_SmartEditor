package com.naver.smarteditor.lesssmarteditor.adpater.edit.util;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.FocusFinder;
import android.view.View;


import com.naver.smarteditor.lesssmarteditor.LogController;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.EditComponentAdapter;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.holder.TextComponentViewHolder;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.holder.TitleComponentViewHolder;
import com.naver.smarteditor.lesssmarteditor.data.component.TextComponent;

/**
 * Created by NAVER on 2017. 5. 29..
 */

public class ComponentTouchItemHelperCallback extends ItemTouchHelper.Callback {

    private EditComponentAdapter mAdapter;
    private ComponentTouchEventListener mItemTouchHelper;

    public ComponentTouchItemHelperCallback(EditComponentAdapter adapter, ComponentTouchEventListener componentTouchEventListener) {
        this.mAdapter = adapter;
        this.mItemTouchHelper = componentTouchEventListener;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof TitleComponentViewHolder) {
            return 0;
        } else if(viewHolder instanceof TextComponentViewHolder){
            if(((TextComponentViewHolder)viewHolder).focused)
                return 0;
        }



        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (target instanceof TitleComponentViewHolder) {
            return false;
        }
        mItemTouchHelper.OnComponentMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

}
