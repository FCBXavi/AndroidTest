package com.example.quxiaopeng.androidcharttest;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quxiaopeng on 2017/1/4.
 */

public class CrmLineChartActivity extends Activity implements OnChartValueSelectedListener{

    private TextView tvMark;
    private TextView tvDate;

    private MyLineChart mLineChart;
    private List<ILineDataSet> lineDataSets = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crm_linechart);
        initView();
        mLineChart.setOnChartValueSelectedListener(this);
    }

    public void initView() {
        mLineChart = (MyLineChart) findViewById(R.id.crm_line_chart);
        tvMark = (TextView) findViewById(R.id.tv_mark);
        tvMark.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/DINCondensedC.otf"));
        setLineStyle();
        setData();
    }

    public void setLineStyle() {
        mLineChart.setDrawBorders(false);  //是否在折线图上添加边框
        mLineChart.setNoDataText("");
        mLineChart.setNoDataTextDescription("You need to provide data for the chart.");
        mLineChart.setDrawGridBackground(true);//是否显示表格颜色
        mLineChart.setGridBackgroundColor(Color.TRANSPARENT);//表格的颜色
        mLineChart.setTouchEnabled(true);
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(false);
        mLineChart.setPinchZoom(false);// if disabled, scaling can be done on x- and y-axis separately
        mLineChart.getLegend().setEnabled(false);
        mLineChart.getAxisRight().setEnabled(false);
        mLineChart.setDescription("");
        mLineChart.setMinOffset(0);
        mLineChart.setMarkerView(new MarkerView(this, R.layout.view_marker) {
            @Override
            public void refreshContent(Entry e, Highlight highlight) {

            }

            @Override
            public int getXOffset(float xpos) {
                return -getWidth()/2;
            }

            @Override
            public int getYOffset(float ypos) {
                return -getWidth()/2;
            }
        });
        mLineChart.setDrawMarkerViews(true);
        mLineChart.animateX(2000);

//        mLineChart.setHighlightEnabled(true);




        //设置xy轴的属性
        YAxis leftAxis = mLineChart.getAxisLeft();
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawLabels(false);
        leftAxis.enableGridDashedLine(15, 30, 0);
        leftAxis.setGridColor(Color.argb(51, 255, 255, 255));
        leftAxis.setGridLineWidth(1);
        leftAxis.setAxisMinValue(0);
        leftAxis.setAxisMaxValue(5);

        leftAxis.setLabelCount(11, true);
        Log.i("quxiaopeng", leftAxis.getSpaceTop() + "");



//        leftAxis.setStartAtZero(true);
//        leftAxis.setTextColor(Color.parseColor("#9B9B9B"));
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawLabels(false);
    }

    public void setData(){
        List<String> xVals = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            xVals.add(i + 1 + "");
        }

        List<Entry> entries1 = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            float value = (int) (Math.random() * 11) * 0.5f;
            entries1.add(new Entry(value, i));
        }

        LineDataSet lineDataSet1 = new LineDataSet(entries1, "测试数据1");
        lineDataSets.add(lineDataSet1);

        lineDataSet1.setLineWidth(3f);//线宽
//        lineDataSet1.setCircleSize(10f);//圆形的大小
        lineDataSet1.setCircleRadius(0);
        lineDataSet1.setColor(Color.rgb(254,205,192));//显示颜色
//        lineDataSet1.setCircleColor(Color.RED);//圆形的颜色
        lineDataSet1.setHighLightColor(Color.WHITE);//高亮线的颜色
//        lineDataSet1.setCubicIntensity(1);
        lineDataSet1.setHighlightLineWidth(4f);
        lineDataSet1.setDrawCircles(false);//每个点是否有圆形
        lineDataSet1.setDrawValues(false);//每个点处是否有值
//        lineDataSet1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
//        lineDataSet1.setDrawCubic(true);//设置允许曲线平滑
//        lineDataSet1.setCubicIntensity(0.05f);//设置折线的平滑度
//        lineDataSet1.setDrawFilled(true);//设置允许填充
//        lineDataSet1.setFillColor(Color.rgb(0,255,255));//设置填充颜色
        lineDataSet1.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet1.setDrawHorizontalHighlightIndicator(false);

        LineData lineData = new LineData(xVals, lineDataSets);
        mLineChart.setData(lineData);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        tvMark.setText(lineDataSets.get(0).getYValForXIndex(h.getXIndex()) + "");

    }

    @Override
    public void onNothingSelected() {

    }
}
