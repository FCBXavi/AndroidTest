package com.example.quxiaopeng.testui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends ActionBarActivity {

    final static int LOADING=0;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       ImageView imageView=(ImageView)findViewById(R.id.iv_image);
        final Drawable drawable=imageView.getDrawable();
        final Timer timer=new Timer();

        handler=new Handler(){
            public void handleMessage(Message msg){
                if(msg.what==LOADING){
                    drawable.setLevel(drawable.getLevel()+200);
                    Log.i("MyApplication", "loading");
                    Toast.makeText(MainActivity.this, "loading", Toast.LENGTH_SHORT).show();
                }

            }
        };

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message=handler.obtainMessage();
                message.what=LOADING;
                handler.sendMessage(message);
                Log.i("MyApplication", "loading1");
                if(drawable.getLevel()>=10000){
                    timer.cancel();
                }
            }
        },0,300);



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
