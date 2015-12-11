package com.example.quxiaopeng.androidcharttest;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quxiaopeng on 15/12/9.
 */
public class LineChartActivity extends Activity implements OnChartValueSelectedListener{
    LineChart mLineChart;
    List<LineDataSet> lineDataSets = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linechart);
        initView();
        mLineChart.setOnChartValueSelectedListener(this);
    }

    private void initView() {
        mLineChart = (LineChart) findViewById(R.id.line_chart);
        setLineStyle();
        setData();
    }

    public void setData(){
        List<String> xVals = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            xVals.add(i + 1 + "");
        }

        List<Entry> entries1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int value = (int) (Math.random() * 10);
            entries1.add(new Entry(value, i));
        }

        List<Entry> entries2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int value = (int) (Math.random() * 10);
            entries2.add(new Entry(value, i));
        }

        LineDataSet lineDataSet1 = new LineDataSet(entries1, "测试数据1");
        LineDataSet lineDataSet2 = new LineDataSet(entries2, "测试数据2");
        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);

        lineDataSet1.setLineWidth(3f);//线宽
        lineDataSet1.setCircleSize(6f);//圆形的大小
        lineDataSet1.setColor(Color.RED);//显示颜色
        lineDataSet1.setCircleColor(Color.RED);//圆形的颜色
        lineDataSet1.setHighLightColor(Color.WHITE);//高亮线的颜色
        lineDataSet1.setDrawCircles(false);//每个点是否有圆形
        lineDataSet1.setDrawValues(false);//每个点处是否有值
        lineDataSet1.setDrawCubic(true);//设置允许曲线平滑
        lineDataSet1.setCubicIntensity(0.2f);//设置折线的平滑度
//        lineDataSet1.setDrawFilled(true);//设置允许填充
//        lineDataSet1.setFillColor(Color.rgb(0,255,255));//设置填充颜色
        lineDataSet1.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet1.setDrawHorizontalHighlightIndicator(false);


//        lineDataSet1.setLineWidth(lineWidth);
//        lineDataSet1.setHighLightColor(Color.WHITE);
//        lineDataSet1.setCircleSize(circleWidth);
//        lineDataSet1.setDrawCircles(false);
//        lineDataSet1.setAxisDependency(YAxis.AxisDependency.LEFT);
//        lineDataSet1.setDrawValues(false);
//        lineDataSet1.setDrawHorizontalHighlightIndicator(false);

        lineDataSet2.setHighLightColor(Color.TRANSPARENT);
        LineData lineData = new LineData(xVals, lineDataSets);
        mLineChart.setData(lineData);
    }


    public void setLineStyle() {

//        XAxis xAxis = chart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setDrawGridLines(false);
//        xAxis.setAxisLineColor(Color.argb(33, 0, 0, 0));
//        xAxis.setLabelsToSkip(10);
//        xAxis.setTextColor(Color.parseColor("#9B9B9B"));
//        xAxis.setAvoidFirstLastClipping(true);
//        Legend legend = chart.getLegend();
//        legend.setForm(Legend.LegendForm.CIRCLE);
//        legend.setTextSize(11f);
//        legend.setTextColor(Color.argb(100, 153, 153, 153));
//        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
//        legend.setXEntrySpace(62f);
//
//        chart.setDescription("");
//        chart.setNoDataText("");
//        chart.setNoDataTextDescription("You need to provide data for the chart");
//
//        chart.setDrawGridBackground(true);
//        chart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF);
//        chart.setTouchEnabled(true);
//        chart.setDragEnabled(true);
//        chart.setScaleEnabled(false);
//        chart.setPinchZoom(false);
//        chart.setHighlightEnabled(true);
//        chart.animateX(1000);

        mLineChart.setDrawBorders(false);  //是否在折线图上添加边框
        mLineChart.setDescription("这是一个折线图");
        mLineChart.setNoDataText("");
        mLineChart.setNoDataTextDescription("You need to provide data for the chart.");
        mLineChart.setDrawGridBackground(true);//是否显示表格颜色
        mLineChart.setGridBackgroundColor(Color.TRANSPARENT);//表格的颜色
        mLineChart.setTouchEnabled(true);
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(false);
        mLineChart.setPinchZoom(false);// if disabled, scaling can be done on x- and y-axis separately
        mLineChart.setHighlightEnabled(true);
        mLineChart.animateX(2000);

        Legend legend = mLineChart.getLegend();// 设置比例图标示，就是那个一组y的value的
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(11f);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

        //设置xy轴的属性
        YAxis rightAxis = mLineChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawLabels(false);

        YAxis leftAxis = mLineChart.getAxisLeft();
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);
        leftAxis.setStartAtZero(true);
        leftAxis.setTextColor(Color.parseColor("#9B9B9B"));


        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisLineColor(Color.argb(33, 0, 0, 0));
//        xAxis.setLabelsToSkip(8);
        xAxis.setTextColor(Color.parseColor("#9B9B9B"));
//        xAxis.setAvoidFirstLastClipping(true);

    }

    @Override
    public void onValueSelected(Entry entry, int i, Highlight highlight) {
        int index=highlight.getXIndex();
        Toast.makeText(this, "select num:" + lineDataSets.get(0).getYValForXIndex(index), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

    }
}
