package com.example.quxiaopeng.salesrecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * Created by quxiaopeng on 2017/3/20.
 */

public class RefreshRecyclerView extends RecyclerView {

    private Scroller mScroller;
    private RefreshRecyclerViewAdapter mAdapter;
    private RefreshHeaderView mRefreshHeader;
    private RefreshFooterView mRefreshFooter;
    private boolean canLoad;
    private boolean canRefresh = true;
    private boolean mPullLoad;
    private boolean mPullRefreshing;//是否正在刷新
    private boolean mPullLoading;//是否正在加载更多

    private View mHeaderView;
    private int mScrollBack;
    private float mLastY;//上一次Y 值
    private LinearLayoutManager mLayoutManager;

    private RelativeLayout mHeaderViewContent;


    private int mRefreshHeaderHeight;
    private RecyclerViewRefreshListener mOnRefreshListener;

    private final static int SCROLLBACK_HEADER = 4;
    private final static int SCROLLBACK_FOOTER = 3;

    private final static float OFFSET_RADIO = 1.8f;
    private final static int PULL_LOAD_MORE_DELTA = 150;
    private final static int SCROLL_DURATION = 400;

    public RefreshRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public RefreshRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RefreshRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(getContext(), new DecelerateInterpolator());
        mRefreshHeader = new RefreshHeaderView(context);
        mRefreshFooter = new RefreshFooterView(context);
        mHeaderViewContent = (RelativeLayout) mRefreshHeader.findViewById(R.id.rl_header_content);
        mRefreshHeader.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mRefreshHeaderHeight = mHeaderViewContent.getHeight();
                Log.i("size", "onGlobalLayout: " + mRefreshHeaderHeight + "，" + mHeaderViewContent.getWidth());
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        mLayoutManager = new LinearLayoutManager(getContext());
        setLayoutManager(mLayoutManager);
    }


    private void startLoadMore() {
        if (mOnRefreshListener != null) {
            mOnRefreshListener.onLoadMore();
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mAdapter = new RefreshRecyclerViewAdapter(getContext(), adapter);
        mAdapter.setRefreshHeader(mRefreshHeader);
        mAdapter.setRefreshFooter(mRefreshFooter);
        if (mHeaderView != null) {
            mAdapter.setHeaderView(mHeaderView);
        }
        mAdapter.setRefresh(canRefresh);
        mAdapter.setLoadMore(canLoad);
        super.setAdapter(mAdapter);
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        if (mAdapter != null) {
            mAdapter.setHeaderView(headerView);
        }
    }

    /**
     * 用于重置头或尾的高度
     */
    @Override
    public void computeScroll() {
        if (mScroller != null && mScroller.computeScrollOffset()) {
            if (mScrollBack == SCROLLBACK_HEADER) {
                mRefreshHeader.setVisiableHeight(mScroller.getCurrY());
            } else {
                mRefreshFooter.setBottomMargin(mScroller.getCurrY());
            }
            postInvalidate();
        }
        super.computeScroll();
    }

    /**
     * notification的时候调用
     */
    @Override
    public void requestLayout() {
        super.requestLayout();
        if (mRefreshFooter == null || mAdapter == null) {
            return;
        }
        boolean b = mAdapter.getItemCount() <= (mAdapter.getHeaderAndFooterCount());
        if (b) {
            mRefreshFooter.hide();
        } else {
            mRefreshFooter.show();
        }
        if (!canLoad) {
            mRefreshFooter.hide();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (mLastY == -1 || mLastY == 0) {
            mLastY = e.getRawY();
        }

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = e.getRawY() - mLastY;
                mLastY = e.getRawY();
                if (canRefresh && !mPullLoading && mLayoutManager.findFirstVisibleItemPosition() <= 1
                        && (mRefreshHeader.getVisiableHeight() > 0 || moveY > 0)) {
                    updateHeaderHeight(moveY / OFFSET_RADIO);
                } else if (canLoad && !mPullRefreshing &&
                        mLayoutManager.findLastVisibleItemPosition() == mAdapter.getItemCount() - 1 &&
                        (mRefreshFooter.getBottomMargin() > 0 || moveY < 0) &&
                        mAdapter.getItemCount() > 0) {
                    updateFooterHeight(-moveY / OFFSET_RADIO);
                }
                break;
            case MotionEvent.ACTION_UP:
                mLastY = -1; // reset
                if (!mPullRefreshing && mLayoutManager.findFirstVisibleItemPosition() == 0) {
                    // invoke refresh
                    if (canRefresh && mRefreshHeader.getVisiableHeight() > mRefreshHeaderHeight) {
                        mPullRefreshing = true;
                        mRefreshHeader.setState(RefreshHeaderView.STATE_REFRESHING);
                        if (mOnRefreshListener != null) {
                            mOnRefreshListener.onRefresh();
                        }
                    }

                }
                if (canLoad && mPullLoading && mLayoutManager.findLastVisibleItemPosition() == mAdapter.getItemCount() - 1
                        && mRefreshFooter.getBottomMargin() > PULL_LOAD_MORE_DELTA
                        ) {
                    mRefreshFooter.setState(RefreshFooterView.STATE_LOADING);
                    mPullLoad = true;
                    startLoadMore();
                }
                resetHeaderHeight();
                resetFooterHeight();
        }

        return super.onTouchEvent(e);
    }

    public void refresh() {
        mPullRefreshing = true;
        mScrollBack = SCROLLBACK_HEADER;
        mRefreshHeader.setState(RefreshHeaderView.STATE_REFRESHING);
        scrollToPosition(0);
        mScroller.startScroll(0, 0, 0, mRefreshHeaderHeight);
        Log.i("mRefreshHeaderHeight", "refresh: " + mRefreshHeaderHeight);
        invalidate();
        if (mOnRefreshListener != null) {
            mOnRefreshListener.onRefresh();
        }
//        resetHeaderHeight();
    }

    /**
     * 更新刷新头高度
     *
     * @param delta
     */
    private void updateHeaderHeight(float delta) {
        mRefreshHeader.setVisiableHeight((int) delta + mRefreshHeader.getVisiableHeight());
        if (canRefresh && !mPullRefreshing) { // 未处于刷新状态，更新箭头
            if (mRefreshHeader.getVisiableHeight() > mRefreshHeaderHeight) {
                mRefreshHeader.setState(RefreshHeaderView.STATE_READY);
            } else {
                mRefreshHeader.setState(RefreshHeaderView.STATE_NORMAL);
            }
        }
    }

    /**
     * reset header view's height.
     */
    private void resetHeaderHeight() {
        final int height = mRefreshHeader.getVisiableHeight();
        if (height == 0)
            return;
        if (mPullRefreshing && height <= mRefreshHeaderHeight) {
            return;
        }
        int finalHeight = 0;
        if (mPullRefreshing && height > mRefreshHeaderHeight) {
            finalHeight = mRefreshHeaderHeight;
        }

        mScrollBack = SCROLLBACK_HEADER;
        mScroller.startScroll(0, height, 0, finalHeight - height,
                SCROLL_DURATION);
        invalidate();
    }

    private void resetFooterHeight() {
        int bottomMargin = mRefreshFooter.getBottomMargin();
        if (bottomMargin > 0) {
            mScrollBack = SCROLLBACK_FOOTER;
            mScroller.startScroll(0, bottomMargin, 0, -bottomMargin,
                    SCROLL_DURATION);
            invalidate();
        }
    }

    public void setCanRefresh(boolean canRefresh) {
        this.canRefresh = true;
    }

    public void setCanLoad(boolean canLoad) {
        this.canLoad = true;
    }

    public void setRefreshListener(RecyclerViewRefreshListener listener) {
        this.mOnRefreshListener = listener;
    }

    public void stopRefresh(boolean isSuccess) {
//        lfAdapter.notifyDataSetChanged();
        if (mPullRefreshing) {
            if (isSuccess) {
                mRefreshHeader.setState(RefreshHeaderView.STATE_SUCCESS);
            } else {
                mRefreshHeader.setState(RefreshHeaderView.STATE_FRESH_FAILT);
            }
            mRefreshHeader.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPullRefreshing = false;
                    resetHeaderHeight();
                }
            }, 1000);

        }
    }

    /**
     * stop load more, reset footer view.
     */
    public void stopLoadMore(boolean hasData) {
//        lfAdapter.notifyDataSetChanged();
        if (mPullLoading) {
            mPullLoad = false;
            mPullLoading = false;
            if (hasData) {
                mRefreshFooter.setState(RefreshFooterView.STATE_NORMAL);
            } else {
                mRefreshFooter.setState(RefreshFooterView.STATE_NO_MORE_DATA);
            }
            resetFooterHeight();
        }
    }

    /**
     * 更新底部加载更多高度
     *
     * @param delta
     */
    private void updateFooterHeight(float delta) {
        int height = mRefreshFooter.getBottomMargin() + (int) delta;
        Log.i("footerheight", "updateFooterHeight: " + height);
        if (canLoad) {
            if (height > PULL_LOAD_MORE_DELTA) {
                mRefreshFooter.setState(RefreshFooterView.STATE_READY);
                mPullLoading = true;
            } else {
                mRefreshFooter.setState(RefreshFooterView.STATE_NORMAL);
                mPullLoading = false;
                mPullLoad = false;
            }
        }
        mRefreshFooter.setBottomMargin(height);
    }

    public interface RecyclerViewRefreshListener {
        void onRefresh();

        void onLoadMore();
    }
}
