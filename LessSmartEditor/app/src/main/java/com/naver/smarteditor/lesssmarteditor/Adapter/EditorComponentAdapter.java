package com.naver.smarteditor.lesssmarteditor.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by NAVER on 2017. 5. 11..
 */

public class EditorComponentAdapter extends RecyclerView.Adapter<EditorComponentAdapter.ComponentViewHolder>{

    @Override
    public ComponentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ComponentViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ComponentViewHolder extends RecyclerView.ViewHolder{

        public ComponentViewHolder(View itemView) {
            super(itemView);
        }
    }
}