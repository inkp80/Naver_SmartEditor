package com.example.naver.lec7.Thread;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.naver.lec7.R;

/**
 * Created by NAVER on 2017. 4. 27..
 */

public class MainActivity extends AppCompatActivity {
    TextView mTextView;
    Button mButton;
    int value;


    boolean running = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.tv_fromthread);
        mButton = (Button) findViewById(R.id.bt_thread_start);

        value = 0;

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText("스레드에서 받은 값 : "+ value);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

        running = true;

        Thread thread1 = new BackgroundThread();
        thread1.start();
    }

    @Override
    protected void onPause(){
        super.onPause();

        running = false;
        value = 0;
    }

    class BackgroundThread extends Thread{
        @Override
        public void run(){
            while(running){
                try{
                    Thread.sleep(1000);
                    value++;
//                    mTextView.setText("스레드에서 받은 값 : "+ value);
                } catch (InterruptedException e){
                    Log.e("Thread example", "Exception in thread", e);
                }
            }
        }

    }
}
