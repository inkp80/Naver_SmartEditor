package com.naver.smarteditor.lesssmarteditor.adpater.edit;

import com.naver.smarteditor.lesssmarteditor.adpater.basic.BaseAdapterContract;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.listener.OnComponentLongClickListener;
import com.naver.smarteditor.lesssmarteditor.listener.OnEditTextComponentChangeListener;

import java.util.List;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public interface EditComponentAdapterContract {
    interface View extends BaseAdapterContract.View{

        void notifyDataChange();

        void swapDocumentComponent(int fromPosition, int toPosition);

        void setOnEditTextComponentChangeListener(OnEditTextComponentChangeListener onEditTextComponentChangeListener);
    }

    interface Model extends BaseAdapterContract.Model {

        void clearDocumentComponent();

        void initDocmentComponents(List<BaseComponent> components);

        void addDocumentComponent(BaseComponent.Type type, BaseComponent baseComponent);
        void deleteDocumentComponent(int postion);
        void updateDocumentComponent(int position, BaseComponent baseComponent);
        void swapDocumentComponent(int fromPosition, int toPosition);


        //데이터베이스에 저장하기 위해서 presenter측에서 정보를 Adapter Model에 요청한다.
        //view - action:save = mPresenter.save - mAdapter.Model getDataFromAdapterModel, LocalDB.saveIntoLocalDB
        List<BaseComponent> printOutDocument();


    }
}
