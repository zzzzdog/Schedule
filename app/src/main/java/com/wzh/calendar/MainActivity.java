package com.wzh.calendar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.wzh.calendar.adapter.ScheduleAdapter;
import com.wzh.calendar.bean.DateEntity;
import com.wzh.calendar.bean.Schedule;
import com.wzh.calendar.dao.ScheduleDao;
import com.wzh.calendar.utils.MyOpenHelp;
import com.wzh.calendar.view.DataView;

import java.util.List;

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
    private ScheduleAdapter scheduleAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        thinglist = (ListView) findViewById(R.id.Thing);
        dataView = (DataView) findViewById(R.id.week);
        myOpenHelp=new MyOpenHelp(this);

        dataView.setOnSelectListener(new DataView.OnSelectListener() {
            @Override
            public void onSelected(DateEntity date) {

                SQLiteDatabase sqLiteDatabase=myOpenHelp.getReadableDatabase();
                ScheduleDao scheduleDao=new ScheduleDao(sqLiteDatabase);
                Schedule schedule=new Schedule();
                schedule.setDate(date.date);
                madata=scheduleDao.query(schedule);
                scheduleAdapter=new ScheduleAdapter(getApplicationContext(), madata);
                thinglist.setAdapter(scheduleAdapter);
                sqLiteDatabase.close();

            }
        });
        dataView.getData("");
    }

}
