package com.wzh.calendar;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.wzh.calendar.bean.Schedule;
import com.wzh.calendar.dao.DataCallBack;
import com.wzh.calendar.dao.ScheduleDao;
import com.wzh.calendar.utils.MyOpenHelp;
import com.wzh.calendar.view.TimePickerFragment;

import java.util.Calendar;

public class AddActivity extends Activity implements DataCallBack {
    EditText edittext_scheduleTitle;
    EditText edittext_schedulePlace;
    EditText edittext_scheduleRemarks;

    TextView text_scheduleStartTime;
    TextView text_scheduleStopTime;
    TextView text_scheduleDate;
    Switch sw_isAlarmClock;

    Button addConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //
        edittext_scheduleTitle=(EditText)findViewById(R.id.schedule_title);
        edittext_schedulePlace=(EditText)findViewById(R.id.schedule_place);
        edittext_scheduleRemarks=(EditText)findViewById(R.id.schedule_remarks);
        text_scheduleDate= (TextView) findViewById(R.id.text_scheduleDate);
        text_scheduleStartTime=(TextView)findViewById(R.id.schedule_startTime);
        text_scheduleStopTime=(TextView)findViewById(R.id.schedule_stopTime);
        sw_isAlarmClock=(Switch)findViewById(R.id.schedule_isAlarmClock);
        addConfirm=(Button)findViewById(R.id.addConfirm);
        final MyOpenHelp myOpenHelp=new MyOpenHelp(this);

        if("update".equals(getIntent().getStringExtra("type"))) {   //进行更新操作，传入值为Schedule对象
            Schedule schedule = (Schedule) getIntent().getSerializableExtra("schedule");

            text_scheduleDate.setText(schedule.getDate());
            edittext_scheduleTitle.setText(schedule.getTitle());
            edittext_schedulePlace.setText(schedule.getPlace());
            edittext_scheduleRemarks.setText(schedule.getRemarks());
            text_scheduleStartTime.setText(schedule.getStarttime());
            text_scheduleStopTime.setText(schedule.getEndtime());
            if(schedule.getIsalarmclock()==1)
                sw_isAlarmClock.setChecked(true);



        }
        else{                                                               //进行添加操作，传入值为日期
            final String schedule_date=getIntent().getStringExtra("date");
            text_scheduleDate.setText(schedule_date);
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            //时间选择功能，用dialogfragment实现
            text_scheduleStartTime.setText("开始时间:"+hour+"时"+minute+"分");


            text_scheduleStopTime.setText("结束时间:"+hour+"时"+minute+"分");




        }
        text_scheduleStartTime.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String temp=text_scheduleStartTime.getText().toString();
                int hour=Integer.parseInt(temp.substring(temp.indexOf(":")+1,temp.indexOf("时",temp.indexOf("时")+1)));
                int minute=Integer.parseInt(temp.substring(temp.indexOf("时",temp.indexOf("时")+1)+1,temp.indexOf("分")));
                Bundle fgbd=new Bundle();
                fgbd.putString("type","1");
                fgbd.putInt("hour",hour);
                fgbd.putInt("minute",minute);
                TimePickerFragment timePickerFragment=new TimePickerFragment();
                timePickerFragment.setArguments(fgbd);
                timePickerFragment.show(getFragmentManager(),"time_picker");
            }
        });

        text_scheduleStopTime.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String temp=text_scheduleStopTime.getText().toString();
                int hour=Integer.parseInt(temp.substring(temp.indexOf(":")+1,temp.indexOf("时",temp.indexOf("时")+1)));
                int minute=Integer.parseInt(temp.substring(temp.indexOf("时",temp.indexOf("时")+1)+1,temp.indexOf("分")));
                Bundle fgbd=new Bundle();
                fgbd.putInt("hour",hour);
                fgbd.putInt("minute",minute);
                fgbd.putString("type","2");
                TimePickerFragment timePickerFragment2=new TimePickerFragment();
                timePickerFragment2.setArguments(fgbd);
                timePickerFragment2.show(getFragmentManager(),"time_picker2");
            }
        });
        addConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Schedule addSchedule=new Schedule();
                addSchedule.setDate(text_scheduleDate.getText().toString());
                addSchedule.setTitle(edittext_scheduleTitle.getText().toString());
                addSchedule.setPlace(edittext_schedulePlace.getText().toString());
                addSchedule.setRemarks(edittext_scheduleRemarks.getText().toString());
                addSchedule.setStarttime(text_scheduleStartTime.getText().toString());
                addSchedule.setEndtime(text_scheduleStopTime.getText().toString());
                int ischecked;
                if(sw_isAlarmClock.isChecked())
                    ischecked=1;
                else
                    ischecked=0;
                addSchedule.setIsalarmclock(ischecked);

                SQLiteDatabase sqLiteDatabase= myOpenHelp.getReadableDatabase();
                ScheduleDao scheduleDao=new ScheduleDao(sqLiteDatabase);
                if(scheduleDao.insert(addSchedule))
                    Log.i("isInserted","**** new schedule is inserted! ****");
                else
                    Log.i("isInserted","**** new schedule insertion fail! ****");
                finish();
            }
        });

    }


    @Override
    public void getDataForStart(String Data){

        text_scheduleStartTime.setText("开始时间:"+Data);

    }
    @Override
    public void getDataForStop(String Data){
        text_scheduleStopTime.setText("结束时间:"+Data);
    }

}
