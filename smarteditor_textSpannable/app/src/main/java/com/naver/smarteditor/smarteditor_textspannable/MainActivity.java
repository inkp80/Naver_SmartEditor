package com.naver.smarteditor.smarteditor_textspannable;

import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText mText;
    Button mBold;
    Button mColor;
    Button mClear;
    Button mHtml;

    EditText mResultEditText;


    TextView textView;

    boolean textFont = false;

    String results;

    int startPosition, endPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mResultEditText = (EditText) findViewById(R.id.result_et);
        textView = (TextView) findViewById(R.id.textview);
        mText = (EditText) findViewById(R.id.et_text);
        mBold = (Button) findViewById(R.id.bt_bold);
        mColor = (Button) findViewById(R.id.bt_background);
        mClear = (Button) findViewById(R.id.bt_clear);
        mHtml = (Button) findViewById(R.id.bt_make_html);



        mBold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                textFont = true;
                setBold();
            }
        });



        mColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackgroundColor();
            }
        });


        mText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("start, end", String.valueOf(start)+", "+String.valueOf(count));
                Log.d("charSeq", s.toString());
//                return;
//                if(textFont){
//                    Spannable spannable = mText.getText();
//                    spannable.setSpan(new StyleSpan(Typeface.BOLD), mText.getSelectionStart(), mText.getSelectionEnd(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                }

            }

            @Override
            public void afterTextChanged(Editable s) {

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



//  Spannable espan = edit.getText();
//  espan.setSpan(new StyleSpan(Typeface.ITALIC), 1, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//  espan.setSpan(new BackgroundColorSpan(0xffff0000), 8, 11, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//  esapn.setSpan(new UnderlineSpan(), 12, 17, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//  esapn.setSpan(new RelativeSizeSpan(0.5f), 12, 17, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//  esapn.setSpan(new ForegroundColorSpan(0xff0000ff), 12, 17, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);



    private void setBold(){


        Spannable spannable = mText.getText();


        if(!mText.isSelected()){
            int start = mText.getSelectionStart();
            mText.getText().insert(start, " ");
            int end = mText.getSelectionEnd();

            mText.clearComposingText();
            mText.setSelection(0);
            mText.setSelection(start, end);
            spannable.gets
            spannable.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            return;
        }



        int startPos = mText.getSelectionStart();
        Log.d("sstart", String.valueOf(startPos));
        int endPos = mText.getSelectionStart();
        Log.d("eend", String.valueOf(endPos));
        spannable.setSpan(new StyleSpan(Typeface.BOLD), mText.getSelectionStart(), mText.getSelectionEnd(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);


    }

    private void setBackgroundColor(){
        Spannable spannable = mText.getText();

        if(!mText.isSelected()){
            int start = mText.getSelectionStart();
            mText.getText().insert(start, " ");
            int end = mText.getSelectionEnd();

            mText.clearComposingText();
            mText.setSelection(0);
            mText.setSelection(start, end);

//            mText.setSelection(start);

            spannable.setSpan(new StyleSpan(Typeface.ITALIC), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            return;
        }

        int startPos = mText.getSelectionStart();
        int endPos = mText.getSelectionEnd();
        spannable.setSpan(new StyleSpan(Typeface.ITALIC), startPos, endPos, Spanned.SPAN_INCLUSIVE_INCLUSIVE);


//        spannable.setSpan(new BackgroundColorSpan(0xffff0000), startPos,endPos, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        //TextUtils.con
        // cat(span1, span2);
        //(Spanned) TextUtils.concat(foo, bar, baz)


        //String text = span.subString(0, span.length());
        //List<Span> spans = span.getSpans(0, span.length(), Object.class);


        //https://stackoverflow.com/questions/2730706/highlighting-text-color-using-html-fromhtml-in-android
        
    }

    StyleSpan typeSpan[];

    private void setSpan(){  mText.setTypeface(mText.getTypeface(), Typeface.NORMAL);
        textFont = false;
        Spannable spannable;
        spannable = mText.getText();
        typeSpan = spannable.getSpans(0, mText.length(), StyleSpan.class);

        for(StyleSpan span : typeSpan){
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


        mResultEditText.setText(mText.getText().toString());

        Spannable spanTest = mResultEditText.getText();

        for(int i=0; i < typeSpan.length; i++) {
//            spanTest.setSpan();
        }


//
//        color delete
//        BackgroundColorSpan[] spans=spannable.getSpans(0,
//                spannable.length(),
//                BackgroundColorSpan.class);
//
//        for (BackgroundColorSpan span : spans) {
//            spannable.removeSpan(span);
//        }


        int startPos = mText.length();
        int endPos = mText.length();
    }


}
