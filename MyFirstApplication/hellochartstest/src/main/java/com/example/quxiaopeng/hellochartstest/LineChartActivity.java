package com.example.quxiaopeng.hellochartstest;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by quxiaopeng on 15/12/11.
 */
public class LineChartActivity extends Activity {
    LineChartView mLineChartView;
    LineChartData lineChartData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linechart);
        initView();
    }

    private void initView() {
        mLineChartView = (LineChartView) findViewById(R.id.chart_line);
        setChartStyle();
        setData();
    }

    private void setChartStyle() {
        mLineChartView.setInteractive(true);
        mLineChartView.setZoomType(ZoomType.HORIZONTAL);
        mLineChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
    }

    private void setData() {
        List<AxisValue> axisValues = new ArrayList<>();
        List<PointValue> pointValues= new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            AxisValue axisValue=new AxisValue(i);
            axisValue.setLabel(i+"月");
            axisValues.add(axisValue);

            PointValue pointValue=new PointValue(i, (float) (Math.random()*10));
            pointValues.add(pointValue);
        }

        Line line=new Line(pointValues);
        line.setColor(Color.BLUE).setCubic(false);

        List<Line> lines=new ArrayList<>();
        lines.add(line);
        lineChartData=new LineChartData(lines);

        //坐标轴
        Axis axisX = new Axis();
        axisX.setHasTiltedLabels(false);
        axisX.setTextColor(Color.BLUE);
        axisX.setValues(axisValues);
        axisX.setMaxLabelChars(3);
        axisX.setName("月份");
        lineChartData.setAxisXBottom(axisX);

        Axis axisY = new Axis();
//        axisY.setName("金额");
        axisY.setMaxLabelChars(3);
        axisY.setTextColor(Color.RED);
        lineChartData.setAxisYLeft(axisY);


        mLineChartView.setLineChartData(lineChartData);


    }
}
