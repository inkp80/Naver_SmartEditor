package com.naver.smarteditor.lesssmarteditor.adpater.edit.holder;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.naver.smarteditor.lesssmarteditor.MyApplication;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.util.ComponentFocusListener;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.component.TitleComponent;
import com.naver.smarteditor.lesssmarteditor.listener.OnEditTextComponentChangeListener;
import com.naver.smarteditor.lesssmarteditor.views.edit.SmartEditText;
import com.naver.smarteditor.lesssmarteditor.views.edit.utils.TitleFilter;

/**
 * Created by NAVER on 2017. 6. 2..
 */

public class TitleComponentViewHolder extends ComponentViewHolder {

    private SmartEditText title;
    private OnEditTextComponentChangeListener onEditTextComponentChangeListener;
    private TextWatcher textWatcher;


    public TitleComponentViewHolder(View itemView, OnEditTextComponentChangeListener onEditTextComponentChangeListener) {
        super(itemView);
        this.title = (SmartEditText) itemView;
        ((EditText)itemView).setHint("제목을 입력하세요");

        setTitleLengthLimit(30);
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
                onEditTextComponentChangeListener.onEditTextComponentTextChange(new TitleComponent(s.toString(), MyApplication.NO_TITLE_IMG), getAdapterPosition());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setTitleLengthLimit(int lengthLimit) {
        InputFilter[] f = new InputFilter[]{
                new TitleFilter(title.getContext(), lengthLimit)
        };
        title.setFilters(f);
    }



    public void removeWatcher() {
        title.removeTextChangedListener(textWatcher);
    }

    @Override
    public void bindView(BaseComponent baseComponent) {
        TitleComponent component = (TitleComponent) baseComponent;
        Log.d("bindView - Title", String.valueOf(component.getTitleBackgroundUri()));
        this.removeWatcher();
        if(component.getTitle() != null)
            (this.title).setText(component.getTitle().toString());
        this.bindTextWathcer();
    }

    public SmartEditText getEditText(){
        return title;
    }

    @Override
    public void setMark() {
        return;
    }

    @Override
    public void removeMark() {
        return;
    }
}
