package com.naver.smarteditor.lesssmarteditor.adpater.main.holder;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.naver.smarteditor.lesssmarteditor.adpater.basic.holder.BasicViewHolder;
import com.naver.smarteditor.lesssmarteditor.data.Document;
import com.naver.smarteditor.lesssmarteditor.data.DocumentParcelable;
import com.naver.smarteditor.lesssmarteditor.listener.OnDocumentClickListener;

/**
 * Created by NAVER on 2017. 5. 25..
 */

public class DocumentListViewHolder extends BasicViewHolder{

    private TextView mTitleText;
    private Document document;
    private OnDocumentClickListener onDocumentClickListener;

    public DocumentListViewHolder(View itemView, final OnDocumentClickListener onDocumentClickListener) {
        super(itemView);
        mTitleText = (TextView) itemView;
        mTitleText.setEllipsize(TextUtils.TruncateAt.END);
        mTitleText.setMaxLines(1);
        this.onDocumentClickListener = onDocumentClickListener;
        mTitleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentParcelable documentParcelable = new DocumentParcelable(
                        document.get_id(),
                        document.getTitle(),
                        document.getTimeStemp(),
                        document.getComponentsJson()
                );
                onDocumentClickListener.OnDocumentClick(documentParcelable);
            }
        });
    }

    public void bindDocumentData(Document data){
        this.document = data;
    }

    public void setTitleTextView(String title){
       mTitleText.setText(title);
    }

    public Document getDocument(){
        return document;
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
