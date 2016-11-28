package com.example.quxiaopeng.androidcharttest;

import android.app.Activity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quxiaopeng on 16/8/15.
 */

public class HorizontalBarChartActivity extends Activity {

    private HorizontalBarChart mChart;
    private List<IBarDataSet> dataSets = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_barchart);
        mChart = (HorizontalBarChart) findViewById(R.id.horizontal_bar_chart);
        initChart();
        initData();
    }

    private void initChart() {
        XAxis xAxis = mChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawLabels(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        mChart.getAxisLeft().setEnabled(false);
        mChart.getAxisLeft().setAxisMinValue(0);
        mChart.getAxisRight().setEnabled(false);
    }

    private void initData() {
        List<String> xValues = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            xValues.add(i + 1 + "月");
        }

        List<BarEntry> entries1 = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            float temp = (float) (Math.random() * 10);
            BarEntry entry=new BarEntry(temp, i);
            entries1.add(entry);
        }

        BarDataSet barDataSet1 = new BarDataSet(entries1, "测试数据1");
        dataSets.add(barDataSet1);
        BarData barData = new BarData(xValues, dataSets);
        mChart.setData(barData);
    }
}
