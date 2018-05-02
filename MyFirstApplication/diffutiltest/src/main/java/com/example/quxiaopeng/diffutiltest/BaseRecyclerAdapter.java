package com.example.quxiaopeng.diffutiltest;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quxiaopeng on 2017/5/2.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter {

    protected final List<T> oldList;
    protected final List<T> newList;
    protected OnItemClickListener mItemClickListener;
    protected OnItemLongClickListener mItemLongClickListener;

    public BaseRecyclerAdapter(List<T> datas) {
        this.newList = datas;
        oldList = new ArrayList<>();
        oldList.addAll(newList);
    }

    protected boolean areItemsTheSame(T oldItem, T newItem) {
        return false;
    }

    protected boolean areContentsTheSame(T oldItem, T newItem) {
        return false;
    }

    public void notifyDiff() {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return oldList.size();
            }

            @Override
            public int getNewListSize() {
                return newList.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return BaseRecyclerAdapter.this.areItemsTheSame(oldList.get(oldItemPosition), newList.get(newItemPosition));
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return BaseRecyclerAdapter.this.areContentsTheSame(oldList.get(oldItemPosition), newList.get(newItemPosition));
            }
        });
        diffResult.dispatchUpdatesTo(this);
        oldList.clear();
        oldList.addAll(newList);
    }

    @Override
    public int getItemCount() {
        return newList == null ? 0 : newList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null && position != RecyclerView.NO_POSITION) {
                    mItemClickListener.onItemClick(v, position);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return mItemLongClickListener != null
                        && position != RecyclerView.NO_POSITION
                        && mItemLongClickListener.onItemLongClick(v, position);
            }
        });
        bind(holder, position);
    }

    public abstract void bind(RecyclerView.ViewHolder holder, int position);


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mItemLongClickListener = listener;
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }
}
