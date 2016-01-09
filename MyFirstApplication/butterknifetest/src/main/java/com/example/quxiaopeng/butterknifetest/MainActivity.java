package com.example.quxiaopeng.butterknifetest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @Bind(R.id.tv_main)TextView textView;
    @Bind(R.id.btn_main)Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        textView.setText("hahahaha");


    }

    @OnClick(R.id.btn_main)
    void sayHello(){
        Log.i("MainActivity", "sayHello ");
    }

}
