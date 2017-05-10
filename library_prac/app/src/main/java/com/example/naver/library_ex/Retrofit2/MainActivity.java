package com.example.naver.library_ex.Retrofit2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.naver.library_ex.R;

/**
 * Created by NAVER on 2017. 5. 10..
 *
 *
 * Retrofit은 Square가 제공하는 안드로이드와 Java 애플리케이션을 위한 라이브러리로,
 * 안전한 타입(type-safe) 방식의 HTTP 클라이언트입니다.
 *
 * 안전한 타입 방식의 HTTP 클라이언트이므로 URL 생성이나 매개 변수의 설정 등을 걱정할 필요 없이 네트워크로 보낼 쿼리 문법만 살펴보면 됩니다.
 * Retrofit을 사용하면 몇몇 인터페이스를 작성하는 것으로 이런 작업을 정말 쉽게 할 수 있습니다.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrofit2_main);
    }
}
