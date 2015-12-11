package com.example.quxiaopeng.hellochartstest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button lineChart = (Button) findViewById(R.id.btn_linechart);

        lineChart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_linechart:
                startActivity(new Intent(this,LineChartActivity.class));
                break;
            default:
                break;
        }
    }
}
