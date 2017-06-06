package com.naver.smarteditor.smarteditor_textspannable;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by NAVER on 2017. 6. 5..
 */

public class SmartEditor extends EditText {


    Spannable span = this.getEditableText();
    TextCursorListener textCursorListener;

    public SmartEditor(Context context) {
        super(context);
    }

    public SmartEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        removeInvaildSpan(0, this.length());
        getStyleSpan(selStart, selEnd);
    }


    public void setTextCursorListener(TextCursorListener textCursorListener) {
        this.textCursorListener = textCursorListener;
    }


    private void getStyleSpan(int selStart, int selEnd) {
        Spannable spannable;
        spannable = this.getText();

        StyleSpan typeSpan[];
        typeSpan = spannable.getSpans(selStart, selEnd, StyleSpan.class);

        int styleType = 0;
        for (StyleSpan span : typeSpan) {

            styleType |= (1 << span.getStyle());

            if(span.getStyle() == 1){
                SETED=true;
                Log.d("current", spannable.getSpanStart(span) + ", " + spannable.getSpanEnd(span));
            }
        }


        if (textCursorListener != null) {
            textCursorListener.showSelectedTypes(styleType);
        }
    }

    private void removeInvaildSpan(int start, int end){
        Spannable spannable = this.getEditableText();
        StyleSpan[] styleSpan = spannable.getSpans(start, end, StyleSpan.class);
        for(StyleSpan span : styleSpan){
            if(spannable.getSpanStart(span) == spannable.getSpanEnd(span)){
                spannable.removeSpan(span);
            }
        }
    }



    boolean SETED = false;
    public void editSpannable(boolean isSet, Class typeClass, int typeValue){
        if(SETED){
            SETED = false;
            adjustSpan(typeClass, typeValue);
            return;
        }else {
            SETED = true;
            setSpan(typeClass, typeValue);
        }
    }

    private <T> void setSpan(Class<T> typeClass, int typeValue) {
        Gen<T> classGenerator = new Gen<>(typeClass);
        Spannable spannable = this.getText();

        int selStartPosition = this.getSelectionStart();
        int selEndPosition = this.getSelectionEnd();

        if (selStartPosition == selEndPosition) {
            this.getText().insert(selStartPosition, " ");
            selEndPosition = this.getSelectionEnd();

            this.clearComposingText();
            this.setSelection(selStartPosition, selEndPosition);
        }

        spannable.setSpan(classGenerator.get(typeValue),
                selStartPosition, selEndPosition,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
    }

    public <T> void adjustSpan(Class<T> typeClass, int typeValue){

        int selStart = this.getSelectionStart();
        int selEnd = this.getSelectionEnd();

        Spannable spannable = this.getEditableText();
        Log.d("Size", String.valueOf(spannable.length()));
        T[] spans = spannable.getSpans(selStart, selEnd, typeClass);
        for(T span : spans){
            if(((StyleSpan)span).getStyle() != 1) {
                return;
            }

            int spanStart = spannable.getSpanStart(span);
            int spanEnd = spannable.getSpanEnd(span);

            spannable.removeSpan(span);
            Gen<T> classGenerator = new Gen<>(typeClass);

            if(selStart == selEnd) {
                this.getText().insert(selStart, " ");
                this.setSelection(selStart, selStart + 1);


                if (selStart > spanStart) {


                    spannable.setSpan(classGenerator.get(typeValue), spanStart, selStart, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    spannable.setSpan(classGenerator.get(typeValue), selEnd + 1, spanEnd + 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                } else if (selStart == spanEnd){
                    spannable.setSpan(classGenerator.get(typeValue), spanEnd, selStart, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                }

            } else if(selStart >= spanStart && selEnd >= spanEnd){
                spannable.setSpan(classGenerator.get(typeValue), spanStart, selStart, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

            } else if(selStart <= spanStart && selEnd <= spanEnd){
                spannable.setSpan(classGenerator.get(typeValue), selEnd, spanEnd, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }

    }


}
