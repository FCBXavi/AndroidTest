package com.example.quxiaopeng.diffutiltest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Button mBtnRefresh;
    private List<Student> mList = new ArrayList<>();
    private Random mRandom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnRefresh = (Button) findViewById(R.id.btn_refresh);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_student);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRandom = new Random();
        for (int i = 0; i < 10; i++) {
            mList.add(new Student(String.valueOf(mRandom.nextInt(3000)), "Student : " + i));
        }
        final StudentAdapter adapter = new StudentAdapter(this, mList);
        mRecyclerView.setAdapter(adapter);

        mBtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mList.size() > 0) {
                    mList.remove(0);
                }
                for (int i = 0; i < mRandom.nextInt(3); i++) {
//                    int index = mRandom.nextInt(mList.size() - 1);
                    int index = mList.size();
                    Student student = new Student(String.valueOf(mRandom.nextInt(3000)), "new Student" + mRandom.nextInt());
                    mList.add(index, student);
                }

                adapter.notifyDiff();
            }
        });
    }
}
