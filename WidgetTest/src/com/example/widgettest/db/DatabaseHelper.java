package com.example.widgettest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * データベースを作成するクラス
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    //データベース名
    private final static String DB_NAME = "TelopSchedule_DB";
    //データベースVer
    private final static int DB_VER = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    /*
     * テーブルの作成を行うメソッド
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "";
        sql += "create table ScheduleTable (";
        sql += " No integer primary key autoincrement";
        sql += ",ScheduleYear   integer not null";
        sql += ",ScheduleMonth  integer not null";
        sql += ",ScheduleDay    integer not null";
        sql += ",ScheduleAMPM   integer not null";
        sql += ",ScheduleHour   integer not null";
        sql += ",ScheduleMinute integer not null";
        sql += ")";
        db.execSQL(sql);
    }

    /*
     * データベースをバージョンアップする際に呼ばれるメソッド
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}