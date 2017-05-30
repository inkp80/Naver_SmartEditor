package com.naver.smarteditor.lesssmarteditor.views.main.presenter;

import com.naver.smarteditor.lesssmarteditor.adpater.main.DocumentListAdapterContract;
import com.naver.smarteditor.lesssmarteditor.data.DocumentDataParcelable;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.EditorComponentRepository;
import com.naver.smarteditor.lesssmarteditor.views.basic.BaseView;
import com.naver.smarteditor.lesssmarteditor.views.basic.presenter.BasePresenter;

/**
 * Created by NAVER on 2017. 5. 24..
 */

public interface DocumentListContract {
    interface View extends BaseView{
        void editThisDocument(DocumentDataParcelable documentDataParcelable);
    }

    interface Presenter extends BasePresenter{
        void attachView(View view);

        void detachView();

        void setMainAdapterModel(DocumentListAdapterContract.Model adapter);

        void setMainAdapterView(DocumentListAdapterContract.View adapter);

        void setComponentDataSource(EditorComponentRepository repository);

        void requestDocList();
    }
}
