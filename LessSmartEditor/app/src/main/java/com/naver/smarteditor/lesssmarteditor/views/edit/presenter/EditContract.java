package com.naver.smarteditor.lesssmarteditor.views.edit.presenter;

import com.naver.smarteditor.lesssmarteditor.views.basic.BaseView;
import com.naver.smarteditor.lesssmarteditor.views.basic.presenter.BasePresenter;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public interface EditContract {

    interface View extends BaseView{

    }

    interface Presenter extends BasePresenter{

        void attachView(View view);

        void detachView();

        void loadComponent();

        void setComponentAdatperModel();

        void setComponentAdapterView();
    }
}
