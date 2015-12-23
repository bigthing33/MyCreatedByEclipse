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

//	@Override
//	public void onClick(View view) {
//
//		int sId = 1;
//		switch (view.getId()) {
//		case R.id.notificacion_system:
//			/*
//			 * Notification notification=new Notification();
//			 * notification.icon=R.drawable.ic_launcher; //设置弹出时提醒的text
//			 * notification.tickerText="hello world";
//			 * notification.when=System.currentTimeMillis();
//			 * notification.flags=Notification.FLAG_AUTO_CANCEL;
//			 * notification.setLatestEventInfo(this, "cyq_notification",
//			 * "this is a systemNotification", pendingIntent);
//			 * NotificationManager manager=(NotificationManager)
//			 * getSystemService(Context.NOTIFICATION_SERVICE);
//			 * manager.notify(1,notification);
//			 */
//			// 一个对通知栏解释的很详细的博客
//			// http://blog.csdn.net/vipzjyno1/article/details/25248021
//			sId ++;
//			Intent intent = new Intent(this, NotificationActivity.class);
//			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
//					intent, PendingIntent.FLAG_CANCEL_CURRENT);
//			NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//			Notification.Builder mBuilder = new Notification.Builder(this);
//			mBuilder.setContentTitle("测试标题")// 设置通知栏标题
//
//					.setContentText("测试内容") // 设置通知栏显示内容
//					.setContentIntent(pendingIntent) // 设置通知栏点击意图
//					// .setNumber(number) //设置通知集合的数量
//					.setTicker("测试通知来啦") // 通知首次出现在通知栏，带上升动画效果的
//					.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
//					.setPriority(Notification.PRIORITY_DEFAULT) // 设置该通知优先级
//					// .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
//					.setOngoing(false)// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
//					.setDefaults(Notification.DEFAULT_VIBRATE)// 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
//					// Notification.DEFAULT_ALL Notification.DEFAULT_SOUND 添加声音
//					// // requires VIBRATE permission
//					.setSmallIcon(R.drawable.ic_launcher);// 设置通知小ICON
//			mNotificationManager.notify(sId, mBuilder.build());
//			break;
//		case R.id.notificacion_custom:
////			RemoteViews mRemoteView = new RemoteViews(getPackageName(),
////					R.layout.activity_sample);
////			  Notification notification=new Notification();
////			  notification.icon=R.drawable.ic_launcher; //设置弹出时提醒的text
////			  notification.tickerText="hello world";
////			  notification.when=System.currentTimeMillis();
////			  notification.flags=Notification.FLAG_AUTO_CANCEL;
////			  notification.contentView=mRemoteView;
////			  notification.contentIntent=pendingIntent;
////			  mNotificationManager.notify(notifyId, mBuilder.build());
//            sId ++;
//            Notification notification = new Notification();
////            notification.icon = R.drawable.ic_launcher;
//            notification.tickerText = "hello world";
//            notification.when = System.currentTimeMillis();
//            notification.flags = Notification.FLAG_AUTO_CANCEL;
//            Intent intent2 = new Intent(this, NotificationActivity.class);
//            intent2.putExtra("sid", "" + sId);
//            PendingIntent pendingIntent2 = PendingIntent.getActivity(this,
//                    0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
//            System.out.println(pendingIntent2);
//            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.activity_sample);
////            remoteViews.setTextViewText(R.id.msg, "chapter_5: " + sId);
////            remoteViews.setImageViewResource(R.id.icon, R.drawable.icon1);
////            PendingIntent openActivity2PendingIntent = PendingIntent.getActivity(this,
////                    0, new Intent(this, DemoActivity_2.class), PendingIntent.FLAG_UPDATE_CURRENT);
////            remoteViews.setOnClickPendingIntent(R.id.open_activity2, openActivity2PendingIntent);
//            notification.contentView = remoteViews;
//            notification.contentIntent = pendingIntent2;
//            NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//            manager.notify(sId, notification);
//	
//
//			break;
//		case R.id.notificacion_remote:
//
//			break;
//
//		default:
//			break;
//		}
//
//	}

	public static void actionStart(Context context) {
		Intent intent = new Intent(context, NotificationActivity.class);
		context.startActivity(intent);
	}

@Override
public void onClick(View v) {
    if (v == notificacion_system) {
        sId ++;
        Notification notification = new Notification();
        notification.icon = R.drawable.ic_launcher;
        notification.tickerText = "hello world";
        notification.when = System.currentTimeMillis();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setLatestEventInfo(this, "chapter_5", "this is notification.", pendingIntent);
        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(sId, notification);
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
    }
}

}
