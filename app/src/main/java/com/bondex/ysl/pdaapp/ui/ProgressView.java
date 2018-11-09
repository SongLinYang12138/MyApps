package com.bondex.ysl.pdaapp.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.bondex.ysl.pdaapp.R;

/**
 * date: 2018/11/9
 * Author: ysl
 * description:
 */
public class ProgressView extends View {


    private final int firstRadius = 10;
    private final int secondRadius = 20;
    private  Paint firstPaint;
    private Paint secondPaint;
    private String progress = "";
    private int circleWidth = 6;
    public ProgressView(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setProgress(String progress){
        this.progress = progress;
    }

    private void init(Context context){

        firstPaint =  new Paint();
        secondPaint = new Paint();

        firstPaint.setColor(getResources().getColor(R.color.colorPrimary));
        secondPaint.setColor(getResources().getColor(R.color.text_gray));

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }


    private void drawCircle(Canvas canvas,int center,int radius){



    }


}
