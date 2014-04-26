package com.example.widgettest.activity;

import com.example.widgettest.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * アプリのMainActivity
 */
public class MainActivity extends Activity implements OnClickListener {

	EditText _text_edit;
	Button _save_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// AppWidget IDを取得
		Uri uri = getIntent().getData();
		int id = (int)ContentUris.parseId(uri);

		_text_edit = (EditText)findViewById(R.id.editText1);
		//_text_edit.setText(String.format("WidgetID id %d", id));
		_save_btn = (Button)findViewById(R.id.activityBtn);

		_save_btn.setOnClickListener(this);
	}

	/*
	 * 入力された値をwidgetにBrodcastするメソッド
	 */
	@Override
	public void onClick(View arg0) {
		Intent widgetUpdate = new Intent("WIDGET_CLICK_ACTION");
		Bundle bundle = new Bundle();
		bundle.putString("text", _text_edit.getText().toString());
		widgetUpdate.putExtras(bundle);
		// Widgetに渡す値をBroadcastする
		sendBroadcast(widgetUpdate);

		finish();
	}

	/*
	 * ActionButtonを追加するメソッド
	 */
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

	/*
	 * ActionButton押下時の処理を行うメソッド
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();

		if(item.getTitle().equals("ScheduleActivity Button")) {
			Intent intent = new Intent(this, ScheduleActivity.class);
			startActivity(intent);
		} else if (item.getTitle().equals("SetActivity Button")) {
			Intent intent = new Intent(this, SetActivity.class);
			startActivity(intent);
		}

		return true;
	}
}
