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
        canvas.drawCircle(dp2px(28), dp2px(28), dp2px(28), mWhite10PercentagePaint);
        canvas.drawCircle(dp2px(28), dp2px(28), dp2px(16), mWhite20PercentagePaint);
        canvas.drawCircle(dp2px(28), dp2px(28), dp2px(8), mWhitePaint);
        canvas.drawCircle(dp2px(28), dp2px(28), dp2px(5), mOrangePaint);
    }

    /**
     * dp2px
     *
     * @param values
     * @return
     */
    public int dp2px(int values) {

        float density = getResources().getDisplayMetrics().density;
        return (int) (values * density + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public int sp2px(float spValue) {
        float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
