package com.example.quxiaopeng.dashboardtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.text.DecimalFormat;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private CrmDashBoardView crmDashBoardView;
    private Button btnButton;
    private Random random = new Random();
    private DecimalFormat format = new DecimalFormat("#.0");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        TextView tvText = (TextView) findViewById(R.id.tv_text);
//        Typeface customFont = Typeface.createFromAsset(this.getAssets(), "fonts/DINCond-MediumAlternate.otf");
//        tvText.setTypeface(customFont);
//        tvText.setText("4.50");

        crmDashBoardView = (CrmDashBoardView) findViewById(R.id.view_dashboard);
        btnButton = (Button) findViewById(R.id.btn_button);
        btnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float f = random.nextFloat() * 5;
                crmDashBoardView.setMark(Float.parseFloat(format.format(f)));
            }
        });

    }
}
