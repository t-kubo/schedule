package com.example.widgettest.activity;

import com.example.widgettest.R;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;

/**
 * WidgetのActivity
 */
public class WidgetActivity extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		final Uri CONTENT_URI = Uri.parse("content://com.example.widgettest");

		super.onUpdate(context, appWidgetManager, appWidgetIds);

		for (int id : appWidgetIds) {
			// Serviceを起動する
			Intent serviceIntent = new Intent(context, TestService.class);
			context.startService(serviceIntent);

			// Widgetが押下した時に起動するActivityを指定する
			Intent activityIntent = new Intent(context, MainActivity.class);
			Uri uri = ContentUris.withAppendedId(CONTENT_URI, id);
			activityIntent.setData(uri);

			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);

			// Widgetのレイアウトを取得後、OnClickリスナーを設定する
			RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_main);
			remoteView.setOnClickPendingIntent(R.id.widgetBtn, pendingIntent);

			// widgetにremoteViewをセット
			appWidgetManager.updateAppWidget(id, remoteView);
		}
	}

	// widget終了時、Serviceを停止する処理
	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);

		Intent intent = new Intent(context, TestService.class);
		context.stopService(intent);
	}

	public static class TestService extends Service {
		private final String WIDGET_CLICK_ACTION = "WIDGET_CLICK_ACTION";

		/*
		 * サービスが開始された時の処理
		 */
		@Override
		public int onStartCommand(Intent intent, int flags, int startId) {
			super.onStartCommand(intent, flags, startId);

			// IntentFilterを設定して、Intentを取得できる状態にする
			IntentFilter filter = new IntentFilter();
			filter.addAction(WIDGET_CLICK_ACTION);
			registerReceiver(widget_Receiver, filter);

			return START_NOT_STICKY;
		}

		// 横画面切り替え時の処理
		@Override
		public void onConfigurationChanged(Configuration newConfig) {
			super.onConfigurationChanged(newConfig);

			Context context = getBaseContext();

			Intent activityIntent = new Intent(context, MainActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_main);
			views.setOnClickPendingIntent(R.id.widgetBtn, pendingIntent);
			ComponentName widget = new ComponentName(context, WidgetActivity.class);

			AppWidgetManager manager = AppWidgetManager.getInstance(TestService.this);
			manager.updateAppWidget(widget, views);
		}

		@Override
		public IBinder onBind(Intent intent) {
			return null;
		}

		@Override
		public void onDestroy() {
			super.onDestroy();
			unregisterReceiver(widget_Receiver);
		}
	}

	/*
	 * Broadcastされた情報を取得してWidgetを更新するメソッド
	 */
	private static BroadcastReceiver widget_Receiver = new BroadcastReceiver() {

		// BroadcastReceiver
		@Override
		public void onReceive(Context context, Intent intent) {
			// Intentのアクションを取得する
			String action = intent.getAction();
			Log.i(getClass().getSimpleName(), "Action Ren = " + action);

			if (action.equals("WIDGET_CLICK_ACTION")) {
				Bundle bundle = intent.getExtras();

				if (bundle == null) {
					Log.i(getClass().getSimpleName(), "Bundle = null");
				} else {
					String msg = bundle.getString("text");
					setTextView(context, msg);
				}
			}
		}

		void setTextView(Context context, String msg_st) {
			RemoteViews remoteViews;

			// 更新するremoteViewを生成する
			remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_main);
			remoteViews.setTextViewText(R.id.TextView1, msg_st);
			remoteViews.setTextColor(R.id.TextView1, Color.rgb(128, 0, 0));

			// AppWidgetManagerを取得して、Widgetを更新する
			ComponentName thiswWidget = new ComponentName(context, WidgetActivity.class);
			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			manager.updateAppWidget(thiswWidget, remoteViews);
		}
	};
}
