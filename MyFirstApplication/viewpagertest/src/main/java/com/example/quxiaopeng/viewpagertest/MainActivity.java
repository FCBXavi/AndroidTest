package com.example.quxiaopeng.viewpagertest;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {


    private List<Fragment> mFragmentList = new ArrayList<>();
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.vp_viewPager);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
//        views=new ArrayList<>();
//        LayoutInflater inflater=getLayoutInflater();
//        View view1=inflater.inflate(R.layout.fragment1, null);
//        View view2=inflater.inflate(R.layout.fragment2, null);
//        View view3=inflater.inflate(R.layout.fragment3,null);
//        views.add(view1);
//        views.add(view2);
//        views.add(view3);
//
//        viewPager.setAdapter(new MyAdapter(views));
//        viewPager.setCurrentItem(0);
//        viewPager.setOffscreenPageLimit(2);

        Fragment1 fragment1 = new Fragment1();
        Fragment2 fragment2 = new Fragment2();
        Fragment3 fragment3 = new Fragment3();
        mFragmentList.add(fragment1);
        mFragmentList.add(fragment2);
        mFragmentList.add(fragment3);
        OtherAdapter adapter = new OtherAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        Log.i(TAG, "onCreate: ");
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "run: ");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }
}
