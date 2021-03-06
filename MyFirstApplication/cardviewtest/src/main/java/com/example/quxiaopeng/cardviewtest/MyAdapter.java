package com.example.quxiaopeng.cardviewtest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by quxiaopeng on 15/9/29.
 */
public class MyAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<String> list;

    public MyAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        String s = list.get(i);
        ViewHolder myHolder=(ViewHolder)viewHolder;
        myHolder.mTextView.setText(s);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;


        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.name);
        }
    }
}
