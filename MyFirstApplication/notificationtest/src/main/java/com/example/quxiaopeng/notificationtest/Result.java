package com.example.quxiaopeng.notificationtest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by quxiaopeng on 15/10/8.
 */
public class Result extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        TextView textView=(TextView)findViewById(R.id.tv_text);
        textView.setText("result");
    }
}
