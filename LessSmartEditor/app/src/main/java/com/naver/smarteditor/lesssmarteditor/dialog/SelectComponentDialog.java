package com.naver.smarteditor.lesssmarteditor.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.naver.smarteditor.lesssmarteditor.R;


/**
 * Created by NAVER on 2017. 5. 11..
 */

public class SelectComponentDialog extends Dialog {

    private Button mTextButton;
    private Button mImgButton;
    private Button mMapButton;

    private View.OnClickListener mTextButtonClickListener;
    private View.OnClickListener mImgButtonClickListener;
    private View.OnClickListener mMapButtonClickListener;


    public SelectComponentDialog(Context context) {
        super(context);
    }

    public SelectComponentDialog(Context context,
                                 View.OnClickListener textButtonListener,
                                 View.OnClickListener imgButtonListener,
                                 View.OnClickListener mapButtonListener) {

        super(context, android.R.style.Theme_Translucent_NoTitleBar);

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
        getWindow().setGravity(Gravity.CENTER_VERTICAL);

        setContentView(R.layout.dialog_view);
        init();
    }


    public void init(){
        mTextButton = (Button) findViewById(R.id.dialog_selcomp_bt_text);
        mImgButton = (Button) findViewById(R.id.dialog_selcomp_bt_img);
        mMapButton = (Button) findViewById(R.id.dialog_selcomp_bt_map);

        mTextButton.setOnClickListener(mTextButtonClickListener);
        mImgButton.setOnClickListener(mImgButtonClickListener);
        mMapButton.setOnClickListener(mMapButtonClickListener);
    }

}
