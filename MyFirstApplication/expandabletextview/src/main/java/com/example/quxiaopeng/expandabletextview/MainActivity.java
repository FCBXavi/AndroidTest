package com.example.quxiaopeng.expandabletextview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExpandableTextView textView = (ExpandableTextView) findViewById(R.id.tv_expandable_text);
        textView.setText("技术开发、软件开发、技术推广；经济信息咨询；计算机培训；设计、制作、代理、发布广告；销售计算机软硬件及辅助设备。（依法须经" +
                "批准的项目硬件及辅助设备。（依法须经批准的项目硬件及辅助设备。（依法须经批准的项目硬件及辅助设备。（依法须经批准的项目硬件及辅助设备。（依法须经批准的项目硬件及辅助设备。（依法须经批准的项目");
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }
}
