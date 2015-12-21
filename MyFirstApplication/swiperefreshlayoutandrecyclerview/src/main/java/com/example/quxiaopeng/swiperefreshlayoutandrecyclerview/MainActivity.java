package com.example.quxiaopeng.swiperefreshlayoutandrecyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Adapter.RecyclerAdapter;
import SwipeRefreshView.HeaderAndFooterRecyclerViewAdapter;
import SwipeRefreshView.LoadingFooter;
import SwipeRefreshView.MySwiperRefreshView;

public class MainActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener, MySwiperRefreshView.OnLoadListener{
    
    List<String> list;
    MySwiperRefreshView mSwiperRefreshView;
    RecyclerView mRecyclerView;
    RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.rc_my_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSwiperRefreshView = (MySwiperRefreshView) findViewById(R.id.swipe_layout);

        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("item - "+i);
        }
        recyclerAdapter = new RecyclerAdapter(this,list);
        HeaderAndFooterRecyclerViewAdapter adapter=new HeaderAndFooterRecyclerViewAdapter(recyclerAdapter);
        mRecyclerView.setAdapter(adapter);


        mSwiperRefreshView.setOnRefreshListener(this);
        mSwiperRefreshView.setOnLoadListener(this);
    }

    @Override
    public void onRefresh() {
        Toast.makeText(MainActivity.this, "refresh", Toast.LENGTH_SHORT).show();
        mSwiperRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                list.add(dateFormat.format(new Date()));
                recyclerAdapter.notifyDataSetChanged();
                mSwiperRefreshView.setRefreshing(false);
            }
        },1000);
    }

    @Override
    public void onLoad() {
        Toast.makeText(MainActivity.this, "load", Toast.LENGTH_SHORT).show();
        mSwiperRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                list.add(dateFormat.format(new Date()));
                recyclerAdapter.notifyDataSetChanged();
                mSwiperRefreshView.setPageSize(5);
                mSwiperRefreshView.setLoading(LoadingFooter.State.Normal);
            }
        },1000);
    }
}
