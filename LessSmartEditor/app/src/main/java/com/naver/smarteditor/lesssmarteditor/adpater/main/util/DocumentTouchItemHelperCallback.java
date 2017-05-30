package com.naver.smarteditor.lesssmarteditor.adpater.main.util;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.naver.smarteditor.lesssmarteditor.adpater.edit.EditComponentAdapter;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.util.ComponentTouchEventListener;
import com.naver.smarteditor.lesssmarteditor.adpater.main.DocumentListAdapter;
import com.naver.smarteditor.lesssmarteditor.adpater.main.holder.DocumentListViewHolder;
import com.naver.smarteditor.lesssmarteditor.data.DocumentData;

/**
 * Created by NAVER on 2017. 5. 30..
 */

public class DocumentTouchItemHelperCallback extends ItemTouchHelper.Callback {

    private DocumentListAdapter mAdapter;
    private DocumentTouchEventListener mItemTouchHelper;

    public DocumentTouchItemHelperCallback(DocumentListAdapter adapter, DocumentTouchEventListener documentTouchEventListener){
        this.mAdapter = adapter;
        this.mItemTouchHelper = documentTouchEventListener;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mItemTouchHelper.OnItemDismiss(((DocumentListViewHolder)viewHolder).getDocumentData());
    }

}