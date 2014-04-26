package com.example.widgettest.activity;

import com.example.widgettest.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 設定画面のActivity
 */
public class SetActivity extends Activity {

	EditText _text_edit;
	Button _save_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set);


	}

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem actionItem = menu.add("MainActivity Button");

		actionItem.setIcon(android.R.drawable.ic_menu_today);

		MenuItemCompat.setShowAsAction(actionItem, MenuItem.SHOW_AS_ACTION_ALWAYS);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();

		finish();

		return true;
	}
}
