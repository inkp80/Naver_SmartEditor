package com.naver.smarteditor.lesssmarteditor.adpater.main.holder;

import android.view.View;
import android.widget.TextView;

import com.naver.smarteditor.lesssmarteditor.adpater.basic.holder.BasicViewHolder;
import com.naver.smarteditor.lesssmarteditor.data.DocumentData;
import com.naver.smarteditor.lesssmarteditor.data.DocumentDataParcelable;
import com.naver.smarteditor.lesssmarteditor.listener.OnDocumentClickedListener;

/**
 * Created by NAVER on 2017. 5. 25..
 */

public class MainViewHolder extends BasicViewHolder{

    private TextView mTitleText;
    private DocumentData documentData;
    private OnDocumentClickedListener onDocumentClickedListener;

    public MainViewHolder(View itemView, final OnDocumentClickedListener onDocumentClickedListener) {
        super(itemView);
        mTitleText = (TextView) itemView;
        this.onDocumentClickedListener = onDocumentClickedListener;
        mTitleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentDataParcelable documentDataParcelable = new DocumentDataParcelable(
                        documentData.get_id(),
                        documentData.getTitle(),
                        documentData.getTimeStemp(),
                        documentData.getComponentsJson()
                );
                onDocumentClickedListener.OnClick(documentDataParcelable);
            }
        });
    }

    public void bindDocumentData(DocumentData data){
        this.documentData = data;
    }

    public void setTitleTextView(String title){
        mTitleText.setText(title);
    }
}
