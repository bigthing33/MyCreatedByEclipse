package com.example.testdemo;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testdemo.aidl.ICompare;
import com.example.testdemo.services.AIDLCompareService;
import com.example.testdemo.services.CompareService;
import com.example.testdemo.services.CompareService.MyBinder;

public class CompareActivity extends Activity implements OnClickListener {

	private EditText integer1_et;
	private EditText integer2_et;
	private Button showCompareResult1_btn;
	private Button showCompareResult2_btn;
	private MyBinder myBinder;
	private ICompare myAislBinder;
	private MyServiceConnnection myServiceConnnection;
	private MyAIDLServiceConnnection myAIDLServiceConnnection ;
	private Intent myService;
	private Intent myServiceAidl;
	private boolean isBind;
	private boolean isBind2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compare);
		myService = new Intent(CompareActivity.this, CompareService.class);
		myServiceAidl = new Intent(CompareActivity.this, AIDLCompareService.class);
		myServiceConnnection=new MyServiceConnnection();
		myAIDLServiceConnnection=new MyAIDLServiceConnnection();
		// 绑定一个服务，以便调用服务内的方法
		isBind = getApplicationContext().bindService(myService,myServiceConnnection, Service.BIND_AUTO_CREATE);
		isBind2=getApplicationContext().bindService(myService,myAIDLServiceConnnection, Service.BIND_AUTO_CREATE);
		initView();
	}

	private void initView() {
		integer1_et = (EditText) findViewById(R.id.integer1_et);
		integer2_et = (EditText) findViewById(R.id.integer2_et);
		showCompareResult1_btn = (Button) findViewById(R.id.showCompareResult1_btn);
		showCompareResult1_btn.setOnClickListener(this);
		showCompareResult2_btn = (Button) findViewById(R.id.showCompareResult2_btn);
		showCompareResult2_btn.setOnClickListener(this);

	}

	@Override
	protected void onDestroy() {
		// 退出Activity时要解除绑定
		if (isBind && myServiceConnnection != null) {
			getApplicationContext().unbindService(myServiceConnnection);

		}
		super.onDestroy();
	}

	class MyServiceConnnection implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			myBinder = (MyBinder) service;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}
	}
	class MyAIDLServiceConnnection implements ServiceConnection {
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			myAislBinder = ICompare.Stub.asInterface(service) ;
		}
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.showCompareResult1_btn:
			//吐司弹出比较结果，结果是通过myBinder调用服务里的方法获得的
			Toast.makeText(
					CompareActivity.this,
					"较大的数是"+ myBinder.compare(integer1_et.getText().toString(),
							integer2_et.getText().toString()), Toast.LENGTH_LONG)
							.show();
			break;
		case R.id.showCompareResult2_btn:
			//吐司弹出比较结果，结果是通过myBinder调用服务里的方法获得的
			try {
				Toast.makeText(
						CompareActivity.this,
						"较大的数是"+ myAislBinder.compare2(integer1_et.getText().toString(),
								integer2_et.getText().toString()), Toast.LENGTH_LONG)
								.show();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}

	}
	public static void actionStart(Context context) {
		Intent intent = new Intent(context, CompareActivity.class);
		context.startActivity(intent);
	}
}
