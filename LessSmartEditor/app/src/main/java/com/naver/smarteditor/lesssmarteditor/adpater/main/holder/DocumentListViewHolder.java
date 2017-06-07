package com.naver.smarteditor.lesssmarteditor.adpater.main.holder;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.naver.smarteditor.lesssmarteditor.LogController;
import com.naver.smarteditor.lesssmarteditor.adpater.basic.holder.BasicViewHolder;
import com.naver.smarteditor.lesssmarteditor.listener.OnDocumentItemClickListener;

/**
 * Created by NAVER on 2017. 5. 25..
 */

public class DocumentListViewHolder extends BasicViewHolder{

    private TextView mTitleText;
    private int documentId;
    private OnDocumentItemClickListener onDocumentItemClickListener;

    public DocumentListViewHolder(View itemView, final OnDocumentItemClickListener onDocumentItemClickListener) {
        super(itemView);
        mTitleText = (TextView) itemView;
        mTitleText.setEllipsize(TextUtils.TruncateAt.END);
        mTitleText.setMaxLines(1);
        this.onDocumentItemClickListener = onDocumentItemClickListener;
        mTitleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDocumentItemClickListener.OnItemClick(documentId);
            }
        });
    }


    public void setTitleTextView(String title){
       mTitleText.setText(title);
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public int getDocumentId(){
        return this.documentId;
    }
}
