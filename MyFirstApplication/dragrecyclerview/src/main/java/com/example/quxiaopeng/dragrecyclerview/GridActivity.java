package com.example.quxiaopeng.dragrecyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.quxiaopeng.dragrecyclerview.Adapter.GridAdapter;
import com.example.quxiaopeng.dragrecyclerview.helper.MyItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quxiaopeng on 16/1/27.
 */
public class GridActivity extends Activity {
    public List<String> mList = new ArrayList<>();
    public RecyclerView mRecyclerView;
    public GridAdapter mAdapter;
    ItemTouchHelper itemTouchHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        mList.add("one");
        mList.add("two");
        mList.add("three");
        mList.add("four");
        mList.add("five");
        mList.add("six");
        mList.add("seven");
        mList.add("eight");
        mList.add("nine");
        mList.add("ten");
        mAdapter = new GridAdapter(mList, this);
        mRecyclerView.setAdapter(mAdapter);

        MyItemTouchHelper callback = new MyItemTouchHelper(mAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

//    @Override
//    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
//
//    }
}
