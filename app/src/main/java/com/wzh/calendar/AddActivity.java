package com.wzh.calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.wzh.calendar.bean.Schedule;

public class AddActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        TextView textView= (TextView) findViewById(R.id.test);
        if("update".equals(getIntent().getStringExtra("type"))) {   //进行更新操作，传入值为Schedule对象
            Schedule schedule = (Schedule) getIntent().getSerializableExtra("schedule");
            textView.setText(schedule.getTitle()+schedule.getPlace());
        }
        else{                                                               //进行添加操作，传入值为日期
            String date=getIntent().getStringExtra("date");
            textView.setText(date);
        }


    }
}
