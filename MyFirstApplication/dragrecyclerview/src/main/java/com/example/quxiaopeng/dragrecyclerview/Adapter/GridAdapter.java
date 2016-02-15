package com.example.quxiaopeng.dragrecyclerview.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
 * Created by quxiaopeng on 16/1/27.
 */
public class GridAdapter extends RecyclerView.Adapter implements ItemTouchHelperCallback {
    private List<String> mList;
    private Context mContext;
    private OnStartDragListener mOnStartDragListener;

    public GridAdapter(List<String> list, Context context){
        mList = list;
        mContext = context;
//        this.mOnStartDragListener = mOnStartDragListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_item, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.textView.setText(mList.get(position));
//        myViewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN){
//                    mOnStartDragListener.onStartDrag(myViewHolder);
//                }
//                return false;
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_icon);
            textView = (TextView) itemView.findViewById(R.id.tv_text);
        }
    }
}
