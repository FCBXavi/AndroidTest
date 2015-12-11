package com.example.quxiaopeng.androidcharttest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button linechart = (Button) findViewById(R.id.btn_linechart);
        Button pieChart = (Button) findViewById(R.id.btn_pie_chart);
        Button barChart = (Button) findViewById(R.id.btn_bar_chart);
        Button scatterChart = (Button) findViewById(R.id.btn_scatter_chart);

        linechart.setOnClickListener(this);
        pieChart.setOnClickListener(this);
        barChart.setOnClickListener(this);
        scatterChart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_linechart:
                startActivity(new Intent(this, LineChartActivity.class));
                break;
            case R.id.btn_pie_chart:
                startActivity(new Intent(this, PieChartActivity.class));
                break;
            case R.id.btn_bar_chart:
                startActivity(new Intent(this, BarChartActivity.class));
                break;
            case R.id.btn_scatter_chart:
                startActivity(new Intent(this, ScatterChartActivity.class));
                break;
            default:
                break;
        }
    }
}
