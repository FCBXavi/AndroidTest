package com.example.quxiaopeng.swiperefreshlayouttest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    public static final int REFRESH_COMPLETE = 0x00;
    ListView listView;
    SwipeRefreshLayout swipeRefreshLayout;
    List<String>list = new ArrayList<>();
    ArrayAdapter<String> mAdapter;
    Handler mHandler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case REFRESH_COMPLETE:
                    list.addAll(Arrays.asList("Lucene","Canvas","Bitmap"));
                    mAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    public void initView(){
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        listView = (ListView) findViewById(R.id.listview);
        list.add("Java");
        list.add("JavaScript");
        list.add("C++");
        list.add("Json");
        list.add("Python");
        list.add("Html");
        mAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(mAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageAtTime(REFRESH_COMPLETE,2000);
    }
}
