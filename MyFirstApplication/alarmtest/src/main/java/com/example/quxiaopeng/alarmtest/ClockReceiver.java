package com.example.quxiaopeng.alarmtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by quxiaopeng on 16/2/16.
 */
public class ClockReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String str = intent.getStringExtra("hello");
        Intent i = new Intent(context, ClockActivity.class);
        i.putExtra("hello", str);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, 0);
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);
//        Toast.makeText(context, "闹钟响了", Toast.LENGTH_SHORT).show();
//        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
        contentView.setImageViewResource(R.id.image, R.drawable.icon);
        contentView.setTextViewText(R.id.title, "title");
        contentView.setTextViewText(R.id.content, "content");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(context).setTicker("收到新通知").setSmallIcon(R.drawable.icon).
                setNumber(1).setWhen(System.currentTimeMillis()).setContentIntent(pendingIntent).build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.contentView = contentView;
//        notificationManager.notify(1,notification);

        //FLAG_AUTO_CANCEL   该通知能被状态栏的清除按钮给清除掉
        //FLAG_NO_CLEAR      该通知不能被状态栏的清除按钮给清除掉
        //FLAG_ONGOING_EVENT 通知放置在正在运行
        //FLAG_INSISTENT     是否一直进行，比如音乐一直播放，知道用户响应
//        Notification notification = new Notification(R.drawable.icon, "收到一条通知", System.currentTimeMillis());
        notification.flags |= Notification.FLAG_ONGOING_EVENT; // 将此通知放到通知栏的"Ongoing"即"正在运行"组中
        notification.flags |= Notification.FLAG_NO_CLEAR; // 表明在点击了通知栏中的"清除通知"后，此通知不清除，经常与FLAG_ONGOING_EVENT一起使用
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        //DEFAULT_ALL     使用所有默认值，比如声音，震动，闪屏等等
        //DEFAULT_LIGHTS  使用默认闪光提示
        //DEFAULT_SOUNDS  使用默认提示声音
        //DEFAULT_VIBRATE 使用默认手机震动，需加上<uses-permission android:name="android.permission.VIBRATE" />权限
        notification.defaults = Notification.DEFAULT_LIGHTS;
//
        notificationManager.notify(notification.flags,notification);
    }
}
