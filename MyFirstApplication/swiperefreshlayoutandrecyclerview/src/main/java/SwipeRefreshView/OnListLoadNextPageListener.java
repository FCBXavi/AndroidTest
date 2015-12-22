package SwipeRefreshView;

import android.view.View;

/**
 * RecyclerView滑动加载下一页时的回调接口
 */
public interface OnListLoadNextPageListener {

    /**
     * 开始加载下一页
     *
     * @param view 当前RecyclerView
     */
    public void onLoadNextPage(View view);
}
