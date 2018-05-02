package com.example.quxiaopeng.funnelview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<FunnelModel> list = new ArrayList<>();
        list.add(new FunnelModel("尚未联系", 110));
        list.add(new FunnelModel("商务跟进", 100));
        list.add(new FunnelModel("签单成交", 100));
        list.add(new FunnelModel("签单成交", 90));
        list.add(new FunnelModel("签单成交", 80));
        list.add(new FunnelModel("签单成交", 70));
        list.add(new FunnelModel("签单成交", 60));
        list.add(new FunnelModel("签单成交", 50));
        list.add(new FunnelModel("签单成交", 40));
        list.add(new FunnelModel("签单成交", 30));
        list.add(new FunnelModel("签单成交", 20));
        list.add(new FunnelModel("签单成交", 10));
        list.add(new FunnelModel("未知状态", 50));
//        list.add(new FunnelModel("签单成交", 300));
//        list.add(new FunnelModel("签单成交", 300));
//        list.add(new FunnelModel("签单成交", 300));
//        list.add(new FunnelModel("签单成交", 300));
//        list.add(new FunnelModel("签单成交", 300));
//        list.add(new FunnelModel("签单成交", 300));
//        list.add(new FunnelModel("签单成交", 300));
//        list.add(new FunnelModel("签单成交", 300));
//        list.add(new FunnelModel("签单成交", 300));
//        list.add(new FunnelModel("签单成交", 300));





        FunnelView funnelView = (FunnelView) findViewById(R.id.view_funnel);
        funnelView.setFunnelList(list);
        funnelView.setOnSelectListener(new FunnelView.OnSelectListener() {
            @Override
            public void onSelect(int index) {
                Toast.makeText(MainActivity.this, index + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
