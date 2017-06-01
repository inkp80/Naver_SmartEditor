package com.naver.smarteditor.lesssmarteditor.listener;

import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;

/**
 * Created by NAVER on 2017. 5. 22..
 */

public interface OnEditTextComponentChangeListener {
    void onEditTextComponentTextChange(BaseComponent baseComponent, int position);
}
