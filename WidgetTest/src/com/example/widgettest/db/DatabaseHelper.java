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
    private final static String SCHEDULE_CT_SQL = "create table ScheduleTable (" +
    												" Id integer primary key autoincrement" +
    												",ScheduleYear   integer not null" +
    												",ScheduleMonth  integer not null" +
    												",ScheduleDay    integer not null" +
    												",ScheduleHour   integer not null" +
    												",ScheduleMinute integer not null" +
    												",AdditionalCondition integer not null" +
    												",DisplayCondition integer not null" +
    												",Importance integer not null" +
    												",DisplayTelopFlag boolean not null" +
    												",ScheduleTitle text not null" +
    												",ScheduleText text" +
    												")";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    /*
     * テーブルの作成を行うメソッド
     */
    @Override
    public void onCreate(SQLiteDatabase db) {   	
        db.execSQL(SCHEDULE_CT_SQL);
    }

    /*
     * データベースをバージョンアップする際に呼ばれるメソッド
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}