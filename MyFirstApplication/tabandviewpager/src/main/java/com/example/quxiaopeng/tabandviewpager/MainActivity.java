package com.example.quxiaopeng.tabandviewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<View> views;
    private LayoutInflater mInflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    public void init(){
        initTabLayout();
        initViewPager();
        //调用该方法后,tabLayout的tab全部失效,tab会显示viewPager的adapter里面的pageTitle
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initViewPager() {
        views=new ArrayList<>();
        viewPager= (ViewPager) findViewById(R.id.vp_viewPager);
        mInflater=getLayoutInflater();
        View view1=mInflater.inflate(R.layout.pager1,null);
        View view2=mInflater.inflate(R.layout.pager2,null);
        View view3=mInflater.inflate(R.layout.pager3,null);
        views.add(view1);
        views.add(view2);
        views.add(view3);

        MyAdapter adapter=new MyAdapter(views);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(2);
    }

    private void initTabLayout() {
        tabLayout= (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("tab1"));
        tabLayout.addTab(tabLayout.newTab().setText("tab2"));
        tabLayout.addTab(tabLayout.newTab().setText("tab3"));

    }


    class MyAdapter extends PagerAdapter {

        private List<View> views;

        public MyAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager) container).addView(views.get(position));
            return views.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "tab"+(position+1);
        }
    }

}
