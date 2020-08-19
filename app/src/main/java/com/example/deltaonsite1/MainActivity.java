package com.example.deltaonsite1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;


public class MainActivity extends AppCompatActivity {

    Chronometer chronometer;
    long medianOffset = 0;
    Button start, reset, pause;
    boolean run = false;
    Dial dial, dial2;
    Handler handler;
    Runnable runnable;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer);
        start = findViewById(R.id.start);
        reset = findViewById(R.id.reset);
        pause = findViewById(R.id.pause);
        dial = findViewById(R.id.dial);
        dial2 = findViewById(R.id.dial2);


        dial.setSec(0);
        dial.setNums(30);
        dial.invalidate();


        dial2.setSec(0);
        dial2.setNums(15);
        dial2.invalidate();



        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                int timeCount = (int) (SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000;
                int minSet = timeCount / 60;
                int min = minSet % 15;
                int sec = timeCount % 30;

                dial.setSec(sec);
                dial.invalidate();


                dial2.setSec(min);
                dial2.invalidate();






            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!run) {
                    chronometer.setBase(SystemClock.elapsedRealtime() - medianOffset);
                    chronometer.start();
                    run = true;
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.stop();
                medianOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                run = false;
            }
        });


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.stop();
                chronometer.setBase(SystemClock.elapsedRealtime());
                medianOffset = 0;
                run = false;

            }
        });
    }

}