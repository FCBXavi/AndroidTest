package com.example.quxiaopeng.pulltorefreshlistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //MyListView myListView=new MyListView(getApplicationContext());
        MyListView myListView=(MyListView)findViewById(R.id.myListView);
        List<Map<String,Object>>list=new ArrayList<>();
        for (int i=0;i<20;i++){
            Map<String,Object>map=new HashMap<>();
            map.put("text","text"+i);
            list.add(map);
        }
        SimpleAdapter adapter=new SimpleAdapter(MainActivity.this,list,R.layout.item,new String[]{"text"},new int[]{R.id.tv_text});
        myListView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
