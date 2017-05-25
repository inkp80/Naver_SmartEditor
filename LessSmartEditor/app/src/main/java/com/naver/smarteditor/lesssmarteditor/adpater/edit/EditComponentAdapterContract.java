package com.naver.smarteditor.lesssmarteditor.adpater.edit;

import com.naver.smarteditor.lesssmarteditor.adpater.basic.BaseAdapterContract;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.listener.OnTextChangeListener;

import java.util.ArrayList;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public interface EditComponentAdapterContract {
    interface View extends BaseAdapterContract.View{

        void setOnTextChangeListener(OnTextChangeListener onTextChangeListener);

        void notifyAdapter();

    }

    interface Model extends BaseAdapterContract.Model {

        void setComponent(ArrayList<BaseComponent> components);

        void editComponent(CharSequence s, int position);

        BaseComponent getComponent(int position);

        void clearComponent();

    }
}
