package com.example.quxiaopeng.bitmaphandle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by quxiaopeng on 2017/1/20.
 */

public class ReflectView extends View {
    private Paint mPaint;
    private PorterDuffXfermode mXfermode;
    private Bitmap mSrcBitmap;
    private Bitmap mRefBitmap;

    public ReflectView(Context context) {
        super(context);
        init();
    }

    public ReflectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ReflectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mSrcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.erzong);
        Matrix matrix = new Matrix();
        matrix.setScale(1f, -1f);
        mRefBitmap = Bitmap.createBitmap(mSrcBitmap, 0, 0, mSrcBitmap.getWidth(), mSrcBitmap.getHeight(), matrix, true);
        mPaint = new Paint();
        mPaint.setShader(new LinearGradient(0, mSrcBitmap.getHeight(), 0, mSrcBitmap.getHeight() + mSrcBitmap.getHeight() / 4, 0XDD000000, 0X10000000, Shader.TileMode.CLAMP));
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(mSrcBitmap, 0, 0, null);
        canvas.drawBitmap(mRefBitmap, 0, mSrcBitmap.getHeight(), null);
        mPaint.setXfermode(mXfermode);
        canvas.drawRect(0, mSrcBitmap.getHeight(), mRefBitmap.getWidth(), mSrcBitmap.getHeight() * 2, mPaint);
        mPaint.setXfermode(null);

    }
}
