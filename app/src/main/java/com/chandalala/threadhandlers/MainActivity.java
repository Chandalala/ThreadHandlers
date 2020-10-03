package com.chandalala.threadhandlers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Update UI using handlers
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {

            TextView textView = findViewById(R.id.textView);
            textView.setText("Hi Chandalala");

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickMe(View view) {

        // Computations are done this way, but you do not update UI using threads
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                long futureTime = System.currentTimeMillis() + 10_000;
                while (System.currentTimeMillis() < futureTime){
                    synchronized (this){
                        try {
                            wait(futureTime - System.currentTimeMillis());
                        }
                        catch (Exception e){
                            Log.d("msg", e.getMessage());
                        }
                    }
                }
                handler.sendEmptyMessage(0);
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

    }
}
