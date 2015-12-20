package com.example.testdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.testdemo.utils.LogUtil;

public class MainActivity extends Activity implements OnClickListener {
	private static final String TAG = MainActivity.class.getName();
	private Button commonMethod;
	private Button velocityTrackerTest;
	private Button gestureDetectortest;
	private Button scoller;
	private Button inerIntercept;
	private Button sendHttpRequest;
	private Button customWidget;
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
		sendHttpRequest= (Button) findViewById(R.id.sendHttpRequest);
		customWidget= (Button) findViewById(R.id.customWidget);
		sendHttpRequest.setOnClickListener(this);
		inerIntercept= (Button) findViewById(R.id.inerIntercept);
		inerIntercept.setOnClickListener(this);
		customWidget.setOnClickListener(this);
		velocityTrackerTest.setOnClickListener(this);
		gestureDetectortest.setOnClickListener(this);
		scoller.setOnClickListener(this);
		commonMethod.setOnClickListener(this);
		
	}


	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.velocityTrackerTest:
			VelocityTrackerTestAcitvity.actionStart(mContext);
			LogUtil.d(TAG, "onClick R.id.velocityTrackerTest:");
			break;
		case R.id.gestureDetectortest:
			GestureDetectorTestAcitvity.actionStart(mContext);
			LogUtil.d(TAG, "onClick R.id.gestureDetectortest:");
			break;
		case R.id.scoller:
			ScollerAcitvity.actionStart(mContext);
			LogUtil.d(TAG, "onClick R.id.scoller:");
			break;
		case R.id.commonMethod:
			CommonCodeActivity.actionStart(mContext);
			LogUtil.d(TAG, "onClick R.id.scoller:");
			break;
		case R.id.sendHttpRequest:
			SendHttpRequestActivity.actionStart(mContext);
			LogUtil.d(TAG, "onClick R.id.scoller:");
			break;
		case R.id.customWidget:
			CustomWidgetAcitvity.actionStart(mContext);
			LogUtil.d(TAG, "onClick R.id.scoller:");
			break;
		case R.id.inerIntercept:
			CustomWidgetAcitvity.actionStart(mContext);
			LogUtil.d(TAG, "onClick R.id.scoller:");
			break;

		default:
			break;
		}
	}
}
