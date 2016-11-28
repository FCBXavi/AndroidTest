package com.example.quxiaopeng.androidcharttest;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quxiaopeng on 15/12/11.
 */
public class ScatterChartActivity extends Activity implements OnChartValueSelectedListener{

    ScatterChart mScatterChart;
    List<IScatterDataSet> scatterDataSets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scatterchart);
        initView();
        mScatterChart.setOnChartValueSelectedListener(this);
    }

    private void initView() {
        mScatterChart= (ScatterChart) findViewById(R.id.scatter_chart);
        setChartStyle();
        setData();
    }

    private void setChartStyle() {

        mScatterChart.setGridBackgroundColor(Color.TRANSPARENT);
        mScatterChart.setScaleEnabled(true);
        mScatterChart.setDescription("This is a ScatterChart");
        mScatterChart.setDragEnabled(true);

        XAxis xAxis=mScatterChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
//        xAxis.setDrawAxisLine(false);

        YAxis yAxisLeft = mScatterChart.getAxisLeft();
        yAxisLeft.setDrawAxisLine(false);
        yAxisLeft.setDrawGridLines(false);

        YAxis yAxisRight = mScatterChart.getAxisRight();
        yAxisRight.setDrawLabels(false);
        yAxisRight.setDrawGridLines(false);
        yAxisRight.setDrawAxisLine(false);

        Legend legend=mScatterChart.getLegend();
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        legend.setForm(Legend.LegendForm.CIRCLE);
    }

    private void setData() {
        List<String> xValues=new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            xValues.add(i+1+"月");
        }

        List<Entry> entries=new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            int temp= (int) (Math.random()*10);
            Entry entry=new Entry(temp,i);
            entries.add(entry);
        }


        ScatterDataSet scatterDataSet1=new ScatterDataSet(entries,"测试数据1");
        scatterDataSet1.setColor(Color.RED);
        scatterDataSet1.setHighLightColor(Color.WHITE);

        scatterDataSets = new ArrayList<>();
        scatterDataSets.add(scatterDataSet1);

        ScatterData scatterData=new ScatterData(xValues,scatterDataSets);
        mScatterChart.setData(scatterData);
    }

    @Override
    public void onValueSelected(Entry entry, int i, Highlight highlight) {
        int index=highlight.getXIndex();
        Toast.makeText(ScatterChartActivity.this, "select item"+scatterDataSets.get(0).getYValForXIndex(index), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

    }
}
