package com.example.quxiaopeng.viewpagertest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by quxiaopeng on 16/3/8.
 */
public class OtherAdapter extends FragmentStatePagerAdapter {

    private List<String> data;

    public OtherAdapter(FragmentManager fm, List<String> data) {
        super(fm);
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        MyFragment fragment = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("data", data.get(position));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }
}
