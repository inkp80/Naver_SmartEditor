package com.naver.smarteditor.smarteditor_textspannable;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SmartEditor mText;

    Button mBold;
    Button mItalic;

    Button mColor;
    Button mClear;


    boolean textFont = false;


    boolean isBoldSet = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mText = (SmartEditor) findViewById(R.id.et_smarteditor);

        mBold = (Button) findViewById(R.id.bt_bold);
        mItalic = (Button) findViewById(R.id.bt_italic);

        mColor = (Button) findViewById(R.id.bt_background);
        mClear = (Button) findViewById(R.id.bt_clear);

        mText.setTextCursorListener(new TextCursorListener() {
            @Override
            public void showSelectedTypes(int styleType) {
                checkSelectedType(styleType);
            }
        });


        mBold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mText.editSpannable(isBoldSet, StyleSpan.class, Typeface.BOLD);
//                setBold();
            }
        });

        mItalic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setItalic();
            }
        });


        mColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackgroundColor();
            }
        });


        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "clear", Toast.LENGTH_SHORT).show();
                setSpan();
            }
        });
    }

    private void setItalic() {
        Spannable spannable = mText.getText();

        int spanStartPosition = mText.getSelectionStart();
        int spanEndPosition = mText.getSelectionEnd();

        if (!mText.isSelected()) {
            mText.getText().insert(spanStartPosition, " ");
            spanEndPosition = mText.getSelectionEnd();

            mText.clearComposingText();
            mText.setSelection(spanStartPosition, spanEndPosition);
        }

        spannable.setSpan(new StyleSpan(Typeface.ITALIC),
                spanStartPosition, spanEndPosition,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
    }


//  Spannable espan = edit.getText();
//  espan.setSpan(new StyleSpan(Typeface.ITALIC), 1, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//  espan.setSpan(new BackgroundColorSpan(0xffff0000), 8, 11, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//  esapn.setSpan(new UnderlineSpan(), 12, 17, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//  esapn.setSpan(new RelativeSizeSpan(0.5f), 12, 17, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//  esapn.setSpan(new ForegroundColorSpan(0xff0000ff), 12, 17, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);


    private void setBold() {
        Spannable spannable = mText.getText();

        if(isBoldSet) {
            mBold.setText("BOLD(X)");
//            mText.adjustSpan(Typeface.BOLD);
            return;
        }


        int selStartPosition = mText.getSelectionStart();
        int selEndPosition = mText.getSelectionEnd();

        if (selStartPosition == selEndPosition) {
            mText.getText().insert(selStartPosition, " ");
            selEndPosition = mText.getSelectionEnd();

            mText.clearComposingText();
            mText.setSelection(selStartPosition, selEndPosition);
        }

        spannable.setSpan(new StyleSpan(Typeface.BOLD),
                selStartPosition, selEndPosition,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
    }


    private void setBackgroundColor() {
        Spannable spannable = mText.getText();

        if (!mText.isSelected()) {
            int start = mText.getSelectionStart();
            mText.getText().insert(start, " ");
            int end = mText.getSelectionEnd();

            mText.clearComposingText();
            mText.setSelection(0);
            mText.setSelection(start, end);


            spannable.setSpan(new StyleSpan(Typeface.ITALIC), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            return;
        }

        int startPos = mText.getSelectionStart();
        int endPos = mText.getSelectionEnd();
        spannable.setSpan(new StyleSpan(Typeface.ITALIC), startPos, endPos, Spanned.SPAN_INCLUSIVE_INCLUSIVE);


        //TextUtils.con
        // cat(span1, span2);
        //(Spanned) TextUtils.concat(foo, bar, baz)


        //String text = span.subString(0, span.length());
        //List<Span> spans = span.getSpans(0, span.length(), Object.class);
        //https://stackoverflow.com/questions/2730706/highlighting-text-color-using-html-fromhtml-in-android

    }

    StyleSpan typeSpan[];

    private void setSpan() {
        mText.setTypeface(mText.getTypeface(), Typeface.NORMAL);
        textFont = false;
        Spannable spannable;
        spannable = mText.getText();
        typeSpan = spannable.getSpans(0, mText.length(), StyleSpan.class);

        for (StyleSpan span : typeSpan) {
            spannable.removeSpan(span);
        }

        // e = editable text, getEditableText or.. spannable
//        StyleSpan[] ss = mText.getSpans(0, mText.length(),StyleSpan.class);
//        List<StyleSpan> list = new ArrayList<>();
//        Collections.addAll(list, ss);


//
//        for(StyleSpan span : ss){
//            int start = e.getSpanStart(span);
//            int end = e.getSpanEnd(span);
//        }

    }

    public void checkSelectedType(int styleType) {
        mItalic.setText("Italic(X)");
        mBold.setText("BOLD(X)");
        if (styleType == 0) {
            isBoldSet = false;
            return;
        }
        for (int i = 0; i < 8; i++) {
            int isChecked = styleType & (1 << i);
            if (isChecked / 2 == 1) {
                mBold.setText("BOLD(O)");
                isBoldSet = true;
                continue;

            } else if (isChecked / 2 == 2) {
                mItalic.setText("Italic(O)");
                continue;
            }
        }
    }
}
