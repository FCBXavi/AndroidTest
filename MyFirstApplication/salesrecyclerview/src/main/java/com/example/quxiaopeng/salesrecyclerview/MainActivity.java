package com.example.quxiaopeng.salesrecyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RefreshRecyclerView.RecyclerViewRefreshListener{

    RefreshRecyclerView mRecyclerView;
    private boolean b;
    private ArrayList<String> list;
    private MainAdapter adapter;
    private Button mBtnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        RefreshHeaderView headerView = (RefreshHeaderView) findViewById(R.id.view_header);
//        LoadingView loadingView = new SalesLeadsLoadingView(this);
//        headerView.setLoadView(loadingView);
        list = new ArrayList<>();
//        list.add("1");
//        list.add("2");
        mRecyclerView = (RefreshRecyclerView) findViewById(R.id.rv_list);
        mBtnRefresh = (Button) findViewById(R.id.btn_refresh);
        mRecyclerView.setCanLoad(true);
        mRecyclerView.setCanRefresh(true);
        mRecyclerView.setRefreshListener(this);
//        mRecyclerView.setOnItemClickListener(this);
//        mRecyclerView.setLFRecyclerViewListener(this);
//        mRecyclerView.setScrollChangeListener(this);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new MainAdapter(list);
        adapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(adapter);

        mBtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.refresh();
            }
        });

//        mRecyclerView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mRecyclerView.refresh();
//            }
//        }, 0);

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                b = !b;
                list.add(0, "leefeng.me" + "==onRefresh");
                mRecyclerView.stopRefresh(b);
//                adapter.notifyItemInserted(0);
//                adapter.notifyItemRangeChanged(0,list.size());
                adapter.notifyDataSetChanged();

            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                b = !b;
                mRecyclerView.stopLoadMore(b);
                list.add(list.size(), "leefeng.me" + "==onLoadMore");
                Log.i("onload", "load");
//                adapter.notifyItemRangeInserted(list.size()-1,1);
                adapter.notifyDataSetChanged();

            }
        }, 2000);
    }
}
