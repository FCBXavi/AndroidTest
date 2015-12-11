package com.example.quxiaopeng.ottotest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.squareup.otto.Subscribe;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppConfig.getBusInstance().register(this);
        Download download=new Download();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppConfig.getBusInstance().register(this);
    }

    @Subscribe
    public void onOttoEvent(PostEvent postEvent){
        Log.i("MainActivity", "receive post Event");
    }
}
