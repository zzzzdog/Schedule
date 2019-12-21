package com.wzh.calendar.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wzh.calendar.bean.Schedule;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by DELL on 2019/12/11.
 */

public class ScheduleDao {
    private SQLiteDatabase db;

    public ScheduleDao(SQLiteDatabase db) {
        this.db = db;
    }
    public  boolean insert(Schedule schedule)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put("title",schedule.title);
        contentValues.put("starttime",schedule.starttime);
        contentValues.put("endtime",schedule.endtime);
        contentValues.put("isalarmclock",schedule.isalarmclock);
        contentValues.put("place",schedule.place);
        contentValues.put("remarks",schedule.remarks);
        contentValues.put("date",schedule.date);
        long insertResult=db.insert("schedule",null,contentValues);
        if (insertResult==-1)
        {
            return false;
        }
        return true;
    }
    public boolean delete(Schedule schedule)
    {
        int deleteResult=db.delete("schedule","id=?",new String[]{schedule.getId()+""});
        if(deleteResult==0)
        {
            return false;
        }
        return true;
    }
    public boolean update(Schedule schedule)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put("title",schedule.title);
        contentValues.put("starttime",schedule.starttime);
        contentValues.put("endtime",schedule.endtime);
        contentValues.put("isalarmclock",schedule.isalarmclock);
        contentValues.put("place",schedule.place);
        contentValues.put("date",schedule.date);
        int updateResult=db.update("schedule",contentValues,"id=?",new String[]{schedule.getId()+""});
        if(updateResult==0)
        {
            return false;
        }
        return true;
    }
    public List<Schedule> query(Schedule schedule){
        List<Schedule> schedules=new ArrayList<Schedule>();
        Cursor cursor=db.query("schedule",null,"date=?",new String[]{schedule.getDate()},null,null,null);
        while (cursor.moveToNext())
        {
            Schedule schedule1=new Schedule();
            schedule1.setId(cursor.getInt(0));
            schedule1.setTitle(cursor.getString(1));
            schedule1.setStarttime(cursor.getString(2));
            schedule1.setEndtime(cursor.getString(3));
            schedule1.setIsalarmclock(cursor.getInt(4));
            schedule1.setRemarks(cursor.getString(5));
            schedule1.setPlace(cursor.getString(6));
            //schedule1.setRemarks(cursor.getString(7));
            schedule1.setDate(schedule.getDate());
            //schedule1.setDate(cursor.getString(7));
            schedules.add(schedule1);
        }
        return schedules;

    }
}
