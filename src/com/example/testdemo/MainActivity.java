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
	private Button notification;
	private Button compareService_btn;
	private Button drawableDemo;
	private Button animationDemo;
	private Context mContext = MainActivity.this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initUI();
	}

	private void initUI() {
		velocityTrackerTest = (Button) findViewById(R.id.velocityTrackerTest);
		gestureDetectortest = (Button) findViewById(R.id.gestureDetectortest);
		commonMethod = (Button) findViewById(R.id.commonMethod);
		scoller = (Button) findViewById(R.id.scoller);
		sendHttpRequest = (Button) findViewById(R.id.sendHttpRequest);
		customWidget = (Button) findViewById(R.id.customWidget);
		inerIntercept = (Button) findViewById(R.id.inerIntercept);
		notification = (Button) findViewById(R.id.notification);
		compareService_btn = (Button) findViewById(R.id.compareService_btn);
		drawableDemo = (Button) findViewById(R.id.drawableDemo);
		animationDemo = (Button) findViewById(R.id.animationDemo);
		animationDemo.setOnClickListener(this);
		drawableDemo.setOnClickListener(this);
		compareService_btn.setOnClickListener(this);
		notification.setOnClickListener(this);
		inerIntercept.setOnClickListener(this);
		customWidget.setOnClickListener(this);
		sendHttpRequest.setOnClickListener(this);
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
			LogUtil.d(TAG, "onClick R.id.commonMethod:");
			break;
		case R.id.sendHttpRequest:
			SendHttpRequestActivity.actionStart(mContext);
			LogUtil.d(TAG, "onClick R.id.sendHttpRequest:");
			break;
		case R.id.customWidget:
			CustomWidgetAcitvity.actionStart(mContext);
			LogUtil.d(TAG, "onClick R.id.customWidget:");
			break;
		case R.id.inerIntercept:
			CustomWidgetAcitvity.actionStart(mContext);
			LogUtil.d(TAG, "onClick R.id.inerIntercept:");
			break;
		case R.id.notification:
			NotificationActivity.actionStart(mContext);
			LogUtil.d(TAG, "onClick R.id.notification:");
			break;
		case R.id.compareService_btn:
			CompareActivity.actionStart(mContext);
			LogUtil.d(TAG, "onClick R.id.compareService_btn:");
			break;
		case R.id.drawableDemo:
			DrawableDemoActivity.actionStart(mContext);
			LogUtil.d(TAG, "onClick R.id.compareService_btn:");
			break;
		case R.id.animationDemo:
			AnimationActivity.actionStart(mContext);
			LogUtil.d(TAG, "onClick R.id.compareService_btn:");
			break;

		default:
			break;
		}
	}
}
