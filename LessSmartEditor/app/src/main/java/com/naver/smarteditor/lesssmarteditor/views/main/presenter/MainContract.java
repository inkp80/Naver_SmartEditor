package com.naver.smarteditor.lesssmarteditor.views.main.presenter;

import com.naver.smarteditor.lesssmarteditor.adpater.main.MainAdapter;
import com.naver.smarteditor.lesssmarteditor.adpater.main.MainAdapterContract;
import com.naver.smarteditor.lesssmarteditor.data.DocumentDataParcelable;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.EditorComponentRepository;
import com.naver.smarteditor.lesssmarteditor.views.basic.BaseView;
import com.naver.smarteditor.lesssmarteditor.views.basic.presenter.BasePresenter;

/**
 * Created by NAVER on 2017. 5. 24..
 */

public interface MainContract {
    interface View extends BaseView{
        void passDataToEditor(DocumentDataParcelable documentDataParcelable);
    }

    interface Presenter extends BasePresenter{
        void requestDocList();

        void setMainAdapterModel(MainAdapterContract.Model adapter);

        void setMainAdapterView(MainAdapterContract.View adapter);

        void setComponentDataSource(EditorComponentRepository repository);

        void attachView(View view);

        void detachView();
    }
}
