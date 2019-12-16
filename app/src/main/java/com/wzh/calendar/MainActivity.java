package com.wzh.calendar;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
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

                SQLiteDatabase sqLiteDatabase=myOpenHelp.getReadableDatabase();
                ScheduleDao scheduleDao=new ScheduleDao(sqLiteDatabase);
               Schedule schedule=new Schedule();
               final String datev=date.date;
                schedule.setDate(date.date);
                madata=scheduleDao.query(schedule);
                scheduleAdapter=new ScheduleAdapter(getApplicationContext(), madata);
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
                SQLiteDatabase sqLiteDatabase=myOpenHelp.getReadableDatabase();
                ScheduleDao scheduleDao=new ScheduleDao(sqLiteDatabase);
                scheduleDao.delete(madata.get(info.position));
                madata.remove(info.position);
                sqLiteDatabase.close();
                scheduleAdapter.notifyDataSetChanged();
                break;
        }

        return  true;
    }


}
