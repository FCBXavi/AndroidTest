package com.example.quxiaopeng.leakcanarytest;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Button button = (Button) findViewById(R.id.btn_button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this,NewActivity.class));
//            }
//        });

        Box box = new Box();

// 薛定谔之猫
        Cat schrodingerCat = new Cat();
        box.hiddenCat = schrodingerCat;
        Docker.container = box;
//        RefWatcher refWatcher = LeakCanary.install(MyApplication.getInstance());
//        refWatcher.watch(schrodingerCat);
    }
}
