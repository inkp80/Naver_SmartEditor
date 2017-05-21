package com.naver.smarteditor.lesssmarteditor.adpater.edit;

import com.naver.smarteditor.lesssmarteditor.adpater.basic.BaseAdapterContract;
import com.naver.smarteditor.lesssmarteditor.data.BaseComponent;

import java.util.ArrayList;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public interface EditComponentAdapterContract {
    interface View{

        void setOnClickListener();

        void notifyAdapter();

    }

    interface Model{

        void addComponent(ArrayList<BaseComponent> components);

        BaseComponent getComponent(int position);

    }
}
