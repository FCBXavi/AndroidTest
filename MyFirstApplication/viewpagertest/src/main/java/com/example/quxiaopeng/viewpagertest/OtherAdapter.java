package com.example.quxiaopeng.viewpagertest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by quxiaopeng on 16/3/8.
 */
public class OtherAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mList;
    private String[] mTitles = {"Tab1", "Tab2", "Tab3"};

    public OtherAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
