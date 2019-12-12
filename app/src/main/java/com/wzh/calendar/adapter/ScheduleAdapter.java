package com.wzh.calendar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.wzh.calendar.R;
import com.wzh.calendar.bean.Schedule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2019/12/11.
 */

public class ScheduleAdapter extends android.widget.BaseAdapter{
    private Context context;
    private List<Schedule> dates;
    private LayoutInflater inflater;

    public ScheduleAdapter(Context context,List<Schedule> dates) {
        this.context = context;
        this.dates=dates;
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public Object getItem(int i) {
        return dates.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyHolder mholder;
        if(view==null)
        {
            mholder=new MyHolder();
            view= View.inflate(context, R.layout.item_schedule, null);
            mholder.starttime= (TextView) view.findViewById(R.id.starttime);
            mholder.endtime= (TextView) view.findViewById(R.id.endtime);
            mholder.title= (TextView) view.findViewById(R.id.title);
            mholder.place= (TextView) view.findViewById(R.id.place);
            view.setTag(mholder);
        }
        else{
            mholder= (MyHolder) view.getTag();
        }
        mholder.starttime.setText(dates.get(i).getStarttime());
        mholder.endtime.setText(dates.get(i).getEndtime());
        mholder.title.setText(dates.get(i).getTitle());
        mholder.place.setText(dates.get(i).getPlace());
        return view;
    }
    public class MyHolder {
        TextView starttime;
        TextView endtime;
        TextView title;
        TextView place;
    }
}
