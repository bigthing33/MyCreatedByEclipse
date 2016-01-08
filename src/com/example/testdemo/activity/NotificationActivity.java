package com.example.testdemo.activity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import com.example.testdemo.R;
import com.example.testdemo.R.drawable;
import com.example.testdemo.R.id;
import com.example.testdemo.R.layout;
import com.example.testdemo.base.BaseActivity;
import com.example.testdemo.utils.MyConstants;
import com.example.testdemo.widget.MyAppWidgetProvider;

@SuppressLint("NewApi")
public class NotificationActivity extends BaseActivity implements
		OnClickListener {
    private LinearLayout remote_views_content;
	private Button notificacion_system;
	private Button notificacion_custom;
	private Button notificacion_remote;
	private Button notificacion_remote_simulage;
	private int sId = 1;
	private BroadcastReceiver mRemoteViewsReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			RemoteViews remoteViews = intent
					.getParcelableExtra(MyConstants.EXTRA_REMOTE_VIEWS);
			if (remoteViews != null) {
				updateUI(remoteViews);
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_notification);
		super.onCreate(savedInstanceState);
		initUI();
	}

	@Override
	public void onDestroy() {
		 unregisterReceiver(mRemoteViewsReceiver);
		super.onDestroy();
	}

	@Override
	public void initUI() {
		notificacion_system = (Button) findViewById(R.id.notificacion_system);
		notificacion_system.setOnClickListener(this);
		notificacion_custom = (Button) findViewById(R.id.notificacion_custom);
		notificacion_custom.setOnClickListener(this);
		notificacion_remote = (Button) findViewById(R.id.notificacion_remote);
		notificacion_remote.setOnClickListener(this);
		notificacion_remote_simulage = (Button) findViewById(R.id.notificacion_remote_simulage);
		notificacion_remote_simulage.setOnClickListener(this);
		remote_views_content = (LinearLayout) findViewById(R.id.remote_views_content);
		IntentFilter filter = new IntentFilter(MyConstants.REMOTE_ACTION);
        registerReceiver(mRemoteViewsReceiver, filter);

	}

	public static void actionStart(Context context) {
		Intent intent = new Intent(context, NotificationActivity.class);
		context.startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		if (v == notificacion_system) {
			sId++;
			Notification.Builder mBuilder = new Notification.Builder(this);
			Intent intent = new Intent(this, NotificationActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
					intent, PendingIntent.FLAG_CANCEL_CURRENT);
			mBuilder.setTicker("hello world")
					.setSmallIcon(R.drawable.ic_launcher)
					.setWhen(System.currentTimeMillis()).setAutoCancel(true)
					.setContentIntent(pendingIntent)
					.setContentText("you are my sunshing")
					.setContentTitle("hehe").setOngoing(true);
			NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			manager.notify(sId, mBuilder.build());
			// Notification notification = new Notification();
			// notification.icon = R.drawable.ic_launcher;
			// notification.tickerText = "hello world";
			// notification.when = System.currentTimeMillis();
			// notification.flags = Notification.FLAG_AUTO_CANCEL;
			// Intent intent = new Intent(this, NotificationActivity.class);
			// PendingIntent pendingIntent = PendingIntent.getActivity(this,
			// 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			// notification.setLatestEventInfo(this, "chapter_5",
			// "this is notification.", pendingIntent);
			// NotificationManager manager =
			// (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
			// manager.notify(sId, notification);
		} else if (v == notificacion_custom) {
			sId++;
			Notification notification = new Notification();
			notification.icon = R.drawable.ic_launcher;
			notification.tickerText = "hello world";
			notification.when = System.currentTimeMillis();
			notification.flags = Notification.FLAG_AUTO_CANCEL;
			Intent intent = new Intent(this, NotificationActivity.class);
			intent.putExtra("sid", "" + sId);
			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
					intent, PendingIntent.FLAG_UPDATE_CURRENT);
			System.out.println(pendingIntent);
			RemoteViews remoteViews = new RemoteViews(getPackageName(),
					R.layout.layout_notification);
			remoteViews.setTextViewText(R.id.msg, "chapter_5: " + sId);
			remoteViews.setImageViewResource(R.id.icon, R.drawable.ic_launcher);
			PendingIntent openActivity2PendingIntent = PendingIntent
					.getActivity(this, 0, new Intent(this,
							NotificationActivity.class),
							PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(R.id.open_activity2,
					openActivity2PendingIntent);
			notification.contentView = remoteViews;
			notification.contentIntent = pendingIntent;
			NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			manager.notify(sId, notification);
		} else if (v == notificacion_remote) {
			// sendBroadcast(new Intent(TestAppWidget.FRESH));
			sendBroadcast(new Intent(MyAppWidgetProvider.CLICK_ACTION));
		} else if (v == notificacion_remote_simulage) {
			  Intent intent = new Intent(this, Ntificarion_RemoteActivity.class);
	            startActivity(intent);

		}
	}

	private void updateUI(RemoteViews remoteViews) {
		// View view = remoteViews.apply(this, mRemoteViewsContent);
		int layoutId = getResources().getIdentifier(
				"layout_simulated_notification", "layout", getPackageName());
		View view = getLayoutInflater().inflate(layoutId, remote_views_content,
				false);
		remoteViews.reapply(this, view);
		remote_views_content.addView(view);
	}

}
