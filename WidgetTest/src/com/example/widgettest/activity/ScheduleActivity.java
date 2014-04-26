package com.example.widgettest.activity;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.widgettest.R;
import com.example.widgettest.constant.ActivityConst;
import com.example.widgettest.db.*;
import com.example.widgettest.data.*;

/**
 * スケジュール画面のActivity
 */
public class ScheduleActivity extends Activity implements OnClickListener {

	TimePickerDialog timePickerDialog;

	TextView _YearText;
	TextView _MonthText;
	TextView _DayText;
	TextView _HourText;
	TextView _MinuteText;
	TextView _WeatherText_before;
	TextView _WeatherText_after;
	TextView _AdditionalConditionText;
	TextView _DisplayConditionText;
	TextView _ImportanceText;
	TextView _TelopText;
	TextView _ScheduleTitle;
	TextView _ScheduleText;
	Button _BackBtn;
	Button _DeleteBtn;
	Button _EntryBtn;
	Button _DaySetBtn;
	Button _TimeSetBtn;

	// 更新データのプライマリキー　新規登録時はnullとなる
	private Integer _id = null; 
	//スケジュールデータ
	private Schedule _schedule;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);

		_YearText = (TextView)findViewById(R.id.schedule_YearText);
		_MonthText = (TextView)findViewById(R.id.schedule_MonthText);
		_DayText = (TextView)findViewById(R.id.schedule_DayText);
		_HourText = (TextView)findViewById(R.id.schedule_HourText);
		_MinuteText = (TextView)findViewById(R.id.schedule_MinuteText);
		_WeatherText_before = (TextView)findViewById(R.id.schedule_WeatherText_before);
		_WeatherText_after = (TextView)findViewById(R.id.schedule_WeatherText_after);
		_AdditionalConditionText = (TextView)findViewById(R.id.schedule_AdditionalConditionText);
		_DisplayConditionText = (TextView)findViewById(R.id.schedule_DisplayConditionText);
		_ImportanceText = (TextView)findViewById(R.id.schedule_Importance);
		_TelopText  = (TextView)findViewById(R.id.schedule_TelopText);
		_ScheduleTitle =(TextView)findViewById(R.id.schedule_ScheduleTitleInputText);
		_ScheduleText =(TextView)findViewById(R.id.schedule_ScheduleInputText);
		_DeleteBtn  = (Button)findViewById(R.id.schedule_DeleteBtn);
		_EntryBtn   = (Button)findViewById(R.id.schedule_EntryBtn);
		_DaySetBtn  = (Button)findViewById(R.id.scheduleDay_SetBtn);
		_TimeSetBtn = (Button)findViewById(R.id.scheduleTime_SetBtn);

		_BackBtn.setOnClickListener(this);
		_DeleteBtn.setOnClickListener(this);
		_EntryBtn.setOnClickListener(this);
		_DaySetBtn.setOnClickListener(this);
		_TimeSetBtn.setOnClickListener(this);
		
		if (_id == 0) {
			//新規登録時
			_schedule = new Schedule();
			//日付情報の初期設定
			Calendar calendar_Day = Calendar.getInstance();
			
			_schedule.setScheduleYear(calendar_Day.get(Calendar.YEAR));
			_schedule.setScheduleMonth(calendar_Day.get(Calendar.MONTH));
			_schedule.setScheduleDay(calendar_Day.get(Calendar.DAY_OF_MONTH));
			_schedule.setScheduleHour(calendar_Day.get(Calendar.HOUR_OF_DAY));
			_schedule.setScheduleMinute(calendar_Day.get(Calendar.MINUTE));
			
			_WeatherText_before.setVisibility(View.INVISIBLE);
			_WeatherText_after.setVisibility(View.INVISIBLE);
			_AdditionalConditionText.setVisibility(View.INVISIBLE);
			_DisplayConditionText.setVisibility(View.INVISIBLE);
		}else {
			//更新・削除時
			DatabaseHelper heler = new DatabaseHelper(this);
			SQLiteDatabase db = heler.getReadableDatabase();
			ScheduleOperator so = new ScheduleOperator(db);
			_schedule = so.GetScheduleById(_id);
			
		}
		
		//初期表示
		_YearText.setText(_schedule.getScheduleYearInScreen());
		_MonthText.setText(_schedule.getScheduleMonthInScreen());
		_DayText.setText(_schedule.getScheduleDayInScreen());
		_HourText.setText(_schedule.getScheduleHourInScreen());
		_MinuteText.setText(_schedule.getScheduleMinuteInScreen());
		if ( _schedule.getAdditionalCondition() == Schedule.AdditionalCondition.NONE && 
				_schedule.getDisplayCondition() == Schedule.DisplayCondition.NONE) {
			_WeatherText_before.setVisibility(View.INVISIBLE);
			_WeatherText_after.setVisibility(View.INVISIBLE);
			_AdditionalConditionText.setVisibility(View.INVISIBLE);
			_DisplayConditionText.setVisibility(View.INVISIBLE);
		}else {
			_AdditionalConditionText.setText(_schedule.getAdditionalConditionInScreen());
			_DisplayConditionText.setText(_schedule.getDisplayConditionInScreen());
			_WeatherText_before.setVisibility(View.VISIBLE);
			_WeatherText_after.setVisibility(View.VISIBLE);
			_AdditionalConditionText.setVisibility(View.VISIBLE);
			_DisplayConditionText.setVisibility(View.VISIBLE);
		}
		if (_schedule.getImportance() == Schedule.Importance.NONE) {
			_ImportanceText.setVisibility(View.INVISIBLE);
		}else {
			_ImportanceText.setText(_schedule.getImportanceInScreen());
			_ImportanceText.setVisibility(View.VISIBLE);
		}
		_TelopText.setText(_schedule.getDisplayTelopFlagInScreen());
		_ScheduleTitle.setText(_schedule.getScheduleTitle());
		_ScheduleText.setText(_schedule.getScheduleText());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
		// 別の画面から渡されたIDを画面に設定する
		//IDがわたされなかった場合はnullを設定するようにする
		_id = data.getIntExtra(ActivityConst.SCHEDULE_ID, 0);

	}
	
	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem scheduleActItem = menu.add("ScheduleActivity Button");
		MenuItem setActItem = menu.add("SetActivity Button");

		scheduleActItem.setIcon(android.R.drawable.ic_input_add);
		setActItem.setIcon(android.R.drawable.ic_menu_preferences);

		MenuItemCompat.setShowAsAction(scheduleActItem, MenuItem.SHOW_AS_ACTION_ALWAYS);
		MenuItemCompat.setShowAsAction(setActItem, MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return true;
	}

	public void onClick(View v) {

		DatabaseHelper helper = new DatabaseHelper(this);
		SQLiteDatabase db = helper.getReadableDatabase();

		switch(v.getId()){
		case R.id.schedule_DeleteBtn:
			// DB削除処理
			onDelete(db);

			break;

		case R.id.schedule_EntryBtn:
			//DB登録処理
			onRegist(db);

			break;

		case R.id.scheduleDay_SetBtn:
			//日付情報の初期設定
			onDataSet();

			break;

		case R.id.scheduleTime_SetBtn:
			//時刻情報の初期設定
			onTimeSet();

			break;
		}
	}

	/**
	 * 時刻設定処理
	 */
	private void onTimeSet() {
		
		//Calendar calendar_Time 	= Calendar.getInstance();

		//int hourOfDay = Integer.getInteger(_HourText.getText().toString(), 
		//		calendar_Time.get(Calendar.HOUR_OF_DAY)) ;
		//int minute = Integer.getInteger(_MinuteText.getText().toString(), 
		//		calendar_Time.get(Calendar.MINUTE));

		TimePickerDialog timePickerDialog;

		//時刻設定時のリスナ登録
		TimePickerDialog.OnTimeSetListener TimeSetListener = 
				new TimePickerDialog.OnTimeSetListener() {
			public void onTimeSet(TimePicker view, int hour, int minute) {
				_schedule.setScheduleHour(hour);
				_schedule.setScheduleMinute(minute);
				_HourText.setText(_schedule.getScheduleHourInScreen());
				_MinuteText.setText(_schedule.getScheduleMinuteInScreen());
			}
		};

		//時刻設定ダイアログの作成
		timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Black,TimeSetListener,
				_schedule.getScheduleHour(), _schedule.getScheduleMinute(), false);

		//時刻設定ダイアログの表示
		timePickerDialog.show();
	}

	/**
	 * 日付設定処理
	 */
	private void onDataSet() {
		//Calendar calendar_Day = Calendar.getInstance();
		
		//int year = Integer.getInteger(_YearText.getText().toString(),
		//		calendar_Day.get(Calendar.YEAR));
		//int monthOfYear = Integer.getInteger(_MonthText.getText().toString(),
		//		calendar_Day.get(Calendar.MONTH));
		//int dayOfMonth = Integer.getInteger(_DayText.getText().toString(),
		//		calendar_Day.get(Calendar.DAY_OF_MONTH));

		DatePickerDialog datePickerDialog;

		//日付設定時のリスナ作成
		DatePickerDialog.OnDateSetListener DateSetListener = new DatePickerDialog.OnDateSetListener(){
			public void onDateSet(android.widget.DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
				_schedule.setScheduleYear(year);
				_schedule.setScheduleMonth(monthOfYear);
				_schedule.setScheduleDay(dayOfMonth);
				_YearText.setText(_schedule.getScheduleYearInScreen());
				_MonthText.setText(_schedule.getScheduleMonthInScreen());
				_DayText.setText(_schedule.getScheduleDayInScreen());
			}
		};

		//日付設定ダイアログの作成
		datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Black,
				DateSetListener, _schedule.getScheduleYear(), _schedule.getScheduleMonth(), _schedule.getScheduleDay());
		//日付設定ダイアログの表示
		datePickerDialog.show();
	}

	/**
	 * 登録処理
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 * @param minute
	 * @param db
	 */
	private void onRegist(SQLiteDatabase db) {
		
		ScheduleOperator scheduleOperator = new ScheduleOperator(db);
		boolean returnValue = scheduleOperator.RegistData(_schedule, _id);
		
		if (!returnValue){
			Toast.makeText(this, "Update成功", Toast.LENGTH_SHORT).show();
		}

		finish();
	}

	/**
	 * DB削除処理
	 * @param db
	 */
	private void onDelete(SQLiteDatabase db) {

		ScheduleOperator scheduleOperator = new ScheduleOperator(db);
		boolean returnValue = scheduleOperator.DeleteData(_id);
		
		if (!returnValue){
			Toast.makeText(this, "Delete成功", Toast.LENGTH_SHORT).show();
		}

		finish();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();

		finish();

		return true;
	}
}
