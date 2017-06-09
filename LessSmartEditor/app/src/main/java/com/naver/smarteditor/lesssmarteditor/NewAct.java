package com.naver.smarteditor.lesssmarteditor;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.naver.smarteditor.lesssmarteditor.data.edit.local.UnderlineCustom;

/**
 * Created by NAVER on 2017. 6. 8..
 */

public class NewAct extends AppCompatActivity {

    Button button;
    EditText editText;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.tete);

        button = (Button) findViewById(R.id.bt_test);
        editText = (EditText) findViewById(R.id.et_test);
        textView = (TextView) findViewById(R.id.text11);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUnder();
            }
        });



    }

    void setUnder(){
        Spannable spannable = editText.getText();
        spannable.setSpan(new ItalicSpan(Typeface.BOLD), 0, editText.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
    }
}
