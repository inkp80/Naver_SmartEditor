package com.naver.smarteditor.lesssmarteditor.adpater.edit.holder;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;


import com.naver.smarteditor.lesssmarteditor.adpater.edit.util.ComponentFocusListener;
import com.naver.smarteditor.lesssmarteditor.data.component.TextComponent;
import com.naver.smarteditor.lesssmarteditor.listener.OnComponentLongClickListener;
import com.naver.smarteditor.lesssmarteditor.listener.OnEditTextComponentChangeListener;

/**
 * Created by NAVER on 2017. 5. 22..
 */

public class TextComponentViewHolder extends ComponentViewHolder {


    private EditText et;
    private final OnEditTextComponentChangeListener onEditTextComponentChangeListener;
    private TextWatcher textWatcher;

    public TextComponentViewHolder(View itemView, final ComponentFocusListener componentFocusListener, final OnEditTextComponentChangeListener onEditTextComponentChangeListener) {
        super(itemView, componentFocusListener);
        this.et = (EditText) itemView;
        this.onEditTextComponentChangeListener = onEditTextComponentChangeListener;

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                componentFocusListener.OnComponentFocused(getAdapterPosition());
                return false;
            }
        });
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
    public void bindView(Object object) {
        TextComponent textData = (TextComponent)object;
        this.removeWatcher();
        this.setText(textData.getText());
        this.bindTextWathcer();
    }
}

