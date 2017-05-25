package com.naver.smarteditor.lesssmarteditor.adpater.main;

import com.naver.smarteditor.lesssmarteditor.adpater.basic.BaseAdapterContract;
import com.naver.smarteditor.lesssmarteditor.data.DocumentData;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NAVER on 2017. 5. 25..
 */

public interface MainAdapterContract {

    interface View extends BaseAdapterContract.View {
        void notifyAdapter();
    }

    interface Model extends BaseAdapterContract.Model {
        void setComponent(List<DocumentData> docs);
        void clearComponent();
    }
}
