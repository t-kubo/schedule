package com.example.widgettest.activity;

import java.util.Calendar;

import com.example.widgettest.R;
import com.example.widgettest.db.DatabaseHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
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

/**
 * スケジュール画面のActivity
 */
public class ScheduleActivity extends Activity implements OnClickListener {

	TimePickerDialog timePickerDialog;

	Button _BackBtn;
	Button _DeleteBtn;
	Button _EntryBtn;
	Button _DaySetBtn;
	Button _TimeSetBtn;

	TextView _YearText;
	TextView _MonthText;
	TextView _DayText;
	TextView _AMPMText;
	TextView _HourText;
	TextView _MinuteText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);

		_BackBtn    = (Button)findViewById(R.id.schedule_BackBtn);
		_DeleteBtn  = (Button)findViewById(R.id.schedule_DeleteBtn);
		_EntryBtn   = (Button)findViewById(R.id.schedule_EntryBtn);
		_DaySetBtn  = (Button)findViewById(R.id.scheduleDay_SetBtn);
		_TimeSetBtn = (Button)findViewById(R.id.scheduleTime_SetBtn);

		_BackBtn.setOnClickListener(this);
		_DeleteBtn.setOnClickListener(this);
		_EntryBtn.setOnClickListener(this);
		_DaySetBtn.setOnClickListener(this);
		_TimeSetBtn.setOnClickListener(this);

		_YearText   = (TextView)findViewById(R.id.schedule_YearText);
		_MonthText  = (TextView)findViewById(R.id.schedule_MonthText);
		_DayText    = (TextView)findViewById(R.id.schedule_DayText);
		_AMPMText   = (TextView)findViewById(R.id.schedule_AMPMText);
		_HourText   = (TextView)findViewById(R.id.schedule_HourText);
		_MinuteText = (TextView)findViewById(R.id.schedule_MinuteText);
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem actionItem = menu.add("MainActivity Button");

		actionItem.setIcon(android.R.drawable.ic_menu_today);

		MenuItemCompat.setShowAsAction(actionItem, MenuItem.SHOW_AS_ACTION_ALWAYS);

		return true;
	}

	public void onClick(View v) {
		int year = 0;
		int monthOfYear = 0;
		int dayOfMonth = 0;
		int hourOfDay = 0;
		int minute = 0;

		DatabaseHelper helper = new DatabaseHelper(this);
		SQLiteDatabase db = helper.getReadableDatabase();

		switch(v.getId()){
		case R.id.schedule_BackBtn:
			finish();

			break;

		case R.id.schedule_DeleteBtn:
			//DB削除処理
			int ret_Delete;
			try {
				ret_Delete = db.delete("ScheduleTable", "No = '1'", null);
			} finally {
				db.close();
			}
			if (ret_Delete == -1){
				Toast.makeText(this, "Delete失敗", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Delete成功", Toast.LENGTH_SHORT).show();
			}

			finish();

			break;

		case R.id.schedule_EntryBtn:
			//DB登録処理
			ContentValues values = new ContentValues();
			values.put("ScheduleYear",   year);
			values.put("ScheduleMonth",  monthOfYear);
			values.put("ScheduleDay",    dayOfMonth);
			values.put("ScheduleAMPM",   "AM");
			values.put("ScheduleHour",   dayOfMonth);
			values.put("ScheduleMinute", minute);

			int ret_Update;
			try {
				ret_Update = db.update("ScheduleTable", values, "No = '1'", null);
			} finally {
				db.close();
			}
			if (ret_Update == -1){
				Toast.makeText(this, "Update失敗", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Update成功", Toast.LENGTH_SHORT).show();
			}

			finish();

			break;

		case R.id.scheduleDay_SetBtn:
			//日付情報の初期設定
			Calendar calendar_Day = Calendar.getInstance();
			year = calendar_Day.get(Calendar.YEAR);
			monthOfYear = calendar_Day.get(Calendar.MONTH);
			dayOfMonth = calendar_Day.get(Calendar.DAY_OF_MONTH);

			DatePickerDialog datePickerDialog;

			//日付設定時のリスナ作成
			DatePickerDialog.OnDateSetListener DateSetListener = new DatePickerDialog.OnDateSetListener(){
				public void onDateSet(android.widget.DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
					_YearText.setText(Integer.toString(year) + "年");
					_MonthText.setText(Integer.toString(monthOfYear + 1) + "月");
					_DayText.setText(Integer.toString(dayOfMonth) + "日");
				}
			};

			//日付設定ダイアログの作成
			datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Black,
					DateSetListener, year, monthOfYear, dayOfMonth);
			//日付設定ダイアログの表示
			datePickerDialog.show();

			break;

		case R.id.scheduleTime_SetBtn:
			//時刻情報の初期設定
			Calendar calendar_Time 	= Calendar.getInstance();

			hourOfDay = calendar_Time.get(Calendar.HOUR_OF_DAY);
			minute    = calendar_Time.get(Calendar.MINUTE);

			TimePickerDialog timePickerDialog;

			//時刻設定時のリスナ登録
			TimePickerDialog.OnTimeSetListener TimeSetListener = new TimePickerDialog.OnTimeSetListener() {
				public void onTimeSet(TimePicker view, int hour, int minute) {
					if (hour > 12) {
						_AMPMText.setText("PM");
						_HourText.setText(Integer.toString(hour - 12) + "時");
					}else {
						_AMPMText.setText("AM");
						_HourText.setText(Integer.toString(hour) + "時");
					}
					_MinuteText.setText(Integer.toString(minute) + "分");
				}
			};

			//時刻設定ダイアログの作成
			timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Black,TimeSetListener,
					hourOfDay, minute, false);

			//時刻設定ダイアログの表示
			timePickerDialog.show();

			break;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();

		finish();

		return true;
	}
}
