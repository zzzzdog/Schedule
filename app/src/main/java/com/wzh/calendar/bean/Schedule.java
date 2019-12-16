package com.wzh.calendar.bean;

import java.io.Serializable;

/**
 * Created by DELL on 2019/12/11.
 */

public class Schedule implements Serializable{
    public String title;
    public String starttime;
    public String endtime;
    public int isalarmclock;
    public String remarks;
    public String place;
    public String date;
    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getStarttime() {
        return starttime;
    }

    public String getEndtime() {
        return endtime;
    }


    public String getRemarks() {
        return remarks;
    }

    public String getPlace() {
        return place;
    }

    public String getDate() {
        return date;
    }

    public int getIsalarmclock() {
        return isalarmclock;
    }

    public void setIsalarmclock(int isalarmclock) {
        this.isalarmclock = isalarmclock;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
