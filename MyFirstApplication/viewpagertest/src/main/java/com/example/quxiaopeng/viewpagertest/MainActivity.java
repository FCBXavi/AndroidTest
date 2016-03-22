package com.example.quxiaopeng.viewpagertest;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {


    List<View> views;
    List<String> data = new ArrayList<>();
    ViewPager mViewPager;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ViewPager viewPager=(ViewPager)findViewById(R.id.vp_viewPager);
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

        mViewPager = (ViewPager) findViewById(R.id.vp_viewPager);
        button = (Button) findViewById(R.id.btn_button);
        for (int i = 0; i < 10; i++) {
            data.add("title" + i);
        }
        OtherAdapter otherAdapter = new OtherAdapter(getSupportFragmentManager(), data);
        mViewPager.setAdapter(otherAdapter);
        mViewPager.setPageMargin(10);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = mViewPager.getCurrentItem();
                mViewPager.setCurrentItem(current+1, true);
            }
        });
//        mViewPager.setPageMarginDrawable(R.drawable.icon);

    }
}
