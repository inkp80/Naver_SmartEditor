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

        void setOnComponentLongClickListener(OnComponentLongClickListener onComponentLongClickListener);

    }

    interface Model extends BaseAdapterContract.Model {

        void initDocmentComponents(List<BaseComponent> components);

        void clearDocumentComponent();
    }
}
