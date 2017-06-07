package com.naver.smarteditor.lesssmarteditor.adpater.edit.holder;

import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;


import com.naver.smarteditor.lesssmarteditor.adpater.edit.util.ComponentFocusListener;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.component.TextComponent;
import com.naver.smarteditor.lesssmarteditor.listener.OnEditTextComponentChangeListener;
import com.naver.smarteditor.lesssmarteditor.views.edit.SmartEditText;

/**
 * Created by NAVER on 2017. 5. 22..
 */

public class TextComponentViewHolder extends ComponentViewHolder {


    public boolean focused = false;

    private SmartEditText et;
    private final OnEditTextComponentChangeListener onEditTextComponentChangeListener;
    private TextWatcher textWatcher;

    public TextComponentViewHolder(final View itemView, final OnEditTextComponentChangeListener onEditTextComponentChangeListener) {
        super(itemView);
        this.et = (SmartEditText) itemView;

        this.onEditTextComponentChangeListener = onEditTextComponentChangeListener;
    }


    public void setText(String text) {
        et.setText(text);
    }


    public void bindTextWathcer() {

        //TODO : onCreateViewHolder에 textWatcher 결합하도록 이동
        et.addTextChangedListener(textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onEditTextComponentChangeListener.onEditTextComponentTextChange(new TextComponent(s.toString()), getAdapterPosition());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    public void removeWatcher() {
        et.removeTextChangedListener(textWatcher);
    }


    @Override
    public void bindView(BaseComponent baseComponent) {
        TextComponent textData = (TextComponent)baseComponent;
        this.removeWatcher();
        this.setText(textData.getText());
        this.bindTextWathcer();
    }

    public SmartEditText getEditText(){
        return et;
    }
}

