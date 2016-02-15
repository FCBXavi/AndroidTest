package com.example.quxiaopeng.dragrecyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.quxiaopeng.dragrecyclerview.Adapter.ListAdapter;
import com.example.quxiaopeng.dragrecyclerview.helper.MyItemTouchHelper;
import com.example.quxiaopeng.dragrecyclerview.helper.OnStartDragListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quxiaopeng on 16/1/26.
 */
public class RecyclerListActivity extends Activity implements OnStartDragListener{
    RecyclerView mRecyclerView;
    ListAdapter mAdapter;
    List<String> mList = new ArrayList<>();
    MyItemTouchHelper callback;
    ItemTouchHelper itemTouchHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_list_layout);
        initView();
        initData();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void initData() {
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
        mAdapter = new ListAdapter(mList, this, this);
        mRecyclerView.setAdapter(mAdapter);

        callback = new MyItemTouchHelper(mAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }


    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }
}
