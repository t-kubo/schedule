package com.example.widgettest.db;

import java.util.ArrayList;
import java.util.List;

import com.example.widgettest.data.Schedule;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author eddieVH
 *
 */
public class ScheduleOperator {

	private SQLiteDatabase db;
	/**
	 * コンストラクタ
	 * @param db データベース
	 */
	public ScheduleOperator(SQLiteDatabase db){
		this.db = db;
	}
	
	/**
	 * スケジュールデータを取得
	 * @param id ID
	 * @return スケジュールデータ
	 */
	public Schedule GetScheduleById(int id){
		Cursor cursor = db.rawQuery("select * from ScheduleTable where Id=" + id,null);
		
		Schedule schedule = null;
		Boolean isEof = cursor.moveToFirst();
		while (isEof) {
			schedule = new Schedule();
			schedule.setId(cursor.getInt(cursor.getColumnIndex("Id")));
			schedule.setScheduleYear(cursor.getInt(cursor.getColumnIndex("ScheduleYear")));
			schedule.setScheduleMonth(cursor.getInt(cursor.getColumnIndex("ScheduleMonth")));
			schedule.setScheduleDay(cursor.getInt(cursor.getColumnIndex("ScheduleDay")));
			schedule.setScheduleHour(cursor.getInt(cursor.getColumnIndex("ScheduleHour")));
			schedule.setScheduleMinute(cursor.getInt(cursor.getColumnIndex("ScheduleMinute")));
			schedule.setAdditionalCondition(cursor.getInt(cursor.getColumnIndex("AdditionalCondition")));
			schedule.setDisplayCondition(cursor.getInt(cursor.getColumnIndex("DisplayCondition")));
			schedule.setImportance(cursor.getInt(cursor.getColumnIndex("Importance")));
			schedule.setDisplayTelopFlag(cursor.getInt(cursor.getColumnIndex("DisplayTelopFlag"))>0);
			schedule.setScheduleTitle(cursor.getString(cursor.getColumnIndex("ScheduleTitle")));
			schedule.setScheduleText(cursor.getString(cursor.getColumnIndex("ScheduleText")));
			isEof = cursor.moveToNext();
		}
		
		return schedule;
	}
	
	
	/**
	 * すべてのスケジュールを取得
	 * @return スケジュールデータリスト
	 */
	public List<Schedule> GetAllSchedule(){
		List<Schedule> schedules = new ArrayList<Schedule>();
		try {
			Cursor cursor = db.rawQuery("select * from ScheduleTable",null);
			
			Boolean isEof = cursor.moveToFirst();
			while (isEof) {
				Schedule schedule = new Schedule();
				schedule.setId(cursor.getInt(cursor.getColumnIndex("Id")));
				schedule.setScheduleYear(cursor.getInt(cursor.getColumnIndex("ScheduleYear")));
				schedule.setScheduleMonth(cursor.getInt(cursor.getColumnIndex("ScheduleMonth")));
				schedule.setScheduleDay(cursor.getInt(cursor.getColumnIndex("ScheduleDay")));
				schedule.setScheduleHour(cursor.getInt(cursor.getColumnIndex("ScheduleHour")));
				schedule.setScheduleMinute(cursor.getInt(cursor.getColumnIndex("ScheduleMinute")));
				
				schedules.add(schedule);
				
				isEof = cursor.moveToNext();
			}
		}finally {
			db.close();
		}
		return schedules;
	}
	
	public boolean RegistData(Schedule schedule, int id) {
		
		long returnValue;
		
		//登録データ取得
		ContentValues values = new ContentValues();
		values.put("ScheduleYear", schedule.getScheduleYear());
		values.put("ScheduleMonth", schedule.getScheduleMonth());
		values.put("ScheduleDay", schedule.getScheduleDay());
		values.put("ScheduleHour", schedule.getScheduleHour());
		values.put("ScheduleMinute", schedule.getScheduleMinute());
		values.put("AdditionalCondition", schedule.getAdditionalCondition());
		values.put("DisplayCondition", schedule.getDisplayCondition());
		values.put("Importance", schedule.getImportance());
		values.put("DisplayTelopFlag", schedule.getDisplayTelopFlag());
		values.put("ScheduleTitle", schedule.getScheduleTitle());
		values.put("ScheduleText", schedule.getScheduleText());
		
		try {
			if (schedule.getId() == 0){
				//新規登録
				//String sql = "insert into ScheduleTable (ScheduleYear,ScheduleMonth," +
				//			 "ScheduleDay,ScheduleHour,ScheduleMinute,AdditionalCondition" +
				//			 "DisplayCondition,Importance,DisplayTelopFlag,ScheduleTitle,ScheduleText) " +
				//			 "values (" + schedule.getScheduleYear() + "," + schedule.getScheduleMonth() + "," +
				//			 schedule.getScheduleDay() + ","+schedule.getScheduleHour() + ","+schedule.getScheduleMinute() +
				//			 "," + schedule.getAdditionalCondition() + "," + schedule.getDisplayCondition() + "," +
				//			 schedule.getImportance() + "," + schedule.get
				
				returnValue = db.insert("ScheduleTable", null, values);
			}else {
				//更新
				returnValue = db.update("ScheduleTable", values, "No = '" + id + "'", null);
			}
		}finally {
			db.close();
		}
		
		return (returnValue == 0);
	}
	
public boolean DeleteData(int id) {
		
		long returnValue;
		
		try {
			returnValue = db.delete("ScheduleTable", "No = '" + id + "'", null);
		}finally {
			db.close();
		}
		
		return (returnValue == 0);
	}
}