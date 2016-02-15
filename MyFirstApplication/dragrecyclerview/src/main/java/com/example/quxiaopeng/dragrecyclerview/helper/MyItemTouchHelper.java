package com.example.quxiaopeng.dragrecyclerview.helper;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by quxiaopeng on 16/1/26.
 */
public class MyItemTouchHelper extends ItemTouchHelper.Callback {

    ItemTouchHelperCallback mCallback;

    public MyItemTouchHelper(ItemTouchHelperCallback callback){
        this.mCallback = callback;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //ItemTouchHelper可以让你轻易得到一个事件的方向。你需要重写getMovementFlags()方法来指定可以支持的拖放和滑动的方向。使用helperItemTouchHelper.makeMovementFlags(int, int)来构造返回的flag。
        // 这里我们启用了上下左右两种方向。注：上下为拖动（drag），左右为滑动（swipe）。
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public boolean isLongPressDragEnabled() {
        //ItemTouchHelper可以用于没有滑动的拖动操作（或者反过来），你必须指明你到底要支持哪一种。要支持长按RecyclerView item进入拖动操作，你必须在isLongPressDragEnabled()方法中返回true。
        // 或者，也可以调用ItemTouchHelper.startDrag(RecyclerView.ViewHolder) 方法来开始一个拖动。
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        //要在view任意位置触摸事件发生时启用滑动操作，则直接在sItemViewSwipeEnabled()中返回true就可以了。
        // 或者，你也主动调用ItemTouchHelper.startSwipe(RecyclerView.ViewHolder) 来开始滑动操作。
        return true;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
        mCallback.onItemMove(viewHolder.getAdapterPosition(), viewHolder1.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        mCallback.onItemDismiss(viewHolder.getAdapterPosition());
    }

    //dX 与 dY参数代表目前被选择view 的移动距离，其中：
//    -1.0f is a full ItemTouchHelper.END to ItemTouchHelper.STARTswipe
//    1.0f is a full ItemTouchHelper.START to ItemTouchHelper.END swipe
//    为了不漏掉我们没有处理的actionState，记住务必调用super方法，这样其他的默认动画才会运行。
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            float width = viewHolder.itemView.getWidth();
            float alpha = 1.0f - Math.abs(dX)/width;
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }
}
