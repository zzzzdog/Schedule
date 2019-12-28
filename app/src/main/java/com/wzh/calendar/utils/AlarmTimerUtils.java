package com.wzh.calendar.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.wzh.calendar.bean.Schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static android.content.ContentValues.TAG;

/**
 * Created by DELL on 2019/12/20.
 */

public class AlarmTimerUtils {
    public static void setAlarmTimer(Context context, Schedule schedule)
    {
       // Log.i("zzz", schedule.getTitle());
        String cyctime=schedule.getDate()+" "+schedule.starttime+":00";
     //   Log.i("zzz", cyctime);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       long time=0;
        try {
             time = dateformat.parse(cyctime).getTime();
           // Log.i("zzz", String.valueOf(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(time>System.currentTimeMillis()) {
            Intent mytent = new Intent();
            mytent.setAction("com.wzh.calendar.TIMER_ACTION");
            mytent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            String string = new Gson().toJson(schedule);
            //String string="dfsasadasdsadsadasda";
            //Bundle bundle=new Bundle();
            mytent.putExtra("schedule1", string);
            // mytent.putExtra("schedule1","dfs");
            //Log.i("zzz", new Gson().toJson(schedule));
            //mytent.putExtra("zzz",bundle);
            SharedPreferences sharedPreferences = context.getSharedPreferences("idclub", Context.MODE_PRIVATE);
            int alarmId = sharedPreferences.getInt("alarmId", 0);
            sharedPreferences.edit().putInt("alarmId", alarmId + 1).commit();
            //Log.i("zzz", String.valueOf(alarmId));
            PendingIntent sender = PendingIntent.getBroadcast(context, alarmId, mytent, 0);
            AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            alarm.set(AlarmManager.RTC_WAKEUP, time, sender);
            Log.i("zzz", "test");
        }
    }
}
