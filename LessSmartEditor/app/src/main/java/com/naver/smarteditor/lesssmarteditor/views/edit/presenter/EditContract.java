package com.naver.smarteditor.lesssmarteditor.views.edit.presenter;

import com.naver.smarteditor.lesssmarteditor.adpater.edit.EditComponentAdapter;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.EditComponentAdapterContract;
import com.naver.smarteditor.lesssmarteditor.data.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.edit.EditComponentRepository;
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

        void addComponent(BaseComponent.TypE type);

        void loadComponent();

        void setComponentAdatperModel(EditComponentAdapterContract.Model adapter);

        void setComponentAdapterView(EditComponentAdapterContract.View adapter);

        void setComponentDataSource(EditComponentRepository repository);

    }
}
