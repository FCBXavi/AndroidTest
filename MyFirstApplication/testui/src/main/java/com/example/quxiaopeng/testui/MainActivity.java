package com.example.quxiaopeng.testui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

    final static int LOADING = 0;
    Handler handler;
    private int likestate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ImageView imageView = (ImageView) findViewById(R.id.iv_image);
//        final Drawable drawable = imageView.getDrawable();
//        final Timer timer = new Timer();
//
//        handler = new Handler() {
//            public void handleMessage(Message msg) {
//                if (msg.what == LOADING) {
//                    drawable.setLevel(drawable.getLevel() + 200);
//                    Log.i("MyApplication", "loading");
//                    Toast.makeText(MainActivity.this, "loading", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        };
//
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Message message = handler.obtainMessage();
//                message.what = LOADING;
//                handler.sendMessage(message);
//                Log.i("MyApplication", "loading1");
//                if (drawable.getLevel() >= 10000) {
//                    timer.cancel();
//                }
//            }
//        }, 0, 300);


        final Button ivGet = (Button) findViewById(R.id.iv_get);

        ObjectAnimator largeScaleXAnim = ObjectAnimator.ofFloat(ivGet, "scaleX", 1f, 1.5f, 1f, 0.8f, 1f);
        ObjectAnimator largescaleYAnim = ObjectAnimator.ofFloat(ivGet, "scaleY", 1f, 1.5f, 1f, 0.8f, 1f);

        final AnimatorSet largeAnimatorSet = new AnimatorSet();
        largeAnimatorSet.play(largeScaleXAnim).with(largescaleYAnim);
//        largeAnimatorSet.setInterpolator(new OvershootInterpolator(5));
        largeAnimatorSet.setInterpolator(new LinearInterpolator());
        largeAnimatorSet.setDuration(400);

//        ObjectAnimator smallScaleXAnim = ObjectAnimator.ofFloat(ivGet, "scaleX", 1.5f, 1f);
//        ObjectAnimator smallscaleYAnim = ObjectAnimator.ofFloat(ivGet, "scaleY", 1.5f, 1f);
//        final AnimatorSet smallAnimatorSet = new AnimatorSet();
//        smallAnimatorSet.play(smallScaleXAnim).with(smallscaleYAnim);
//        smallAnimatorSet.setInterpolator(new OvershootInterpolator(5));
//        smallAnimatorSet.setDuration(200);

//        largeAnimatorSet.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                if (likestate == 1) {
//                    ivGet.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_get_press));
//                } else {
//                    ivGet.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_get));
//                }
////                smallAnimatorSet.start();
//            }
//        });

        largeScaleXAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                Log.i("value", "onAnimationUpdate: " + value);
                if (value > 1.4) {
                    if (likestate == 1) {
                        ivGet.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_get_press));
                    } else {
                        ivGet.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_get));
                    }
                }
            }
        });


//        ScaleAnimation scaleAnimation1 = new ScaleAnimation(1.0f, 1.25f, 1.0f, 1.25f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
//        scaleAnimation1.setDuration(200);
//        ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.25f, 1.0f, 1.25f, 1.0f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f );
//        scaleAnimation2.setDuration(200);
//        scaleAnimation2.setStartOffset(200);
//        scaleAnimation2.setInterpolator(new OvershootInterpolator(5));
//        final AnimationSet set = new AnimationSet(true);
//        set.addAnimation(scaleAnimation1);
//        set.addAnimation(scaleAnimation2);
//        scaleAnimation2.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//                if (likestate == 1) {
//                    ivGet.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_get_press));
//                } else {
//                    ivGet.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_get));
//                }
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });


        ivGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (likestate == 0) {
                    likestate = 1;
                } else {
                    likestate = 0;
                }
                largeAnimatorSet.start();
//                ivGet.startAnimation(set);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
