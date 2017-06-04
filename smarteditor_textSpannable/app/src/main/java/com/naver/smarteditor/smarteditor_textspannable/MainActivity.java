package com.naver.smarteditor.smarteditor_textspannable;

import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText mText;
    Button mBold;
    Button mColor;
    Button mClear;
    Button mHtml;


    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textview);
        mText = (EditText) findViewById(R.id.et_text);
        mBold = (Button) findViewById(R.id.bt_bold);
        mColor = (Button) findViewById(R.id.bt_background);
        mClear = (Button) findViewById(R.id.bt_clear);
        mHtml = (Button) findViewById(R.id.bt_make_html);

        mBold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBold();
            }
        });

        mColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackgroundColor();
            }
        });

        mHtml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value;
                if(Build.VERSION.SDK_INT >= 24) {
                    value = Html.toHtml(mText.getText(), Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE);
                } else {
                    value = Html.toHtml(mText.getText());
                }

                Log.d("HTML", value);
                textView.setText(Html.fromHtml(value));
            }
        });

    }



//    Spannable espan = edit.getText();
//  espan.setSpan(new StyleSpan(Typeface.ITALIC), 1, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//  espan.setSpan(new BackgroundColorSpan(0xffff0000), 8, 11, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//  esapn.setSpan(new UnderlineSpan(), 12, 17, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//  esapn.setSpan(new RelativeSizeSpan(0.5f), 12, 17, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//  esapn.setSpan(new ForegroundColorSpan(0xff0000ff), 12, 17, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);


    private void setBold(){
        int startPos = mText.getSelectionStart();
        int endPos = mText.getSelectionEnd();

        Log.d("MainActivity", "start pos : " + String.valueOf(startPos) + ", end pos : " + String.valueOf(endPos));

        Spannable spannable = mText.getText();
        spannable.setSpan(new StyleSpan(Typeface.BOLD), startPos, endPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannable);
    }

    private void setBackgroundColor(){

        int startPos = mText.getSelectionStart();
        int endPos = mText.getSelectionEnd();

        Spannable spannable = mText.getText();
        spannable.setSpan(new BackgroundColorSpan(0xffff0000), startPos,endPos, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        //TextUtils.concat(span1, span2);
        //(Spanned) TextUtils.concat(foo, bar, baz)


        //String text = span.subString(0, span.length());
        //List<Span> spans = span.getSpans(0, span.length(), Object.class);


        //https://stackoverflow.com/questions/2730706/highlighting-text-color-using-html-fromhtml-in-android
        
    }


}
