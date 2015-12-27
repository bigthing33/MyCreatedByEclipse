package com.example.testdemo;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;

import com.example.testdemo.base.BaseActivity;
import com.example.testdemo.utils.MyConstants;

public class Ntificarion_RemoteActivity extends BaseActivity {
	

	private Button send_notification_simlulate;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_remote);
		initUI();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void initUI() {
		send_notification_simlulate = (Button) findViewById(R.id.send_notification_simlulate);
		send_notification_simlulate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent1=new Intent(Ntificarion_RemoteActivity.this,Ntificarion_RemoteActivity.class);
			      RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_simulated_notification);
			        remoteViews.setTextViewText(R.id.msg, "msg from process:" + Process.myPid());
			        remoteViews.setImageViewResource(R.id.icon, R.drawable.icon1);
			        PendingIntent.getActivity(Ntificarion_RemoteActivity.this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
			        PendingIntent pendingIntent = PendingIntent.getActivity(Ntificarion_RemoteActivity.this,0,intent1 , PendingIntent.FLAG_UPDATE_CURRENT);
			        PendingIntent openActivity2PendingIntent = PendingIntent.getActivity(
			        		Ntificarion_RemoteActivity.this, 0, new Intent(Ntificarion_RemoteActivity.this, Ntificarion_RemoteActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
			        remoteViews.setOnClickPendingIntent(R.id.item_holder, pendingIntent);
			        remoteViews.setOnClickPendingIntent(R.id.open_activity2, openActivity2PendingIntent);
			        Intent intent = new Intent(MyConstants.REMOTE_ACTION);
			        intent.putExtra(MyConstants.EXTRA_REMOTE_VIEWS, remoteViews);
			        sendBroadcast(intent);
				
			}
		});

	}

}
