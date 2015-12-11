package com.example.quxiaopeng.popupwindowtest;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    View contentView;
    PopupWindow popupWindow;
    Button bt_more;
    List<Map<String, String>> list;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_more = (Button) findViewById(R.id.btn_operate_more);
        bt_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(v);
            }
        });
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

    private void showPopupWindow(View view) {

        list = new ArrayList<Map<String, String>>();
        HashMap<String, String> map1 = new HashMap<String, String>();
        map1.put("key", "复制");
        list.add(map1);
        HashMap<String, String> map2 = new HashMap<String, String>();
        map2.put("key", "删除");
        list.add(map2);
        HashMap<String, String> map3 = new HashMap<String, String>();
        map3.put("key", "修改");
        list.add(map3);
        HashMap<String, String> map4 = new HashMap<String, String>();
        map4.put("key", "添加");
        list.add(map4);
        HashMap<String, String> map5 = new HashMap<String, String>();
        map5.put("key", "查找");
        list.add(map5);



        LayoutInflater inflater = (LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.popupwindow, null);
        popupWindow = new PopupWindow(contentView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        listView = (ListView) contentView.findViewById(R.id.lv_listView);
        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), list, R.layout.list_item,
                new String[]{"key"}, new int[]{R.id.tv_item});
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), list.get(position).get("key"), Toast.LENGTH_SHORT).show();
            }
        });
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

//        listView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//        popupWindow.setWidth(listView.getMeasuredWidth());
//        popupWindow.setHeight((listView.getMeasuredHeight() + 20)
//                * 3);


        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);

        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.abc_ic_menu_selectall_mtrl_alpha));
        popupWindow.showAsDropDown(view);

    }
}
