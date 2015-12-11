package com.example.quxiaopeng.mylistview;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView=(ListView)findViewById(R.id.lv_myListView);
        final ArrayList<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
        HashMap<String,Object>map1=new HashMap<String,Object>();
        map1.put("text", "通知");
        map1.put("picture",R.drawable.notice);
        HashMap<String,Object>map2=new HashMap<String,Object>();
        map2.put("text", "邮箱");
        map2.put("picture",R.drawable.mail);
        HashMap<String,Object>map3=new HashMap<String,Object>();
        map3.put("text", "任务");
        map3.put("picture",R.drawable.notice);
        HashMap<String,Object>map4=new HashMap<String,Object>();
        map4.put("text", "审批");
        map4.put("picture", R.drawable.mail);
        HashMap<String,Object>map5=new HashMap<String,Object>();
        map5.put("text", "公告");
        map5.put("picture",R.drawable.notice);
        HashMap<String,Object>map6=new HashMap<String,Object>();
        map6.put("text", "日程");
        map6.put("picture",R.drawable.mail);
        HashMap<String,Object>map7=new HashMap<String,Object>();
        map7.put("text", "客户管理");
        map7.put("picture",R.drawable.notice);
        HashMap<String,Object>map8=new HashMap<String,Object>();
        map8.put("text", "外勤");
        map8.put("picture",R.drawable.mail);
        HashMap<String,Object>map9=new HashMap<String,Object>();
        map9.put("text", "汇报");
        map9.put("picture",R.drawable.notice);
        HashMap<String,Object>map10=new HashMap<String,Object>();
        map10.put("text", "文档");
        map10.put("picture", R.drawable.mail);
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        list.add(map5);
        list.add(map6);
        list.add(map7);
        list.add(map8);
        list.add(map9);
        list.add(map10);

        final SimpleAdapter adapter=new SimpleAdapter(getApplicationContext(), list, R.layout.list_item, new String[]{"text", "picture"}, new int[]{R.id.tv_text, R.id.iv_icon});
        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, list.get(position).get("text").toString(), Toast.LENGTH_SHORT).show();
                //adapter.notifyDataSetChanged();
            }
        });

//        List<String> list=new ArrayList<String>();
//        list.add("通知");
//        list.add("邮箱");
//        list.add("任务");
//        list.add("审批");
//        MyAdapter adapter=new MyAdapter(getApplicationContext(),list);
//        listView.setAdapter(adapter);

        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(getResources(),R.drawable.mail,options);
        int height=options.outHeight;
        int width=options.outHeight;
        String type=options.outMimeType;
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
