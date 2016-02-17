package com.example.quxiaopeng.alarmtest;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends Activity {

    Button setClockButton;
    Button cancelClockButton;
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        setClockButton = (Button) findViewById(R.id.btn_setClock);
        cancelClockButton = (Button) findViewById(R.id.btn_cancelClock);
        setClockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                //弹出一个时间设置的对话框,供用户选择时间
                new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
//                        c.setTimeInMillis(System.currentTimeMillis());
                        //根据用户选择的时间来设置Calendar对象
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);
                        Intent intent = new Intent(MainActivity.this, ClockReceiver.class);
                        intent.putExtra("hello", "hello");
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
                        //设置AlarmManager在Calendar对应的时间启动Activity
                        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
//                        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
                        //提示闹钟设置完毕
                        Toast.makeText(MainActivity.this, "闹钟设置完毕", Toast.LENGTH_SHORT).show();
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            }
        });
//
//        cancelClockButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alarmManager.cancel(pendingIntent);
//                Toast.makeText(MainActivity.this, "闹钟已取消", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
