package com.example.quxiaopeng.salesrecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by quxiaopeng on 2017/3/18.
 */

public class RefreshHeaderView extends LinearLayout {

    private LinearLayout mContainer;
    private LoadingView mLoadingView;
    private TextView mTvHint;
    private ImageView mIvCompleted;

    private int mState = STATE_NORMAL;

    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_REFRESHING = 2;
    public final static int STATE_SUCCESS = 3;
    public static final int STATE_FRESH_FAILT = 4;

    public RefreshHeaderView(Context context) {
        super(context);
//        mLoadingView = view;
        init(context);
    }

    public RefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setLoadView(LoadingView view) {
        mLoadingView = view;
        invalidate();
    }

    private void init(Context context) {
        // 初始情况，设置下拉刷新view高度为0
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        mContainer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layout_sale_leads_header, null);
        addView(mContainer,lp);
        setGravity(Gravity.BOTTOM);
        mLoadingView = (LoadingView) findViewById(R.id.view_loading);
        mTvHint = (TextView) findViewById(R.id.tv_hint);
        mIvCompleted = (ImageView) findViewById(R.id.iv_completed);

    }

    public void setState(int state) {
        if (state == mState) return;

        switch (state) {
            case STATE_NORMAL:
                mTvHint.setText("下拉刷新 换一批企业");
                mLoadingView.setVisibility(VISIBLE);
                mIvCompleted.setVisibility(GONE);
                break;
            case STATE_READY:
                mTvHint.setText("松开手指 换一批企业");
                mLoadingView.setVisibility(VISIBLE);
                mIvCompleted.setVisibility(GONE);
                break;
            case STATE_REFRESHING:
                mTvHint.setText("推荐中...");
                mLoadingView.setVisibility(VISIBLE);
                mIvCompleted.setVisibility(GONE);
                break;
            case STATE_SUCCESS:
                setVisiableHeight(dip2px(60));
                mTvHint.setText("推荐成功");
                mLoadingView.setVisibility(INVISIBLE);
                mIvCompleted.setVisibility(VISIBLE);
                break;
            case STATE_FRESH_FAILT:
                setVisiableHeight(dip2px(60));
                mTvHint.setText("推荐失败");
                mLoadingView.setVisibility(INVISIBLE);
                mIvCompleted.setVisibility(GONE);
                break;
            default:
        }
        mState = state;
    }

    public void setVisiableHeight(int height) {
        if (height < 0)
            height = 0;
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
//        mContainer.setLayoutParams(lp);
    }

    public int getVisiableHeight() {
        return mContainer.getLayoutParams().height;
    }

    /**
     * 将dip转为px
     */
    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
