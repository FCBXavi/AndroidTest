package com.example.quxiaopeng.salesrecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;


/**
 * Created by quxiaopeng on 2017/3/18.
 */

public class SalesLeadsLoadingView extends LoadingView {

    ImageView mIvLoading;

    public SalesLeadsLoadingView(Context context) {
        super(context);
        init(context);
    }

    public SalesLeadsLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SalesLeadsLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_sale_leads_loading, this);
        mIvLoading = (ImageView) findViewById(R.id.iv_loading);
    }

    private void showAnimation() {
        final RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(2000);
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mIvLoading.startAnimation(rotateAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mIvLoading.startAnimation(rotateAnimation);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        showAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mIvLoading.clearAnimation();
    }
}
