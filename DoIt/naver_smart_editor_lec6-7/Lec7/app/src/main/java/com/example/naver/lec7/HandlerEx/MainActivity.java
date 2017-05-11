package com.example.naver.lec7.HandlerEx;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.naver.lec7.R;

/**
 * Created by NAVER on 2017. 4. 27..
 */

public class MainActivity extends AppCompatActivity{

    TextView mProgressText;
    ProgressBar mProgressBar;

    boolean isRunning = false;
    Handler handler;
    ProgressRunnable runnable;


    @Override
    protected void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.handlerex_activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressText = (TextView) findViewById(R.id.progress_text);

//        handler = new Handler();
        handler = new ProgressHandler();
        runnable = new ProgressRunnable();
    }

    @Override
    public void onStart(){
        super.onStart();

        mProgressBar.setProgress(0);

        Thread thread1 = new Thread(new Runnable(){

            @Override
            public void run() {
                try{
                    for(int i=0; i < 20 && isRunning; i++){
                        Thread.sleep(1000);

                        Message msg = handler.obtainMessage();
                        handler.sendMessage(msg);

                        //handler.post(runnable);
                    }
                } catch (Exception e){
                    Log.e("ThreadActivity", "Exception in processing message", e);
                }

            }
        });

        isRunning = true;
        thread1.start();
    }

    @Override
    public void onStop(){
        super.onStop();

        isRunning = false;
    }

    public class ProgressRunnable implements Runnable{
        public  void run(){
            mProgressBar.incrementProgressBy(5);

            if(mProgressBar.getProgress() == mProgressBar.getMax()){
                mProgressText.setText("Runnable done");
            } else {
                mProgressText.setText("Working.. " + mProgressBar.getProgress() );
            }
        }

    }


    public class ProgressHandler extends Handler {

        public void handleMessage(Message msg) {

            mProgressBar.incrementProgressBy(5);

            if (mProgressBar.getProgress() == mProgressBar.getMax()) {
                mProgressText.setText("Done");
            } else {
                mProgressText.setText("Working ..." + mProgressBar.getProgress());
            }

        }

    }
}
