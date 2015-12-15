package com.example.testdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	private static final String TAG = MainActivity.class.getName();
	private Button velocityTrackerTest;
	private Button gestureDetectortest;
	private Button scoller;
	private Button commonMethod;
	private Context mContext=MainActivity.this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initUI();
	}

	private void initUI() {
		velocityTrackerTest= (Button) findViewById(R.id.velocityTrackerTest);
		gestureDetectortest= (Button) findViewById(R.id.gestureDetectortest);
		commonMethod= (Button) findViewById(R.id.commonMethod);
		scoller= (Button) findViewById(R.id.scoller);
		velocityTrackerTest.setOnClickListener(this);
		gestureDetectortest.setOnClickListener(this);
		scoller.setOnClickListener(this);
		commonMethod.setOnClickListener(this);
		
	}


	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.velocityTrackerTest:
			VelocityTrackerTest.actionStart(mContext);
			Log.d(TAG, "onClick R.id.velocityTrackerTest:");
			break;
		case R.id.gestureDetectortest:
			GestureDetectorTest.actionStart(mContext);
			Log.d(TAG, "onClick R.id.gestureDetectortest:");
			break;
		case R.id.scoller:
			ScrollerTest.actionStart(mContext);
			Log.d(TAG, "onClick R.id.scoller:");
			break;
		case R.id.commonMethod:
			CommonCodeActivity.actionStart(mContext);
			Log.d(TAG, "onClick R.id.scoller:");
			break;

		default:
			break;
		}
	}
}
