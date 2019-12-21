package com.wzh.calendar.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.wzh.calendar.MainActivity;
import com.wzh.calendar.R;
import com.wzh.calendar.bean.Schedule;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by DELL on 2019/12/20.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private NotificationManager m_notificationMgr = null;
    private static final int NOTIFICATION_FLAG = 3;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("com.wzh.calendar.TIMER_ACTION")) {

           // Schedule schedule = (Schedule) intent.getSerializableExtra("schedule1");
         //   Schedule schedule=new Gson().fromJson(intent.getStringExtra("Schedule1"),Schedule.class);
            //Schedule schedule=new Gson().fromJson(data.getString("schedule1"),Schedule.class);
            String s=intent.getStringExtra("schedule1");
            Schedule schedule=new Gson().fromJson(s,Schedule.class);
           // Log.i("zzz", s);
            m_notificationMgr = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            Intent intent1 = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0);
            NotificationManager manger = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            Notification notification = new NotificationCompat.Builder(context)
                    .setContentTitle(schedule.getTitle())//设置标题，必要
                    .setContentText(schedule.getPlace())//设置内容，必要
                    .setWhen(System.currentTimeMillis())//设置时间，默认设置，可以忽略
                    .setSmallIcon(R.mipmap.ic_clendr_sch)//设置通知栏的小图标，必须设置
                    .setAutoCancel(true)//设置自动删除，点击通知栏信息之后系统会自动将状态栏的通知删除，要与setContentIntent连用
                    .setContentIntent(pendingIntent)//设置在通知栏中点击该信息之后的跳转，参数是一个pendingIntent
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_clendr_sch))
                    .build();
            manger.notify(NOTIFICATION_FLAG, notification);
        }
    }
}
