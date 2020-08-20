package com.example.deltaonsite1;

import android.animation.PropertyValuesHolder;
import android.animation.TimeAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

import androidx.annotation.Nullable;

public class Dial extends View {

    Float height,width=0f;
    Integer padding=0;
    Integer font_size=0;
    Integer spacing=0;
    Integer MinSize,Secsize=0;
    Integer radius=0;
    Paint paint;
    boolean initiated=false;
    Rect rect=new Rect();
    ArrayList<Integer> integerArrayList=new ArrayList<>();
    Double sec=0.0;
    Double min=0.0;
    private static final String TAG = "Dial";
    Integer nums=30;
    public ValueAnimator TimerAnimator;



    public Dial(Context context) {
        super(context);
        integerArrayList.clear();
        for(int i=0;i<=30;i++){
            integerArrayList.add(i);
        }
    }

    public Dial(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.sec = 0.0;
        integerArrayList.clear();
        for(int i=0;i<=30;i++){

            integerArrayList.add(i);
        }
        Log.d(TAG, "Dial: "+integerArrayList);
    }

    public void init(){
        height=(float)getMeasuredHeight();
        width=(float)getMeasuredWidth();
        padding=(nums==30)?50:25;
        font_size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics());


        double min=Math.min(height,width);
        radius=(int)min/2-padding;
        MinSize=(int)min/7;

        paint=new Paint(Paint.DITHER_FLAG);
        initiated=true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(!initiated){
            init();
        }

        canvas.drawColor(Color.parseColor("#eeeeee"));
        DrawCircle(canvas);
        drawCenter(canvas);
        drawNumeral(canvas);
        drawHands(canvas,sec,min);








    }

    private void DrawCircle(Canvas c){
        paint.reset();
        paint.setColor(Color.parseColor("#090909"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);
        paint.setStrokeJoin(Paint.Join.MITER);
        paint.setAntiAlias(true);

        c.drawCircle(width/2,height/2,radius+padding-10,paint);

        c.drawCircle(width/2+radius/2,height/2,radius/4,paint);



    }

    private void drawCenter(Canvas c){
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.parseColor("#090909"));
        c.drawCircle(width/2,height/2,10,paint);
        c.drawCircle(width/2+radius/2,height/2,5,paint);
    }

    private void drawNumeral(Canvas c) {
            Double angle = 0.0;
        font_size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics());
            paint.setTextSize(font_size);
            paint.setColor(Color.BLACK);
            paint.setTypeface(Typeface.SANS_SERIF);
            for (Integer integer : integerArrayList) {
                String t = String.valueOf(integer);
                paint.getTextBounds(t, 0, t.length(), rect);
                angle = 2 * Math.PI / 30 * (integer - 7.5);

                Log.d(TAG, "drawNumeral: " + t);

                double x = (width / 2 + Math.cos(angle) * radius - rect.width() + 20);
                double y = (height / 2 + Math.sin(angle) * radius - rect.height() + 40);
                c.drawText(t, (float) x, (float) y, paint);

            }

        Paint paint1=new Paint();
        paint1.setColor(Color.BLACK);
        paint1.setTypeface(Typeface.SANS_SERIF);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(6);
        paint1.setStrokeJoin(Paint.Join.MITER);

        font_size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 6, getResources().getDisplayMetrics());
        paint1.setTextSize(font_size);
            for(int i=0;i<=15;i++){
                String t = String.valueOf(i);
                paint.getTextBounds(t, 0, t.length(), rect);
                angle =2* Math.PI / 15 * ((double) i - 3.75);

                Log.d(TAG, "drawNumeral: " + t);

                double x = (width / 2 + radius/2+ Math.cos(angle) * radius/5 - rect.width()+20);
                double y = (height / 2 + Math.sin(angle) * radius/5 - rect.height()+37);
                c.drawText(t, (float) x, (float) y, paint1);
            }
    }

    private void drawHands(Canvas c,double sec,double min){
        drawHand(c,sec);
        drawMinHand(c,min);
    }

    private void drawHand(Canvas c,double loc){
        double angle=Math.PI*loc/15-Math.PI/2;
        double mini=Math.min(height,width);
        MinSize=(int)mini/7;
        double handRadius=radius-MinSize;
        c.drawLine(width/2,height/2,(float)(width/2+Math.cos(angle)*handRadius),(float)(height/2+Math.sin(angle)*handRadius),paint);

    }

    private void drawMinHand(Canvas c,double min){
        double angle=Math.PI*min/7.5+Math.PI/2;
        double mini=Math.min(height,width);
        MinSize=(int)mini/6;
        double handRadius=radius/4-MinSize;

        c.drawLine(width/2+radius/2,height/2,(float)(width/2+radius/2+Math.cos(angle)*handRadius),(float)(height/2+Math.sin(angle)*handRadius),paint);


    }

    public void setSec(double sec) {
        this.sec = sec;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }

    public void setMin(Double min) {
        this.min = min;
    }
}
