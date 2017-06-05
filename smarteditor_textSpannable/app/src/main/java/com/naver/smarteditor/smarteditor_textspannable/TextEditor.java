package com.naver.smarteditor.smarteditor_textspannable;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by NAVER on 2017. 6. 5..
 */

public class TextEditor extends android.support.v7.widget.AppCompatEditText {
    TextCursorListener textCursorListener;

    public TextEditor(Context context) {
        super(context);
    }

    public TextEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        textCursorListener.postionSetup(selStart, selEnd);
        Toast.makeText(getContext(), "selStart is " + selStart + "selEnd is " + selEnd, Toast.LENGTH_LONG).show();
    }

    public void setTextCursorListener(TextCursorListener textCursorListener){
        this.textCursorListener = textCursorListener;
    }
}
