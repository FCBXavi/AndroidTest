package com.example.quxiaopeng.androidcharttest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by quxiaopeng on 2017/1/5.
 */

public class MarkerView extends View {

    private Paint mWhite10PercentagePaint;
    private Paint mWhite20PercentagePaint;
    private Paint mWhitePaint;
    private Paint mOrangePaint;

    private float radius;

    public MarkerView(Context context) {
        super(context);
        init();
    }

    public MarkerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MarkerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
    }

    private void init() {
        mWhite10PercentagePaint = new Paint();
        mWhite10PercentagePaint.setColor(Color.argb(25,255,255,255));
        mWhite20PercentagePaint = new Paint();
        mWhite20PercentagePaint.setColor(Color.argb(51,255,255,255));
        mWhitePaint = new Paint();
        mWhitePaint.setColor(Color.WHITE);
        mOrangePaint = new Paint();
        mOrangePaint.setColor(Color.rgb(255,100,80));
    }
    private void drawCircle(Canvas canvas) {
//        canvas.drawCircle(dp2px(28), dp2px(28), dp2px(28), mWhite10PercentagePaint);
//        canvas.drawCircle(dp2px(28), dp2px(28), dp2px(16), mWhite20PercentagePaint);
//        canvas.drawCircle(dp2px(28), dp2px(28), dp2px(8), mWhitePaint);
//        canvas.drawCircle(dp2px(28), dp2px(28), dp2px(5), mOrangePaint);
        canvas.drawCircle(radius, radius, radius, mWhite10PercentagePaint);
        canvas.drawCircle(radius, radius, radius/1.75f, mWhite20PercentagePaint);
        canvas.drawCircle(radius, radius, radius/3.5f, mWhitePaint);
        canvas.drawCircle(radius, radius, radius/5.6f, mOrangePaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureWidth(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = dp2px(56);
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        radius = result/2;
        return result;
    }


    public int dp2px(int values) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (values * density + 0.5f);
    }

}
