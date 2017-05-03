package com.example.quxiaopeng.salesrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by quxiaopeng on 2017/3/20.
 */

public class RefreshRecyclerViewAdapter extends RecyclerView.Adapter {

    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_BOTTOM = 2;
    public static final int ITEM_TYPE_HEADERVIEW = 3;

    private RecyclerView.Adapter mAdapter;
    private Context mContext;
    public int mHeaderCount = 1;//头部View个数
    public int mBottomCount = 1;//底部View个数

    private boolean isRefresh;
    private boolean isLoadMore;
    private RefreshHeaderView mRefreshHeader;
    private RefreshFooterView mRefreshFooter;

    private View mHeaderView;

    RecyclerView.AdapterDataObserver mObserver = new  RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
//            super.onChanged();
            notifyDataSetChanged();
        }


        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
//            super.onItemRangeChanged(positionStart + lfAdapter.getheaderViewCount(), itemCount);
            notifyItemRangeChanged(positionStart + getHeaderViewCount(), itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
//            super.onItemRangeChanged(positionStart + lfAdapter.getheaderViewCount(), itemCount, payload);
            notifyItemRangeChanged(positionStart + getHeaderViewCount(), itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
//            super.onItemRangeInserted(positionStart + lfAdapter.getheaderViewCount(), itemCount);
            notifyItemRangeInserted(positionStart + getHeaderViewCount(), itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
//            super.onItemRangeRemoved(positionStart + lfAdapter.getheaderViewCount(), itemCount);
            notifyItemRangeRemoved(positionStart + getHeaderViewCount(), itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
//            super.onItemRangeMoved(fromPosition + lfAdapter.getheaderViewCount(), toPosition + lfAdapter.getheaderViewCount(), itemCount);
            notifyItemMoved(fromPosition + getHeaderViewCount(), toPosition + getHeaderViewCount());
        }
    };

    public RefreshRecyclerViewAdapter(Context context, RecyclerView.Adapter adapter) {
        this.mContext = context;
        this.mAdapter = adapter;
        mAdapter.registerAdapterDataObserver(mObserver);
    }

    public void setHeaderView(View headerView) {
        this.mHeaderView = headerView;
    }

    public void setRefreshHeader(RefreshHeaderView refreshHeader) {
        this.mRefreshHeader = refreshHeader;
    }

    public void setRefreshFooter(RefreshFooterView refreshFooter) {
        this.mRefreshFooter = refreshFooter;
    }

    public void setRefresh(boolean isRefresh) {
        this.isRefresh = isRefresh;
        if (isRefresh) {
            mHeaderCount = 1;
        } else {
            mHeaderCount = 0;
        }
    }

    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            Log.i("gettype", "Refreshheader");
            return new HeaderBottomHolder(mRefreshHeader);
        } else if (viewType == ITEM_TYPE_CONTENT) {
            Log.i("gettype", "normal");
            return mAdapter.onCreateViewHolder(parent, viewType);
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            Log.i("gettype", "RefreshFooter");
            return new HeaderBottomHolder(mRefreshFooter);
        } else if (viewType == ITEM_TYPE_HEADERVIEW) {
            Log.i("gettype", "header");
            return new HeaderBottomHolder(mHeaderView);
        }
        Log.i("gettype", "unknown");
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isRefreshHeaderView(position) || isRefreshBottomView(position) || isCustomHeaderView(position)) {
            return;
        }
        final int po = position - getHeaderViewCount();
        mAdapter.onBindViewHolder(holder, po);
    }

    @Override
    public int getItemCount() {
        return getHeaderViewCount() + mBottomCount + mAdapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (isRefreshHeaderView(position) && isRefresh) {
            //头部View
            Log.i("getItemtype", "Refreshheader");
            return ITEM_TYPE_HEADER;
        } else if (isRefreshBottomView(position)) {
            //底部View
            Log.i("getItemtype", "RefreshBottom");
            return ITEM_TYPE_BOTTOM;
        } else if (isCustomHeaderView(position)) {
            //内容View
            Log.i("getItemtype", "headerview");
            return ITEM_TYPE_HEADERVIEW;
        } else {
            Log.i("getItemtype", "normal");
            return ITEM_TYPE_CONTENT;
        }
    }

    public int getHeaderAndFooterCount() {
        return getHeaderViewCount()+ mBottomCount;
    }

    private class HeaderBottomHolder extends RecyclerView.ViewHolder {

        public HeaderBottomHolder(View itemView) {
            super(itemView);
        }
    }

    //判断当前item是否是HeadView
    public boolean isRefreshHeaderView(int position) {
        return mHeaderCount != 0 && position < mBottomCount;
    }

    //判断当前item是否是FooterView
    public boolean isRefreshBottomView(int position) {
        return mBottomCount != 0 && position >= (getHeaderViewCount() + mAdapter.getItemCount());
    }

    //判断当前是否是自定义头部View
    public boolean isCustomHeaderView(int position) {
        return mHeaderView != null && position == mHeaderCount;
    }

    public int getHeaderViewCount() {
        int count = 0;
        if (isRefresh) {
            count += 1;
        }
        if (mHeaderView != null) {
            count += 1;
        }
        return count;
    }
}
