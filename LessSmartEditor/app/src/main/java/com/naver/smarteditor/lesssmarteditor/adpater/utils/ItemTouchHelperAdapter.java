package com.naver.smarteditor.lesssmarteditor.adpater.utils;

import android.support.v7.widget.RecyclerView;

/**
 * Created by NAVER on 2017. 5. 29..
 */

public interface ItemTouchHelperAdapter {

    void getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder);
    void onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolderFrom, RecyclerView.ViewHolder viewHolderTo);
    void onSwiped(RecyclerView.ViewHolder viewHolder, int index);

    void isLongPressDragEnabled();
    void isItemViewSwipeEnabled();
}
