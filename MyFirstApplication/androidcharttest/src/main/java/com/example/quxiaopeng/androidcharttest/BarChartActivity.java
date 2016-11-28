package com.example.quxiaopeng.androidcharttest;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quxiaopeng on 15/12/10.
 */
public class BarChartActivity extends Activity implements OnChartValueSelectedListener {

    BarChart mBarChart;
    List<IBarDataSet> barDataSets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchart);
        initView();
    }

    private void initView() {
        mBarChart = (BarChart) findViewById(R.id.bar_chart);
        setChartStyle();
        setData();
        mBarChart.setOnChartValueSelectedListener(this);
    }

    private void setChartStyle() {
//        mBarChart.setDescription("Bar chart");
//        mBarChart.setDrawGridBackground(true);
//        mBarChart.setGridBackgroundColor(Color.TRANSPARENT);
//        mBarChart.setScaleEnabled(false);

        mBarChart.setMaxVisibleValueCount(60);
        mBarChart.setAutoScaleMinMaxEnabled(true);
        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawValueAboveBar(false);
        mBarChart.setDescription("");
        mBarChart.setNoDataText("");
        mBarChart.setPinchZoom(false);
        mBarChart.setDrawGridBackground(false);
        mBarChart.animateY(1000);

        Legend legend=mBarChart.getLegend();
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        legend.setXEntrySpace(62f);


        //X轴属性
        XAxis xAxis=mBarChart.getXAxis();
//        xAxis.setDrawLabels(false);//是否将xvalue中的值显示到屏幕
//        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisLineColor(Color.argb(33, 0, 0, 0));
        xAxis.setLabelsToSkip(2);
        xAxis.setTextColor(Color.parseColor("#9B9B9B"));

        //左侧Y轴属性
        YAxis leftAxis = mBarChart.getAxisLeft();
        leftAxis.setEnabled(true);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);
//        leftAxis.setAxisMinValue(0);
        leftAxis.setTextColor(Color.parseColor("#9B9B9B"));
//        leftAxis.setValueFormatter(new LongValueFormatter());

        //右侧Y轴属性
        YAxis yAxisright=mBarChart.getAxisRight();
        yAxisright.setDrawLabels(false);
        yAxisright.setDrawGridLines(false);
        yAxisright.setDrawAxisLine(false);


        mBarChart.getAxisLeft().setEnabled(true);
        mBarChart.getAxisRight().setEnabled(false);
        mBarChart.setTouchEnabled(true);
        mBarChart.setDragEnabled(true);
        mBarChart.setScaleEnabled(false);
        mBarChart.setPinchZoom(false);
        if(mBarChart.getData() != null)
            mBarChart.getData().setHighlightEnabled(true);

    }

    private void setData() {
        List<String> xValues = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            xValues.add(i + 1 + "月");
        }

        List<BarEntry> entries1 = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            float temp = (float) (Math.random() * 10);
            BarEntry entry=new BarEntry(new float[]{temp,temp+1},i);
            entries1.add(entry);
        }

        List<BarEntry> entries2 = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            float temp = (float) (Math.random() * 10);
            entries2.add(new BarEntry(temp, i));
        }
        BarDataSet barDataSet1 = new BarDataSet(entries1, "测试数据1");
        BarDataSet barDataSet2 = new BarDataSet(entries2, "测试数据2");

        barDataSet1.setStackLabels(new String[]{"数据1","数据2"});
        barDataSet1.setHighLightAlpha(255);
        barDataSet1.setColors(new int[]{Color.parseColor("#FF855A"), Color.parseColor("#FFE7DE")});
        barDataSet1.setHighLightColor(Color.parseColor("#FFB487"));
        barDataSets = new ArrayList<>();
        barDataSets.add(barDataSet1);
//        barDataSets.add(barDataSet2);
        BarData barData = new BarData(xValues, barDataSets);

        mBarChart.setData(barData);

//        String[] stackLabels = new String[2];
//        BarDataSet set = new BarDataSet(yVals, "");
//        int[] colors = new int[2];
//        if (mActivityType == AppConstants.ACTIVITY_TYPE_CONTRACT_AMOUNT) {
//            stackLabels[0] = "合同回款金额";
//            stackLabels[1] = "合同签约金额";
//            set.setHighLightColor(Color.parseColor("#89CFFF"));
//            colors[0] = Color.parseColor("#72AFD9");
//            colors[1] = Color.parseColor("#E3EFF8");
//        } else {
//            stackLabels[0] = "合同回款计划";
//            stackLabels[1] = "合同回款记录";
//            set.setHighLightColor(Color.parseColor("#FFB487"));
//            colors[0] = Color.parseColor("#FF855A");
//            colors[1] = Color.parseColor("#FFE7DE");
//        }
//        set.setStackLabels(stackLabels);
//        set.setDrawValues(false);
//        set.setHighLightAlpha(255);
//        set.setColors(colors);
    }

    @Override
    public void onValueSelected(Entry entry, int i, Highlight highlight) {
        int index = highlight.getXIndex();
        Toast.makeText(BarChartActivity.this, "select num" + barDataSets.get(0).getYValForXIndex(index), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

    }
}
