package com.example.quxiaopeng.gesturedetectortest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnTouchListener {

    GestureDetector mGestureDecetor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mGestureDecetor=new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
//                      可以在这里选择要重载的方法
//        });
        mGestureDecetor = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            // 用户轻触触摸屏，由1个MotionEvent ACTION_DOWN触发
            @Override
            public boolean onDown(MotionEvent e) {
                Log.i("gesture", "onDown ");
                Toast.makeText(MainActivity.this, "ondown", Toast.LENGTH_SHORT).show();
                return false;
            }

            /*
            * 用户轻触触摸屏，尚未松开或拖动，由一个1个MotionEvent ACTION_DOWN触发
            * 注意和onDown()的区别，强调的是没有松开或者拖动的状态
            *
            * 而onDown也是由一个MotionEventACTION_DOWN触发的，但是他没有任何限制，
            * 也就是说当用户点击的时候，首先MotionEventACTION_DOWN，onDown就会执行，
            * 如果在按下的瞬间没有松开或者是拖动的时候onShowPress就会执行，如果是按下的时间超过瞬间
            * （这块我也不太清楚瞬间的时间差是多少，一般情况下都会执行onShowPress），拖动了，就不执行onShowPress。
            */
            @Override
            public void onShowPress(MotionEvent e) {
                Log.i("gesture", "onShowPress ");
                Toast.makeText(MainActivity.this, "onShowPress", Toast.LENGTH_SHORT).show();
            }

            // 用户（轻触触摸屏后）松开，由一个1个MotionEvent ACTION_UP触发
            ///轻击一下屏幕，立刻抬起来，才会有这个触发
            //从名子也可以看出,一次单独的轻击抬起操作,当然,如果除了Down以外还有其它操作,那就不再算是Single操作了,所以这个事件 就不再响应
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.i("gesture", "onSingleTabUp ");
                Toast.makeText(MainActivity.this, "onSingleTabUp", Toast.LENGTH_SHORT).show();
                return false;
            }

            // 用户按下触摸屏，并拖动，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE触发
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.i("gesture", "onScroll:" + (e2.getX() - e1.getX()) + "   " + distanceX);
                Toast.makeText(MainActivity.this, "onScroll", Toast.LENGTH_SHORT).show();
                return false;
            }

            // 用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发
            @Override
            public void onLongPress(MotionEvent e) {
                Log.i("gesture", "onLongPress");
                Toast.makeText(MainActivity.this, "onLongPress", Toast.LENGTH_SHORT).show();
            }

            // 用户按下触摸屏、快速移动后松开，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE, 1个ACTION_UP触发
            //            参数解释：
//            e1：第1个ACTION_DOWN MotionEvent
//            e2：最后一个ACTION_MOVE MotionEvent
//            velocityX：X轴上的移动速度，像素/秒
//            velocityY：Y轴上的移动速度，像素/秒
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.i("gesture", "onFling"+(e2.getX() - e1.getX()) + "   " + velocityX);
                Toast.makeText(MainActivity.this, "onFling", Toast.LENGTH_SHORT).show();
                // 判断是向左滑还是向右滑
                // 触发条件 ：
                // X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒
                int FLING_MIN_DISTANCE = 100;
                int FLING_MIN_VELOCITY = 200;
                if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
                        && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                    // Fling left
                    Log.i("gesture", "Fling left");
                    Toast.makeText(MainActivity.this, "Fling Left", Toast.LENGTH_SHORT).show();
                } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
                        && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                    // Fling right
                    Log.i("gesture", "Fling right");
                    Toast.makeText(MainActivity.this, "Fling Right", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        mGestureDecetor.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {

            //OnDown->OnsingleTapUp->OnsingleTapConfirmed
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Log.i("gesture", "onSingleTapConfirme");
                Toast.makeText(MainActivity.this, "onSingleTapConfirmed", Toast.LENGTH_LONG).show();
                return false;
            }

            //双击事件
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Log.i("gesture", "onDoubleTab");
                Toast.makeText(MainActivity.this, "onDoubleTab", Toast.LENGTH_LONG).show();
                return false;
            }

            //双击间隔中发生的动作
            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                Log.i("gesture", "onDoubleTabEvent");
                Toast.makeText(MainActivity.this, "onDoubleTabEvent" + e.getAction(), Toast.LENGTH_LONG).show();
                return false;
            }
        });

        Button button = (Button) findViewById(R.id.btn_test);
        button.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return mGestureDecetor.onTouchEvent(event);
    }
}
