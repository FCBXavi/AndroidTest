package com.example.quxiaopeng.diffutiltest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by quxiaopeng on 2017/7/13.
 */

public class TestAnimatorAdapter extends RecyclerView.Adapter {

    private List<Student> mList;
    private Context mContext;

    public TestAnimatorAdapter(Context context, List<Student> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_student, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        Student student = mList.get(position);
        viewHolder.tvName.setText(student.name);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_student_name);
        }
    }
}
