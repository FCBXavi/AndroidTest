package SwipeRefreshView;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * 继承自SwipeRefreshLayout,实现滑动到底部时上拉加载更多的功能,需要内部包含一个RecyclerView
 * Created by quxiaopeng on 15/12/18.
 */
public class MySwiperRefreshView extends SwipeRefreshLayout {

    Context mContext;
    /**
     * 滑动到最下面时的上拉操作
     */
    private int mTouchSlop;

    /**
     * RecyclerView实例
     */
    private RecyclerView mRecyclerView;
    /**
     * 上拉监听器, 到了最底部的上拉加载操作
     */
    private OnLoadListener mOnLoadListener;

    /**
     * 按下时的y坐标,与mYDown一起用于滑动到底部时判断是上拉还是下拉
     */
    private int mYdown;

    /**
     * 抬起时的y坐标,与mYDown一起用于滑动到底部时判断是上拉还是下拉
     */
    private int mLastY;

    /**
     * 是否在加载中(上拉加载更多)
     */
    private boolean isLoading = false;

    /**
     *当item条目多于pageSize时,才会有加载更多的提示
     */
    private int pageSize = 10;


    public MySwiperRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        this.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MySwiperRefreshView(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //初始化RecyclerView对象
        if (mRecyclerView == null) {
            initRecyclerView();
        }
    }

    private void initRecyclerView() {
        int childs = getChildCount();
        if (childs > 0) {
            for (int i = 0; i < childs; i++) {
                View childView = getChildAt(i);
                if (childView instanceof RecyclerView) {
                    mRecyclerView = (RecyclerView) childView;
                    mRecyclerView.addOnScrollListener(new RecyclerOnScrollListener() {
                        @Override
                        public void onLoadNextPage(View view) {
                            super.onLoadNextPage(view);

                            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(mRecyclerView);
                            if (state == LoadingFooter.State.Loading) {
                                Log.d("@Cundong", "the state is Loading, just wait..");
                                return;
                            }
                            if (isPullUp() && mOnLoadListener!=null){
                                RecyclerViewStateUtils.setFooterViewState(mContext, mRecyclerView, pageSize, LoadingFooter.State.Loading, null);
                                mOnLoadListener.onLoad();
                            }
                        }
                    });
                    break;
                }
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mYdown = (int) ev.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                mLastY = (int) ev.getRawY();
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    /**
     * 是否是上拉操作
     *
     * @return
     */
    private boolean isPullUp() {
        return (mYdown - mLastY) >= mTouchSlop;
    }


    public void setPageSize(int pageSize){
        this.pageSize = pageSize;
    }

    /**
     * 手动设置加载状态
     */
    public void setLoading(LoadingFooter.State state) {
        if (state == LoadingFooter.State.NetWorkError){
            RecyclerViewStateUtils.setFooterViewState(mContext, mRecyclerView, pageSize, state, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnLoadListener!=null){
                        RecyclerViewStateUtils.setFooterViewState(mContext, mRecyclerView, pageSize, LoadingFooter.State.Loading, null);
                        mOnLoadListener.onLoad();
                    }
                }
            });
        }else {
            RecyclerViewStateUtils.setFooterViewState(mContext, mRecyclerView, pageSize, state, null);
        }
    }

    public void setOnLoadListener(OnLoadListener loadListener) {
        this.mOnLoadListener = loadListener;
    }


    /**
     * 加载更多的接口
     */
    public interface OnLoadListener {
        void onLoad();
    }
}
