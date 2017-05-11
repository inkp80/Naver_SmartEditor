package com.example.naver.lec8;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by NAVER on 2017. 4. 28..
 */

public class SocketTry extends AppCompatActivity {

    Button button;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socket_activity);


        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addr = textView.getText().toString().trim();
                ConnectThread thread = new ConnectThread(addr);
                thread.start();
            }
        });

    }



    public class ConnectThread extends Thread {

        String hostName;

        public ConnectThread(String addr){
            hostName = addr;
        }

        @Override
        public void run() {
            try{
                Log.d("###", "in thread");
                int port = 5001;
                Socket socket = new Socket(hostName, port);

                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.flush();


                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                String obj = (String) inputStream.readObject();
                Log.d("Main", "from server" + obj);

                socket.close();



            } catch (Exception e){
                Log.d("###", "error", e);
            }
        }
    }
}
