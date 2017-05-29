package com.naver.smarteditor.lesssmarteditor.adpater.edit;

import com.naver.smarteditor.lesssmarteditor.adpater.basic.BaseAdapterContract;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.listener.OnComponentMenuClickListener;
import com.naver.smarteditor.lesssmarteditor.listener.OnTextChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public interface EditComponentAdapterContract {
    interface View extends BaseAdapterContract.View{

        void setOnTextChangeListener(OnTextChangeListener onTextChangeListener);

        void setOnComponentClickListener(OnComponentMenuClickListener onComponentClickListener);

        void notifyAdapter();

        void reoderingComponent(int fromPosition, int toPosition);

    }

    interface Model extends BaseAdapterContract.Model {

        void setComponent(List<BaseComponent> components);

        void clearComponent();

    }
}
