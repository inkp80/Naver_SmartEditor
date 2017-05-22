package com.naver.smarteditor.lesssmarteditor.adpater.edit.holder;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.naver.smarteditor.lesssmarteditor.adpater.basic.holder.BasicViewHolder;
import com.naver.smarteditor.lesssmarteditor.listener.OnTextChangeListener;

/**
 * Created by NAVER on 2017. 5. 22..
 */

public class TextComponentViewHolder extends BasicViewHolder {

    private EditText et;
    private OnTextChangeListener onTextChangeListener;

    public TextComponentViewHolder(View itemView, OnTextChangeListener onTextChangeListener) {
        super(itemView);
        this.et = (EditText) itemView;
        this.onTextChangeListener = onTextChangeListener;
    }

    public void setText(String text){
        et.setText(text);
    }

    public void onBind(final int position){

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onTextChangeListener.onTextChanged(s, position);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
