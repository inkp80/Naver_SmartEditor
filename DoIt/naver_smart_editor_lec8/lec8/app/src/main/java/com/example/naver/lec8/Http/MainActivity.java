package com.example.naver.lec8.Http;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.naver.lec8.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText mEditText;
    Button mButton;
    TextView mTextView;
    Handler hander;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.http_main);

        mEditText = (EditText) findViewById(R.id.et_input_url);
        mButton = (Button) findViewById(R.id.bt_request);
        mTextView = (TextView) findViewById(R.id.tv_results);
        hander = new Handler();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlStr = mEditText.getText().toString();

                ConnectThread thread = new ConnectThread(urlStr);
                thread.start();
            }
        });

    }


    public class ConnectThread extends Thread {

        String url;

        public ConnectThread(String addr){
            url = addr;
        }

        @Override
        public void run() {
            try{
                final String output = request(url);
                hander.post(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText(output);
                    }
                });

            } catch (Exception e){
                Log.d("###", "error", e);
            }
        }

        private String request(String hostName){
            StringBuilder output = new StringBuilder();
            try{
                URL url = new URL(hostName);

                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                if(conn!=null){
                    conn.setConnectTimeout(10000);
                    conn.setRequestMethod("GET");

                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    int resCode = conn.getResponseCode();


                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String line = null;
                    while(true){
                        line = reader.readLine();
                        if(line == null){
                            break;
                        }
                        output.append(line+"\n");
                    }
                    reader.close();
                    conn.disconnect();


                }
            }catch (Exception e){

            }
            return output.toString();
        }
    }
}
