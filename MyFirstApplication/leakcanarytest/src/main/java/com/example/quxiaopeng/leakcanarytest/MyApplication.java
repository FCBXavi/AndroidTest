package com.example.quxiaopeng.leakcanarytest;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by quxiaopeng on 16/1/5.
 */
public class MyApplication extends Application {
    private static MyApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        if (instance == null){
            instance = this;
        }
        LeakCanary.install(this);
    }

    public static MyApplication getInstance(){
        return instance;
    }
}
