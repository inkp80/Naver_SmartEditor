package com.naver.smarteditor.lesssmarteditor.views.edit;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import com.naver.smarteditor.lesssmarteditor.listener.TextCursorListener;

/**
 * Created by NAVER on 2017. 6. 5..
 */

public class SmartEditText extends EditText {


    boolean mBold = false;
    boolean mItalic = false;
    private StatusManager statusManager = new StatusManager();

    Spannable span = this.getEditableText();
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

            if(span.getStyle() == Typeface.BOLD){
                statusManager.setStyleState(Typeface.BOLD, true);
                Log.d("current", spannable.getSpanStart(span) + ", " + spannable.getSpanEnd(span));
            } else if(span.getStyle() == Typeface.ITALIC){
                statusManager.setStyleState(Typeface.ITALIC, true);
            }
        }

        UnderlineSpan underlineSpans[];
        underlineSpans = spannable.getSpans(selStart, selEnd, UnderlineSpan.class);
        for(UnderlineSpan underlineSpan : underlineSpans){
            styleType |= (1<<3);
            statusManager.setStyleState(Typeface_Underline, true);
            break;
        }

        if (textCursorListener != null) {
            textCursorListener.showSelectedTypes(styleType);
        }
    }

    private final int Typeface_Underline = 3;

    private void removeInvaildSpan(int start, int end){
        Spannable spannable = this.getEditableText();
        StyleSpan[] styleSpan = spannable.getSpans(start, end, StyleSpan.class);
        for(StyleSpan span : styleSpan){
            if(spannable.getSpanStart(span) == spannable.getSpanEnd(span)){
                spannable.removeSpan(span);
            }
        }

        UnderlineSpan[] underlineSpans = spannable.getSpans(start, end, UnderlineSpan.class);
        for(UnderlineSpan underlineSpan : underlineSpans){
            if(spannable.getSpanStart(underlineSpan) == spannable.getSpanEnd(underlineSpan)) {
                spannable.removeSpan(underlineSpan);
            }
        }

        //style, underline ~
    }


    private class StatusManager{
        private boolean bold = false;
        private boolean itlic = false;
        private boolean underline = false;

        private <T> boolean getStyleState(Class typeClass, int typeValue){
            if(typeClass == StyleSpan.class){
                switch (typeValue){
                    case Typeface.BOLD:
                        return bold;
                    case Typeface.ITALIC:
                        return itlic;
                    default:
                        return false;
                }
            } else if(typeClass == UnderlineSpan.class){
                    return underline;
            }
            return false;
        }
        private void setStyleState(int typeValue, boolean state){

            switch (typeValue){
                case Typeface.BOLD:
                    bold = state;
                    break;
                case Typeface.ITALIC:
                    itlic = state;
                    break;
                case Typeface_Underline:
                    underline = state;
                default:
                    break;
            }
        }
    }



    public void editSpannable(Class typeClass, int typeValue){
        removeInvaildSpan(0, this.length());
        getStyleSpan(this.getSelectionStart(), this.getSelectionEnd());
        boolean state = statusManager.getStyleState(typeClass, typeValue);

        Log.d("Status", String.valueOf(typeValue) + ", " + String.valueOf(state));

        if(state){
            Log.d("status", "in true");
            statusManager.setStyleState(typeValue, false);
            adjustSpan(typeClass, typeValue);
            return;
        }else {
            Log.d("status", "in false");
            statusManager.setStyleState(typeValue, true);
            setSpan(typeClass, typeValue);
        }
    }

    private <T> void setSpan(Class<T> typeClass, int typeValue) {
        ClassGenerator<T> classGenerator = new ClassGenerator<>(typeClass);
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
        T[] spans = spannable.getSpans(selStart, selEnd, typeClass);
        Log.d("Size", String.valueOf(spans.length));

        for(T span : spans){

            if(typeValue <= 2) {
                if ((((StyleSpan) span).getStyle() & typeValue) == 0) {

                    Log.d("spanStyleValue", "span:" + String.valueOf(((StyleSpan) span).getStyle()) + ", typeVal:" + String.valueOf(typeValue));
                    continue;
                }
            }

            int spanStart = spannable.getSpanStart(span);
            int spanEnd = spannable.getSpanEnd(span);

            spannable.removeSpan(span);
            ClassGenerator<T> classGenerator = new ClassGenerator<>(typeClass);

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
