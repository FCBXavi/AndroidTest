package com.example.quxiaopeng.gridviewtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final List<HashMap<String,Object>>list=new ArrayList<>();
        for(int i=0;i<9;i++){
            HashMap<String ,Object>map=new HashMap<>();
            map.put("text","text"+i);
            map.put("image",R.drawable.icon);
            list.add(map);
        }
        GridView gridView=(GridView)findViewById(R.id.gv_myGridView);
        SimpleAdapter adapter = new SimpleAdapter(MainActivity.this,list,R.layout.grid_item,new String[]{"text","image"},new int[]{R.id.tv_text,R.id.iv_image});
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this,list.get(i).get("text").toString(),Toast.LENGTH_SHORT).show();
                setTitle(list.get(i).get("text").toString());
            }
        });
    }

}
