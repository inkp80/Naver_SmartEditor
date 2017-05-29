package com.naver.smarteditor.lesssmarteditor.adpater.main.holder;

import android.view.View;
import android.widget.TextView;

import com.naver.smarteditor.lesssmarteditor.adpater.basic.holder.BasicViewHolder;
import com.naver.smarteditor.lesssmarteditor.data.DocumentData;
import com.naver.smarteditor.lesssmarteditor.data.DocumentDataParcelable;
import com.naver.smarteditor.lesssmarteditor.listener.OnDocumentClickListener;

/**
 * Created by NAVER on 2017. 5. 25..
 */

public class MainViewHolder extends BasicViewHolder{

    private TextView mTitleText;
    private DocumentData documentData;
    private OnDocumentClickListener onDocumentClickListener;

    public MainViewHolder(View itemView, final OnDocumentClickListener onDocumentClickListener) {
        super(itemView);
        mTitleText = (TextView) itemView;
        this.onDocumentClickListener = onDocumentClickListener;
        mTitleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentDataParcelable documentDataParcelable = new DocumentDataParcelable(
                        documentData.get_id(),
                        documentData.getTitle(),
                        documentData.getTimeStemp(),
                        documentData.getComponentsJson()
                );
                onDocumentClickListener.OnClick(documentDataParcelable);
            }
        });
    }

    public void bindDocumentData(DocumentData data){
        this.documentData = data;
    }

    public void setTitleTextView(String title){
        mTitleText.setText(title);
    }

    @Override
    public void setDataPositionOnAdapter(int position) {
        this.position = position;
    }

    @Override
    public int getDataPositionOnAdapter(){
        return this.position;
    }
}
