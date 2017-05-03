package com.example.quxiaopeng.diffutiltest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by quxiaopeng on 2017/5/2.
 */

public class StudentAdapter extends BaseRecyclerAdapter<Student>{
    private Context mContext;

    public StudentAdapter(Context context, List<Student> list) {
        super(list);
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_student, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void bind(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        Student student = newList.get(position);
        viewHolder.tvName.setText(student.name);
    }

    @Override
    protected boolean areItemsTheSame(Student oldItem, Student newItem) {
        return oldItem.id.equals(newItem.id);
    }

    @Override
    protected boolean areContentsTheSame(Student oldItem, Student newItem) {
        return oldItem.name.equals(newItem.name);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_student_name);
        }
    }
}
