package com.wzh.calendar.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DELL on 2019/12/11.
 */

public class MyOpenHelp extends SQLiteOpenHelper {
    private static final String name="my.db";
    private static final int version=1;

    public MyOpenHelp(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+" schedule( id INTEGER PRIMARY KEY AUTOINCREMENT ,"+"title varchar(32),starttime varchar(32), endtime varchar(32), isalarmclock  INTEGER,remarks varchar(32), place varchar(32), date varchar(32))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
