package com.example.quxiaopeng.dragrecyclerview.Adapter;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quxiaopeng.dragrecyclerview.R;
import com.example.quxiaopeng.dragrecyclerview.helper.ItemTouchHelperCallback;
import com.example.quxiaopeng.dragrecyclerview.helper.OnStartDragListener;

import java.util.Collections;
import java.util.List;

/**
 * Created by quxiaopeng on 16/1/26.
 */
public class ListAdapter extends RecyclerView.Adapter implements ItemTouchHelperCallback {
    private List<String> list;
    private Context mContext;
    private OnStartDragListener mOnStartDragListener;

    public ListAdapter(List<String> list, Context context, OnStartDragListener onStartDragListener) {
        this.list = list;
        this.mContext = context;
        this.mOnStartDragListener = onStartDragListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.textView.setText(list.get(i));
        myViewHolder.imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN){
                    mOnStartDragListener.onStartDrag(myViewHolder);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_text);
            imageView = (ImageView) itemView.findViewById(R.id.iv_icon);
        }
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(list, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }
}
