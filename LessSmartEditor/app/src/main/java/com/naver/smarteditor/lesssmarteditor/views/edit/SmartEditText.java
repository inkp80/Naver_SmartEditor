package com.naver.smarteditor.lesssmarteditor.views.edit;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.widget.EditText;

import com.naver.smarteditor.lesssmarteditor.data.edit.local.UnderlineCustom;
import com.naver.smarteditor.lesssmarteditor.listener.TextCursorListener;

/**
 * Created by NAVER on 2017. 6. 5..
 */

public class SmartEditText extends EditText {


    public static final int TYPE_BOLD = 1;
    public static final int TYPE_ITALIC = 2;
    public static final int TYPE_UNDERLINE = 3;

    private StatusManager statusManager = new StatusManager();
    TextCursorListener textCursorListener;

    public SmartEditText(Context context) {
        super(context);
    }

    public SmartEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        cleanGarbageSpan(0, this.length());
        if (textCursorListener != null) {
            textCursorListener.OnTextCursorMove(getSelectionSpannableState());
        }
    }


    public void setTextCursorListener(TextCursorListener textCursorListener) {
        this.textCursorListener = textCursorListener;
    }


    public int getSelectionSpannableState() {
        int selStart = this.getSelectionStart();
        int selEnd = this.getSelectionEnd();
        Spannable spannable;
        spannable = this.getText();
        int spannableState = 0;
        
        //check StyleSpan : BOLD, ITALIC
        StyleSpan typeSpan[];
        typeSpan = spannable.getSpans(selStart, selEnd, StyleSpan.class);
        for (StyleSpan span : typeSpan) {
            spannableState |= (1 << span.getStyle());
            statusManager.setStyleState(span.getStyle(), true);
        }


        UnderlineCustom underlineCustoms[];
        underlineCustoms = spannable.getSpans(selStart, selEnd, UnderlineCustom.class);
        for(UnderlineCustom underlineCustom : underlineCustoms){
            spannableState |= (1 << TYPE_UNDERLINE);
            statusManager.setStyleState(TYPE_UNDERLINE, true);
            break;
        }
        
        return spannableState;
    }


    private void cleanGarbageSpan(int start, int end){
        Spannable spannable = this.getEditableText();
        //TODO 다형성에 대해서 - class
        StyleSpan[] styleSpan = spannable.getSpans(start, end, StyleSpan.class);
        for(StyleSpan span : styleSpan){
            if(spannable.getSpanStart(span) == spannable.getSpanEnd(span)){
                spannable.removeSpan(span);
            }
        }

        UnderlineCustom[] underlineCustoms = spannable.getSpans(start, end, UnderlineCustom.class);
        for(UnderlineCustom underlineCustom : underlineCustoms){
            if(spannable.getSpanStart(underlineCustom) == spannable.getSpanEnd(underlineCustom)){
                spannable.removeSpan(underlineCustom);
            }
        }
    }



    public void editSpannable(Class typeClass, int typeValue){
        cleanGarbageSpan(0, this.length());
        getSelectionSpannableState();
        boolean state = statusManager.getStyleState(typeValue);

        if(state){
            statusManager.setStyleState(typeValue, false);
            adjustSpan(typeClass, typeValue);
            return;
        }else {
            statusManager.setStyleState(typeValue, true);
            setSpan(typeClass, typeValue);
        }
    }

    private <T> void setSpan(Class<T> typeClass, int typeValue) {
        SpanClassGenerator<T> spanClassGenerator = new SpanClassGenerator<>(typeClass);
        Spannable spannable = this.getText();

        int selStartPosition = this.getSelectionStart();
        int selEndPosition = this.getSelectionEnd();

        if (selStartPosition == selEndPosition) {
            this.getText().insert(selStartPosition, " ");
            selEndPosition = this.getSelectionEnd();

            this.clearComposingText();
            this.setSelection(selStartPosition, selEndPosition);
        }

        spannable.setSpan(spanClassGenerator.get(typeValue),
                selStartPosition, selEndPosition,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
    }

    public <T> void adjustSpan(Class<T> typeClass, int typeValue){

        int selStart = this.getSelectionStart();
        int selEnd = this.getSelectionEnd();

        Spannable spannable = this.getEditableText();
        T[] spans = spannable.getSpans(selStart, selEnd, typeClass);

        for(T span : spans){

            if(typeClass == StyleSpan.class) {
                if ((((StyleSpan) span).getStyle() & typeValue) == 0) {
                    //현재 처리 중인 스타일과 일치하지 않는 스타일 -
                    continue;
                }
            }

            int spanStart = spannable.getSpanStart(span);
            int spanEnd = spannable.getSpanEnd(span);

            spannable.removeSpan(span);
            SpanClassGenerator<T> spanClassGenerator = new SpanClassGenerator<>(typeClass);

            if(selStart == selEnd) {
                this.getText().insert(selStart, " ");
                this.setSelection(selStart, selStart + 1);


                if (selStart > spanStart) {
                    spannable.setSpan(spanClassGenerator.get(typeValue), spanStart, selStart, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    spannable.setSpan(spanClassGenerator.get(typeValue), selEnd + 1, spanEnd + 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                } else if (selStart == spanEnd){
                    spannable.setSpan(spanClassGenerator.get(typeValue), spanEnd, selStart, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                }

            } else if(selStart >= spanStart && selEnd >= spanEnd){
                spannable.setSpan(spanClassGenerator.get(typeValue), spanStart, selStart, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

            } else if(selStart <= spanStart && selEnd <= spanEnd){
                spannable.setSpan(spanClassGenerator.get(typeValue), selEnd, spanEnd, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }

    }


    private class StatusManager{
        //TODO : 뭔가 더 깔끔한 방법이 있을 거 같다.
        //TODO : Flag 값 문제 해결
        private boolean bold = false;
        private boolean itlic = false;
        private boolean underline = false;

        private boolean getStyleState(int typeValue){
                switch (typeValue) {
                    case Typeface.BOLD:
                        return bold;
                    case Typeface.ITALIC:
                        return itlic;
                    case TYPE_UNDERLINE:
                        return underline;
                    default:
                        return false;
                }
        }


        private void setStyleState(int typeValue, boolean state){

            switch (typeValue){
                case Typeface.BOLD:
                    bold = state;
                    break;
                case Typeface.ITALIC:
                    itlic = state;
                    break;
                case TYPE_UNDERLINE:
                    underline = state;
                default:
                    break;
            }
        }
    }

}
