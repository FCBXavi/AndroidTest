package com.example.quxiaopeng.salesrecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by quxiaopeng on 2017/3/20.
 */

public class RefreshFooterView extends LinearLayout {

    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_LOADING = 2;
    public final static int STATE_NO_MORE_DATA = 3;

    private Context mContext;
    private int mState;
    private View mContentView;
    private View mLayoutLoading;
    private TextView mtvHint;

    public RefreshFooterView(Context context) {
        super(context);
        init(context);
    }

    public RefreshFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RefreshFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.layout_sale_leads_footer, null);
        addView(moreView);
        moreView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        mContentView = moreView.findViewById(R.id.layout_footer_content);
        mLayoutLoading = moreView.findViewById(R.id.layout_loading);
        mtvHint = (TextView) moreView.findViewById(R.id.tv_hint);
    }

    public void setState(int state) {
        mState = state;
        if (state == STATE_READY) {
            mLayoutLoading.setVisibility(View.GONE);
            mtvHint.setVisibility(View.VISIBLE);
            mtvHint.setText("松开载入更多");
        } else if (state == STATE_LOADING) {
            mtvHint.setVisibility(View.GONE);
            mLayoutLoading.setVisibility(View.VISIBLE);
        } else if (state == STATE_NO_MORE_DATA) {
            mLayoutLoading.setVisibility(View.GONE);
            mtvHint.setVisibility(VISIBLE);
            mtvHint.setText("暂无更多");
        } else {
            mLayoutLoading.setVisibility(View.GONE);
            mtvHint.setVisibility(View.VISIBLE);
            mtvHint.setText("查看更多");
        }
    }

    public void setBottomMargin(int height) {
        if (height < 0) return;
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        lp.bottomMargin = height;
        mContentView.setLayoutParams(lp);
    }

    public int getBottomMargin() {
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        return lp.bottomMargin;
    }

    /**
     * hide footer when disable pull load more
     */
    public void hide() {
        mContentView.setVisibility(View.GONE);
    }

    /**
     * show footer
     */
    public void show() {
        mContentView.setVisibility(View.VISIBLE);
    }

}
