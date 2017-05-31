package com.naver.smarteditor.lesssmarteditor.adpater.main;

import com.naver.smarteditor.lesssmarteditor.adpater.basic.BaseAdapterContract;
import com.naver.smarteditor.lesssmarteditor.data.Document;
import com.naver.smarteditor.lesssmarteditor.listener.OnDocumentClickListener;

import java.util.List;

/**
 * Created by NAVER on 2017. 5. 25..
 */

public interface DocumentListAdapterContract {

    interface View extends BaseAdapterContract.View {
        void notifyDataChange();
        void setDocumentOnClickedListener(OnDocumentClickListener onDocumentClickListener);
    }

    interface Model extends BaseAdapterContract.Model {
        void initDocumentList(List<Document> docs);
    }
}
