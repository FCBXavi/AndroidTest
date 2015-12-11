package com.example.quxiaopeng.painttest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by quxiaopeng on 15/8/28.
 */
public class MyView extends View {

    private Path path=new Path();
    private Paint circlePaint=new Paint();
    private float x=0f;
    private float y=0f;

    public MyView(Context context,AttributeSet set){
        super(context, set);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //把整张画布绘制成白色
        canvas.drawColor(Color.WHITE);

        Paint paint=new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.FILL);

        //为Paint设置渐变器
        Shader shader=new LinearGradient(0,0,20,20,new int[]{Color.RED,Color.YELLOW,Color.GREEN,Color.BLUE},null,Shader.TileMode.REPEAT);
        paint.setShader(shader);
        paint.setShadowLayer(45,10,10,Color.GRAY);

        //绘制圆形
        canvas.drawCircle(40, 40, 30, paint);
        //绘制正方形
        canvas.drawRect(10,80,70,140,paint);
        //绘制矩形
        canvas.drawRect(10,150,130,210,paint);

        //绘制圆角矩形
        RectF rectF1=new RectF(10,220,130,280);
        canvas.drawRoundRect(rectF1,15,15,paint);

        //绘制椭圆
        RectF rectF2=new RectF(10,290,130,350);
        canvas.drawOval(rectF2,paint);

        //定义一个Path对象,封闭成一个三角形
        Path path1=new Path();
        path1.moveTo(10, 360);
        path1.lineTo(70, 360);
        path1.lineTo(40, 420);
        canvas.drawPath(path1, paint);
       // canvas.drawLines(new float[]{0,0,50,50,400,400,30,20,70,90,100,30,40,80,260,220},paint);

        Paint linePaint=new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.RED);
        linePaint.setStrokeWidth(3);
        canvas.drawPath(path, linePaint);


        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(3);
        circlePaint.setColor(Color.GREEN);
        canvas.drawCircle(x,y,20,circlePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        x=event.getX();
        y=event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x,y);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x,y);
                break;
        }
        invalidate();
        return super.onTouchEvent(event);

    }
}
