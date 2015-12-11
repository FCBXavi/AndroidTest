package com.example.quxiaopeng.notificationtest;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

public class MainActivity extends Activity {

    private Button btn1;
    private Button btn2;
    private Button btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Notification.Builder builder = new Notification.Builder(this)
//                .setSmallIcon(R.drawable.notification_template_icon_bg)//若没有设置largeicon，此为左边的大icon，设置了largeicon，则为右下角的小icon，无论怎样，都影响Notifications area显示的图标
//                .setContentTitle("content title") //标题
//                .setContentText("content  text")  //正文
//                .setNumber(3)                       //设置信息条数
//                .setDefaults(Notification.DEFAULT_SOUND) //设置声音，此为默认声音
//                .setVibrate(new long[]{300, 100, 300, 100}) //设置震动，此震动数组为：long vT[]={300,100,300,100}; 还可以设置灯光.setLights(argb, onMs, offMs)
//                .setOngoing(true)                   //true使notification变为ongoing，用户不能手动清除，类似QQ,false或者不设置则为普通的通知
//                .setAutoCancel(true);               //点击之后自动消失
//
//        Intent intent = new Intent(this,Result.class);
//        //实例化一个TaskStackBuilder ,用于添加动作，就像一个stack一样，一个一个压进去
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        //添加父stack，添加下一个intent
//        stackBuilder.addParentStack(this);
//        stackBuilder.addNextIntent(intent);
//
//        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
//        //把刚才的pending添加进去
//        builder.setContentIntent(pendingIntent);
//        //获得NotificationManager
//        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        //mBuilder.build()会返回一个Notifications对象，1000为Notifications的id，可变动。就可以notify出来了。
//        notificationManager.notify(1000,builder.build());
        btn1 = (Button) findViewById(R.id.bt_btn1);
        btn2 = (Button) findViewById(R.id.bt_btn2);
        setlistener();
    }

    private void setlistener() {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMusicPlayerNotification("qq音乐",R.drawable.wechat,R.drawable.wechat,R.drawable.wechat,"单车","陈奕迅");
            }
        });

    }

    public void showNotification() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wechat);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setLargeIcon(bitmap)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("您有五条未读消息")
                .setContentText("congtentText")
                .setNumber(5)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(false);
        Intent intent = new Intent(this, Result.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(this);
        stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1, builder.build());
    }

    public void showMusicPlayerNotification(String tickerText, int id, int resId, int photoId, String songName, String singerName) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Notification notification = new Notification(resId, tickerText,
                System.currentTimeMillis());

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                getIntent(), 0);

        // 自定义界面
        RemoteViews remoteView = new RemoteViews(getPackageName(),
                R.layout.notification);
        remoteView.setTextViewText(R.id.tv_song_name, songName);
        remoteView.setTextViewText(R.id.tv_singer_name, singerName);
        remoteView.setImageViewResource(R.id.iv_singer_photo,
                R.drawable.wechat);
        notification.contentView = remoteView;

        notificationManager.notify(id, notification);
    }

}
