package com.naver.smarteditor.lesssmarteditor.adpater.edit.holder;

import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
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
    private RequestManager requestManager;


    public TitleComponentViewHolder(View itemView, RequestManager requestManager, OnEditTextComponentChangeListener onEditTextComponentChangeListener) {
        super(itemView);
        this.title = (SmartEditText) itemView;
        ((EditText)itemView).setHint("제목을 입력하세요");

        setTitleLengthLimit(30);
        this.onEditTextComponentChangeListener = onEditTextComponentChangeListener;
        title.setOnClickListener(null);
        this.requestManager = requestManager;

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
        if(component.getTitleBackgroundUri() != null) {
            if(component.getTitleBackgroundUri() == ""){
                title.setBackgroundResource(android.R.drawable.edit_text);
                return;
            }
            requestManager.load(component.getTitleBackgroundUri()).override(600, 200).into(new ViewTarget<SmartEditText, GlideDrawable>(this.title) {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation anim) {
                    SmartEditText myView = this.view;
                    if (Build.VERSION.SDK_INT >= 16)
                        myView.setBackground(resource);
                    else
                        return;
                }
            });
        } else {
            title.setBackgroundResource(android.R.drawable.edit_text);
        }

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
