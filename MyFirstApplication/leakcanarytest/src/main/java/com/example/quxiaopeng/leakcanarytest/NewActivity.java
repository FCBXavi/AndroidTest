package com.example.quxiaopeng.leakcanarytest;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by quxiaopeng on 16/1/5.
 */
public class NewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_layout);
    }
}
