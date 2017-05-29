package com.naver.smarteditor.lesssmarteditor.adpater.edit.holder;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


import com.naver.smarteditor.lesssmarteditor.listener.OnComponentMenuClickListener;
import com.naver.smarteditor.lesssmarteditor.listener.OnTextChangeListener;

import static com.naver.smarteditor.lesssmarteditor.MyApplication.COMPONENT_MENU_CANCEL;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.COMPONENT_MENU_DELETE;

/**
 * Created by NAVER on 2017. 5. 22..
 */

public class TextComponentViewHolder extends ComponentViewHolder implements View.OnCreateContextMenuListener {


    private EditText et;
    private final OnTextChangeListener onTextChangeListener;
    private TextWatcher textWatcher;


    //memory leak..?

    public TextComponentViewHolder(View itemView, final OnTextChangeListener onTextChangeListener) {
        super(itemView);
        this.et = (EditText) itemView;
        this.onTextChangeListener = onTextChangeListener;
    }


    public void setText(String text) {
        et.setText(text);
    }

    @Override
    public void setOnComponentContextMenuClickListener(OnComponentMenuClickListener onComponentContextMenuClickListener) {
        this.onComponentMenuClickListener = onComponentContextMenuClickListener;
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
}

