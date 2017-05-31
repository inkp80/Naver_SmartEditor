package com.naver.smarteditor.lesssmarteditor.adpater.edit.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import com.naver.smarteditor.lesssmarteditor.data.component.TextComponent;
import com.naver.smarteditor.lesssmarteditor.listener.OnComponentLongClickListener;
import com.naver.smarteditor.lesssmarteditor.listener.OnTextChangeListener;

/**
 * Created by NAVER on 2017. 5. 22..
 */

public class TextComponentViewHolder extends ComponentViewHolder {


    private EditText et;
    private final OnTextChangeListener onTextChangeListener;
    private TextWatcher textWatcher;

    public TextComponentViewHolder(View itemView, final OnTextChangeListener onTextChangeListener) {
        super(itemView);
        this.et = (EditText) itemView;
        this.onTextChangeListener = onTextChangeListener;
    }


    public void setText(String text) {
        et.setText(text);
    }


    public void onBind(final int position) {

        et.addTextChangedListener(textWatcher = new TextWatcher() {
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


    @Override
    public void setDataPositionOnAdapter(int position) {
        this.position = position;
    }

    @Override
    public int getDataPositionOnAdapter(){
        return this.position;
    }

    public void removeWatcher() {
        et.removeTextChangedListener(textWatcher);
    }


    @Override
    public void setOnComponentLongClickListener(OnComponentLongClickListener onComponentLongClickListener) {
        this.onComponentLongClickListener = onComponentLongClickListener;
    }


    @Override
    public void bindView(Object object) {
        TextComponent textData = (TextComponent)object;
        this.removeWatcher();
        this.setText(textData.getText());
        this.onBind(position);
    }
}

