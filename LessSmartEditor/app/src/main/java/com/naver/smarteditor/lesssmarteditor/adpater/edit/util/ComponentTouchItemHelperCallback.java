package com.naver.smarteditor.lesssmarteditor.adpater.edit.util;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.naver.smarteditor.lesssmarteditor.adpater.edit.EditComponentAdapter;

/**
 * Created by NAVER on 2017. 5. 29..
 */

public class ComponentTouchItemHelperCallback extends ItemTouchHelper.Callback {

    private EditComponentAdapter mAdapter;
    private ComponentTouchEventListener mItemTouchHelper;

    public ComponentTouchItemHelperCallback(EditComponentAdapter adapter, ComponentTouchEventListener componentTouchEventListener){
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
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mItemTouchHelper.OnComponentMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

}
