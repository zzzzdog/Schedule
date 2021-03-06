package com.wzh.calendar.view;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import com.wzh.calendar.dao.DataCallBack;


/**
 * Created by ly on 2019/12/19.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
    private String time="";
    private String type="";


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //新建日历类用于获取当前时间
        Bundle bd=getArguments();
        type=bd.getString("type");
        int hour = bd.getInt("hour");
        int minute = bd.getInt("minute");
        //返回TimePickerDialog对象
        //因为实现了OnTimeSetListener接口，所以第二个参数直接传入this
        TimePickerDialog temp=new TimePickerDialog(getActivity(), 2,this, hour, minute, true);


        return  temp;
    }


    //实现OnTimeSetListener的onTimeSet方法
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //判断activity是否是DataCallBack的一个实例
        if(getActivity() instanceof DataCallBack){
            //将activity强转为DataCallBack
            DataCallBack dataCallBack = (DataCallBack) getActivity();
            String h="";
            String m="";
            if(hourOfDay<10)
                h="0"+String.valueOf(hourOfDay);
            else
                h=String.valueOf(hourOfDay);
            if(minute<10)
                m="0"+String.valueOf(minute);
            else
                m=String.valueOf(minute);
            time =  h + "时" + m + "分";
            //调用activity的getData方法将数据传回activity显示
            if(this.type.equals("1"))
                dataCallBack.getDataForStart(time);
            else
                dataCallBack.getDataForStop(time);
        }
    }



}
