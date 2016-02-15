package com.example.quxiaopeng.dragrecyclerview.helper;

/**
 * Created by quxiaopeng on 16/1/26.
 */
public interface ItemTouchHelperCallback {
    void onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}
