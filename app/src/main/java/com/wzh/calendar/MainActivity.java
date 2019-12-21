package com.wzh.calendar;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wzh.calendar.adapter.ScheduleAdapter;
import com.wzh.calendar.bean.DateEntity;
import com.wzh.calendar.bean.Schedule;
import com.wzh.calendar.dao.ScheduleDao;
import com.wzh.calendar.utils.AlarmTimerUtils;
import com.wzh.calendar.utils.MyOpenHelp;
import com.wzh.calendar.view.DataView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import android.util.Log;
/**
 * author：Administrator on 2017/46/10 16:03
 * description:文件说明
 * version:版本
 */
public class MainActivity extends FragmentActivity {
    private DataView dataView ;
    private List<Schedule> madata;
    private ListView thinglist;
    private MyOpenHelp myOpenHelp;
    ImageButton imageButton;
    private ScheduleAdapter scheduleAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        thinglist = (ListView) findViewById(R.id.Thing);
        dataView = (DataView) findViewById(R.id.week);
        myOpenHelp=new MyOpenHelp(this);
        imageButton= (ImageButton) findViewById(R.id.addbutton);
        dataView.setOnSelectListener(new DataView.OnSelectListener() {
            @Override
            public void onSelected( DateEntity date) {
              //  initData(date);
                SQLiteDatabase sqLiteDatabase=myOpenHelp.getReadableDatabase();
                ScheduleDao scheduleDao=new ScheduleDao(sqLiteDatabase);
               Schedule schedule=new Schedule();
               final String datev=date.date;
                schedule.setDate(date.date);
                madata=scheduleDao.query(schedule);
               // Log.i("zzz", madata.get(0).getTitle());
                Schedule schedule1=new Schedule();
                schedule1.setDate(date.date);
                schedule1.setTitle("吃饭");
                schedule1.setPlace("食堂");
                schedule1.setStarttime("22:35");
                schedule1.setEndtime("18:00");
                Schedule schedule2=new Schedule();
                schedule2.setDate(date.date);
                schedule2.setTitle("睡觉");
                schedule2.setPlace("宿舍");
                schedule2.setStarttime("21:48");
                schedule2.setEndtime("18:00");
                madata.set(0,schedule1);
                madata.set(1,schedule2);
                AlarmTimerUtils.setAlarmTimer(MainActivity.this,madata.get(0));
                AlarmTimerUtils.setAlarmTimer(MainActivity.this,madata.get(1));
                //showNotification(madata.get(0));
                scheduleAdapter=new ScheduleAdapter(MainActivity.this, madata);
                thinglist.setAdapter(scheduleAdapter);
                final Intent myintent=new Intent();
                sqLiteDatabase.close();
               thinglist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                   @Override
                   public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                       myintent.setClass(MainActivity.this,AddActivity.class);
                       myintent.putExtra("schedule", madata.get(i));
                       myintent.putExtra("type", "update");
                       startActivity(myintent);
                   }
               });
               imageButton.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       myintent.setClass(MainActivity.this,AddActivity.class);
                       myintent.putExtra("date",datev);
                       myintent.putExtra("type", "add");
                       startActivity(myintent);
                   }
               });
               thinglist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                   @Override
                   public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                       registerForContextMenu(thinglist);
                       return false;
                   }
               });

            }
        });
        dataView.getData("");
    }
    public  void initData(DateEntity dateEntity)
    {
        SQLiteDatabase sqLiteDatabase=myOpenHelp.getReadableDatabase();
        ScheduleDao scheduleDao=new ScheduleDao(sqLiteDatabase);
        Schedule schedule=new Schedule();
        schedule.setDate(dateEntity.date);
        schedule.setTitle("吃饭");
        schedule.setPlace("食堂");
        schedule.setStarttime("16:43");
        schedule.setEndtime("18:00");
        scheduleDao.insert(schedule);
        showNotification(schedule);
        sqLiteDatabase.close();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater=new MenuInflater(MainActivity.this);
        menuInflater.inflate(R.menu.mymune,menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId())
        {
            case R.id.change:
                Toast.makeText(MainActivity.this,"编辑",Toast.LENGTH_LONG).show();
                break;
            case R.id.delete:
                showDialog1(info.position);

                break;
        }

        return  true;
    }
    public void showNotification(Schedule schedule)
    {
        String mytime=schedule.date+" "+schedule.starttime+":00";
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      /*  long time=0;
        try {
             time = dateformat.parse(mytime).getTime();
             System.out.print(time);
        } catch (ParseException e) {
            e.printStackTrace();
        } */
        Intent  intent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        NotificationManager manger = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(schedule.getTitle())//设置标题，必要
                .setContentText(schedule.getPlace())//设置内容，必要
                .setWhen(System.currentTimeMillis())//设置时间，默认设置，可以忽略
                .setSmallIcon(R.mipmap.ic_clendr_sch)//设置通知栏的小图标，必须设置
                .setAutoCancel(true)//设置自动删除，点击通知栏信息之后系统会自动将状态栏的通知删除，要与setContentIntent连用
                .setContentIntent(pendingIntent)//设置在通知栏中点击该信息之后的跳转，参数是一个pendingIntent
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_clendr_sch))
                .build();
        manger.notify(1,notification);
    }
    public void showDialog1(final int pos){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("要删除此活动吗？");
        builder.setIcon(R.mipmap.ic_warn_icon);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SQLiteDatabase sqLiteDatabase=myOpenHelp.getReadableDatabase();
                ScheduleDao scheduleDao=new ScheduleDao(sqLiteDatabase);
                scheduleDao.delete(madata.get(pos));
                madata.remove(pos);
                sqLiteDatabase.close();
                scheduleAdapter.notifyDataSetChanged();
                dialogInterface.dismiss();
                Toast.makeText(MainActivity.this,"删除成功",Toast.LENGTH_SHORT);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
               // Toast.makeText(MainActivity.this,"",Toast.LENGTH_SHORT);
            }
        });
        builder.create().show();
    }

}
