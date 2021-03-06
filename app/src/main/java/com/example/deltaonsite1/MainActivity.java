package com.example.deltaonsite1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
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
    private static final String TAG = "MainActivity";
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer);
        start = findViewById(R.id.start);
        reset = findViewById(R.id.reset);
        pause = findViewById(R.id.pause);
        dial = findViewById(R.id.dial);




        dial.setSec(0.0);
        dial.setNums(30);
        dial.setMin(0.0);
        dial.invalidate();






        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                int timeCount = (int) (SystemClock.elapsedRealtime() - chronometer.getBase())/1000;
                int minSet = timeCount / 60;
                final int min = minSet % 15;
                final int sec1=timeCount%60;
                final int sec = (timeCount % 30);
                    countDownTimer = new CountDownTimer(1000, 1) {
                        @Override
                        public void onTick(long l) {
                            Log.d(TAG, "onTick: " + l);
                            dial.setSec((sec + (1000 - l) * 0.001));
                            Log.d(TAG, "onTick: "+min);
                            dial.setMin((double) (min+(sec1 + (1000 - l) * 0.001)/60));
                            dial.invalidate();
                        }

                        @Override
                        public void onFinish() {

                        }
                    }.start();









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
                countDownTimer.cancel();
                run = false;
            }
        });


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                chronometer.stop();
                chronometer.setBase(SystemClock.elapsedRealtime());
                medianOffset = 0;
                run = false;
                dial.setMin(0.0);
                dial.setSec(0.0);
                dial.invalidate();

                countDownTimer.cancel();



            }
        });
    }

}