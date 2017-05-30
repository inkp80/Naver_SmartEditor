package com.naver.smarteditor.lesssmarteditor.adpater.main;

import com.naver.smarteditor.lesssmarteditor.adpater.basic.BaseAdapterContract;
import com.naver.smarteditor.lesssmarteditor.data.DocumentData;
import com.naver.smarteditor.lesssmarteditor.listener.OnDocumentClickListener;

import java.util.List;

/**
 * Created by NAVER on 2017. 5. 25..
 */

public interface DocumentListAdapterContract {

    interface View extends BaseAdapterContract.View {
        void notifyAdapter();
        void setDocumentOnClickedListener(OnDocumentClickListener onDocumentClickListener);
    }

    interface Model extends BaseAdapterContract.Model {
        void setDocumentList(List<DocumentData> docs);
    }
}
