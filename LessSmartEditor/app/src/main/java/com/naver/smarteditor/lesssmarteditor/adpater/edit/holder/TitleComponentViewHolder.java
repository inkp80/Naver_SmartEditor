package com.naver.smarteditor.lesssmarteditor.adpater.edit.holder;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.naver.smarteditor.lesssmarteditor.adpater.edit.util.ComponentFocusListener;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.component.TitleComponent;
import com.naver.smarteditor.lesssmarteditor.listener.OnEditTextComponentChangeListener;

/**
 * Created by NAVER on 2017. 6. 2..
 */

public class TitleComponentViewHolder extends ComponentViewHolder {


    private EditText title;
    private OnEditTextComponentChangeListener onEditTextComponentChangeListener;
    private TextWatcher textWatcher;


    public TitleComponentViewHolder(View itemView, ComponentFocusListener componentFocusListener, OnEditTextComponentChangeListener onEditTextComponentChangeListener) {
        super(itemView, null);
        this.title = (EditText) itemView;
        ((EditText)itemView).setHint("제목을 입력하세요");

        this.onEditTextComponentChangeListener = onEditTextComponentChangeListener;
        title.setOnClickListener(null);

    }

    public void bindTextWathcer() {

        //TODO : onCreateViewHolder에 textWatcher 결합하도록 이동
        title.addTextChangedListener(textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onEditTextComponentChangeListener.onEditTextComponentTextChange(new TitleComponent(s.toString(), null), getAdapterPosition());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    public void removeWatcher() {
        title.removeTextChangedListener(textWatcher);
    }

    @Override
    public void bindView(BaseComponent baseComponent) {
        TitleComponent component = (TitleComponent) baseComponent;
        this.removeWatcher();
        if(component.getTitle() != null)
            (this.title).setText(component.getTitle().toString());
        this.bindTextWathcer();
    }

    @Override
    public void showHighlight(){
        return;
    }
    @Override
    public void dismissHighlight(){
        return;
    }

}
