package com.example.quxiaopeng.androidcharttest;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quxiaopeng on 15/12/10.
 */
public class PieChartActivity extends Activity implements OnChartValueSelectedListener{
    PieChart mPieChart;
    PieDataSet pieDataSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart);
        initView();
    }

    private void initView() {
        mPieChart = (PieChart) findViewById(R.id.pie_chart);
        setChartStyle();
        setData();

    }

    private void setChartStyle() {
//        mChart.setDescription("");
//        mChart.setDragDecelerationFrictionCoef(0.95f);
//        mChart.setDrawHoleEnabled(false);
//        mChart.setRotationAngle(270);
//        mChart.setTransparentCircleColor(Color.WHITE);
//        mChart.setTransparentCircleAlpha(110);
//        mChart.setTransparentCircleRadius(61f);
//        mChart.setRotationAngle(0);
//        // enable rotation of the chart by touch
//        mChart.setRotationEnabled(true);
//        // mChart.setUnit(" €");
//        // mChart.setDrawUnitsInChart(true);
//        // add a selection listener
//        mChart.setOnChartValueSelectedListener(this);
//        mChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);
//        // mChart.spin(2000, 0, 360);
//        Legend l = mChart.getLegend();
//        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
//        l.setForm(Legend.LegendForm.CIRCLE);
//        l.setXEntrySpace(32f);
//        l.setYEntrySpace(0f);
//        l.setTextSize(11f);
//        l.setTextColor(Color.argb(100, 153, 153, 153));
//        l.setYOffset(10f);

        mPieChart.setDrawHoleEnabled(false);//中心不显示空白
        mPieChart.setHoleRadius(50f);
        mPieChart.setDescription("测试饼状图");
        mPieChart.setDragDecelerationFrictionCoef(0.7f);
        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);
        mPieChart.setTransparentCircleRadius(60f);//半透明圆
        mPieChart.setRotationAngle(0);//初始旋转角度
        mPieChart.setRotationEnabled(true);//可以手动旋转
        mPieChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);
//        mPieChart.setDrawCenterText(true);//饼状图中间可以添加文字
//        mPieChart.setCenterText("hello");
        mPieChart.setUsePercentValues(true);//显示成百分比
        mPieChart.setOnChartValueSelectedListener(this);

        Legend legend=mPieChart.getLegend();
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(11f);
        legend.setTextColor(Color.argb(100,153,153,153));
    }

    private void setData() {
        List<String> xValues=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            xValues.add(i+1+"月");
        }

        List<Entry> entry = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int value = (int) (Math.random() * 10);
            entry.add(new Entry(value, i));
        }

        pieDataSet= new PieDataSet(entry,"测试数据");

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#40a276"));
        colors.add(Color.parseColor("#ffc773"));
        colors.add(Color.parseColor("#72afd9"));
        colors.add(Color.parseColor("#7fd169"));
        pieDataSet.setColors(colors);
        pieDataSet.setSliceSpace(1f);//设置每个饼状图间的距离

        PieData pieData=new PieData(xValues,pieDataSet);
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.WHITE);
        mPieChart.setData(pieData);
    }

    @Override
    public void onValueSelected(Entry entry, int i, Highlight highlight) {
        int index=highlight.getXIndex();
        Toast.makeText(PieChartActivity.this, "select num:"+pieDataSet.getYValForXIndex(index), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

    }
}
