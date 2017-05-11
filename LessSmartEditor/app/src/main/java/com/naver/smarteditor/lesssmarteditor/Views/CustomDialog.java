package com.naver.smarteditor.lesssmarteditor.Views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.naver.smarteditor.lesssmarteditor.R;


/**
 * Created by NAVER on 2017. 5. 11..
 */

public class CustomDialog extends Dialog {

    private Button mTextButton;
    private Button mImgButton;
    private Button mMapButton;

    private View.OnClickListener mTextButtonClickListener;
    private View.OnClickListener mImgButtonClickListener;
    private View.OnClickListener mMapButtonClickListener;


    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context,
                        View.OnClickListener textButtonListener,
                        View.OnClickListener imgButtonListener,
                        View.OnClickListener mapButtonListener) {

        super(context , android.R.style.Theme_Translucent_NoTitleBar);

        this.mTextButtonClickListener = textButtonListener;
        this.mImgButtonClickListener = imgButtonListener;
        this.mMapButtonClickListener = mapButtonListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_view);
        init();
    }


    public void init(){
        mTextButton = (Button) findViewById(R.id.dialog_text);
        mImgButton = (Button) findViewById(R.id.dialog_img);
        mMapButton = (Button) findViewById(R.id.dialog_map);

        mTextButton.setOnClickListener(mTextButtonClickListener);
        mImgButton.setOnClickListener(mImgButtonClickListener);
        mMapButton.setOnClickListener(mMapButtonClickListener);
    }

}
