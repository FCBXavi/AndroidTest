package com.example.quxiaopeng.alarmtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by quxiaopeng on 16/2/15.
 */
public class ClockActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
//        Toast.makeText(ClockActivity.this, "闹钟响了", Toast.LENGTH_SHORT).show();
        //显示对话框
//        new AlertDialog.Builder(this).
//                setTitle("闹钟").//设置标题
//                setMessage("时间到了！").//设置内容
//                setPositiveButton("知道了", new DialogInterface.OnClickListener(){//设置按钮
//            public void onClick(DialogInterface dialog, int which) {
//                ClockActivity.this.finish();//关闭Activity
//            }
//        }).create().show();
        TextView textView = (TextView) findViewById(R.id.tv_text);
        Intent intent = getIntent();
        textView.setText(intent.getStringExtra("hello"));


    }
}
