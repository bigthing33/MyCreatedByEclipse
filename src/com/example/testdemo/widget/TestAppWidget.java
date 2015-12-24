package com.example.testdemo.widget;

import java.util.Calendar;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.testdemo.R;

public class TestAppWidget extends AppWidgetProvider {
	private static final String TAG = "TestAppWidget";
	public  static final String FRESH = "com.sinxiao.app.fresh";
	private Context mContext;
	private boolean run = true;

	// 定义一个广播接收器，当就收到的广播是ACTION_TIME_TICK，发送一个action是com.sinxiao.app.fresh的广播
	BroadcastReceiver mBroadcast = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if (action.equals(Intent.ACTION_TIME_TICK)) {

				mContext.sendBroadcast(new Intent(FRESH));

			}

		}

	};

	Thread myThread = new Thread() {

		public void run() {

			while (run) {

				try {

					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mContext.sendBroadcast(new Intent(FRESH));// 通知刷新Widget的Intent
			}
		};
	};

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,

	int[] appWidgetIds) {
		// 用来给Widget刷新界面显示
		Log.d(TAG, "onUpdate");
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		mContext = context;
		RemoteViews views = new RemoteViews(context.getPackageName(),
				R.layout.test_widget);
		Calendar cal = Calendar.getInstance();
		System.out.println(cal.getTime().toLocaleString());
		views.setTextViewText(R.id.txttim, cal.getTime().toLocaleString());
		appWidgetManager.updateAppWidget(appWidgetIds, views);
		myThread.start();
		/**
		 * 本类作为一个bracastReveiver能自己再，注册个监听器 （可以取消注释，看报什么错误）
		 */
		//
		context.registerReceiver(mBroadcast, new IntentFilter(
				Intent.ACTION_TIME_TICK));
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "onReceive");
		String action = intent.getAction();
		Log.d(TAG, "theaction is " + action);
		if (FRESH.equals(action)) {
			showTime(context);
		} else if (Intent.ACTION_TIME_TICK.equals(action)) {
			showTime(context);
		}
		super.onReceive(context, intent);
	}

	private void showTime(Context context) {
		RemoteViews views = new RemoteViews(context.getPackageName(),
				R.layout.test_widget);
		Calendar cal = Calendar.getInstance();
		views.setTextViewText(R.id.txttim, cal.getTime().toLocaleString());
		ComponentName thisWidget = new ComponentName(context,
				TestAppWidget.class);
		AppWidgetManager.getInstance(context).updateAppWidget(thisWidget, views);
	}

	public void onDisabled(Context context) {
		Log.d(TAG, "onDisabled");
		super.onDisabled(context);
		run = false;
	}
}
