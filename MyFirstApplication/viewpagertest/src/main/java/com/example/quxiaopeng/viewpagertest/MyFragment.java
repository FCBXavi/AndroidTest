package com.example.quxiaopeng.viewpagertest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by quxiaopeng on 16/3/8.
 */
public class MyFragment extends Fragment {
    TextView textView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, null);
        textView = (TextView) view.findViewById(R.id.tv_title);
        String data = getArguments().getString("data");
        textView.setText(data);
        return view;
    }
}
