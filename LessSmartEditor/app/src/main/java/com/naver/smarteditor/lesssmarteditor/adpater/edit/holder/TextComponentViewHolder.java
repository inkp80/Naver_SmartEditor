package com.naver.smarteditor.lesssmarteditor.adpater.edit.holder;

import android.graphics.Paint;
import android.text.Editable;
import android.text.InputType;
import android.text.Spannable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.naver.smarteditor.lesssmarteditor.LogController;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.util.SpanInfoExtractor;
import com.naver.smarteditor.lesssmarteditor.data.SpanInfo;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.component.TextComponent;
import com.naver.smarteditor.lesssmarteditor.listener.OnEditTextComponentChangeListener;
import com.naver.smarteditor.lesssmarteditor.views.edit.SmartEditText;

import java.util.List;

/**
 * Created by NAVER on 2017. 5. 22..
 */

public class TextComponentViewHolder extends ComponentViewHolder {

    //TODO : Json 파싱 : 데이터 연산이 현재 이 곳에서 일어나고 있음 - 모델로 옮기기

    public boolean focused = false;

    private SmartEditText et;
    private TextWatcher textWatcher;

    public TextComponentViewHolder(final View itemView, final OnEditTextComponentChangeListener onEditTextComponentChangeListener) {
        super(itemView);
        this.et = (SmartEditText) itemView;

        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    Spannable spannable = et.getText();

                    Gson gson = new Gson();
                    String str = gson.toJson(SpanInfoExtractor.extractSpansFromSpannable(spannable));

                    onEditTextComponentChangeListener.onEditTextComponentTextChange(new TextComponent(et.getText().toString(), str), getAdapterPosition());
                }
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
//                onEditTextComponentChangeListener.onEditTextComponentTextChange(new TextComponent(s.toString(), ""), getAdapterPosition());
            }

            @Override
            public void afterTextChanged(Editable s) {
//                onEditTextComponentChangeListener.onEditTextComponentTextChange(new TextComponent(s.toString(), SpanInfoExtractor.extractSpansFromSpannable(s)), getAdapterPosition());
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

        if(textData.getTextSpans() != null || textData.getText().length() != 0) {
            List<SpanInfo> spanInfoList = SpanInfoExtractor.spanJsonDeserializer(textData.getTextSpans());
            SpanInfoExtractor.setSpansIntoSpannable(spanInfoList, this.et);
        }
        this.bindTextWathcer();
    }

    public SmartEditText getEditText(){
        return et;
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

