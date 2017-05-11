package com.example.naver.lec6.CustomView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.naver.lec6.CustomViewDrawable.CustomViewDrawable;
import com.example.naver.lec6.CustomViewImage.CustomViewImage;
import com.example.naver.lec6.CustomViewStyle.CustomViewStyle;
import com.example.naver.lec6.R;
import com.example.naver.lec6.SurfaceView.PaintBoardSurface;

/**
 * Created by NAVER on 2017. 4. 26..
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        CustomView myView = new CustomView(this);
//        setContentView(myView);


//        CustomViewStyle myView = new CustomViewStyle(this);
//        setContentView(myView);

//        CustomViewDrawable myView = new CustomViewDrawable(this);
//        setContentView(myView);

//        CustomViewImage myView = new CustomViewImage(this);
//        setContentView(myView);

        PaintBoardSurface paintBoardSurface = new PaintBoardSurface(this);
        setContentView(paintBoardSurface);
    }
}
