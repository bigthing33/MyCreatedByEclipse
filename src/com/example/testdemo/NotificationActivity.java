package com.example.testdemo;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;

import com.example.testdemo.base.BaseActivity;
import com.example.testdemo.widget.MyAppWidgetProvider;
import com.example.testdemo.widget.TestAppWidget;

@SuppressLint("NewApi")
public class NotificationActivity extends BaseActivity implements
		OnClickListener {
	private Button notificacion_system;
	private Button notificacion_custom;
	private Button notificacion_remote;
	private int sId=1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_notification);
		super.onCreate(savedInstanceState);
		initUI();
	}

	@Override
	public void onDestroy() {
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

	}


	public static void actionStart(Context context) {
		Intent intent = new Intent(context, NotificationActivity.class);
		context.startActivity(intent);
	}

@Override
public void onClick(View v) {
    if (v == notificacion_system) {
        sId ++;
        Notification.Builder mBuilder = new Notification.Builder(this);
        Intent intent = new Intent(this, NotificationActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				intent, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setTicker("hello world").setSmallIcon(R.drawable.ic_launcher).setWhen(System.currentTimeMillis()).setAutoCancel(true).setContentIntent(pendingIntent)
        .setContentText("you are my sunshing").setContentTitle("hehe").setOngoing(true);
        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(sId, mBuilder.build());
//        Notification notification = new Notification();
//        notification.icon = R.drawable.ic_launcher;
//        notification.tickerText = "hello world";
//        notification.when = System.currentTimeMillis();
//        notification.flags = Notification.FLAG_AUTO_CANCEL;
//        Intent intent = new Intent(this, NotificationActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,
//                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        notification.setLatestEventInfo(this, "chapter_5", "this is notification.", pendingIntent);
//        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(sId, notification);
    } else if (v == notificacion_custom) {
        sId ++;
        Notification notification = new Notification();
        notification.icon = R.drawable.ic_launcher;
        notification.tickerText = "hello world";
        notification.when = System.currentTimeMillis();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.putExtra("sid", "" + sId);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        System.out.println(pendingIntent);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
        remoteViews.setTextViewText(R.id.msg, "chapter_5: " + sId);
        remoteViews.setImageViewResource(R.id.icon, R.drawable.ic_launcher);
        PendingIntent openActivity2PendingIntent = PendingIntent.getActivity(this,
                0, new Intent(this, NotificationActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.open_activity2, openActivity2PendingIntent);
        notification.contentView = remoteViews;
        notification.contentIntent = pendingIntent;
        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(sId, notification);
    }else if(v==notificacion_remote){
//		sendBroadcast(new Intent(TestAppWidget.FRESH));
		sendBroadcast(new Intent(MyAppWidgetProvider.CLICK_ACTION));
    }
}

}
