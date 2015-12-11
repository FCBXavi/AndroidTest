package com.example.quxiaopeng.viewpagertest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    List<View> views;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager=(ViewPager)findViewById(R.id.vp_viewPager);
        views=new ArrayList<View>();
        LayoutInflater inflater=getLayoutInflater();
        View view1=inflater.inflate(R.layout.fragment1, null);
        View view2=inflater.inflate(R.layout.fragment2, null);
        View view3=inflater.inflate(R.layout.fragment3,null);
        views.add(view1);
        views.add(view2);
        views.add(view3);

        viewPager.setAdapter(new MyAdapter(views));
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(2);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
