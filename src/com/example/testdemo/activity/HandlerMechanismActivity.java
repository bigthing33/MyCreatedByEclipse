package com.example.testdemo.activity;

import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.testdemo.R;
import com.example.testdemo.R.anim;
import com.example.testdemo.R.id;
import com.example.testdemo.R.layout;
import com.example.testdemo.base.BaseActivity;
import com.example.testdemo.utils.LogUtil;

public class HandlerMechanismActivity extends BaseActivity {
	private Button sample_btn;
	private static final String TAG = HandlerMechanismActivity.class
			.getSimpleName();
	private ThreadLocal<Boolean> mThreadLocal = new ThreadLocal<Boolean>();
	MyHandlerThread handlerThread;
	private Handler handler;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			super.handleMessage(msg);

			switch (msg.what) {
			case 0:
			
				break;
			case 1:

				break;

			default:
				break;
			}
		}

	};
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sample);
		sample_btn = (Button) findViewById(R.id.sample_btn);
		sample_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//点击按钮后来开启线程  
		        handler.sendEmptyMessage(1);  
			}
		});
		handlerThread = new MyHandlerThread("myHanler");
		handlerThread.start();
		// 注意：
		// 这里必须用到handler的这个构造器，因为需要把callback传进去，从而使自己的HandlerThread的handlerMessage来替换掉Handler原生的handlerThread
		handler = new Handler(handlerThread.getLooper(), handlerThread);  
		mThreadLocal.set(true);
		// 打印UI线程的名称
		LogUtil.e(TAG, "onCreate  CurrentThread = "
				+ Thread.currentThread().getName());
		LogUtil.d(TAG, "thread_Main mThreadLocal" + mThreadLocal.get());
		Thread thread1 = new Thread("Thread1") {

			@Override
			public void run() {
				mThreadLocal.set(false);
				LogUtil.d(TAG,
						"thread_Thread1 mThreadLocal" + mThreadLocal.get());
				Looper.prepare();// 为thread1创建一个Looper
				Looper.loop();// 开启消息循环

			}

		};
		thread1.start();
		Thread thread2 = new Thread("Thread1") {

			@Override
			public void run() {
				LogUtil.d(TAG,
						"thread_Thread2 mThreadLocal" + mThreadLocal.get());

			}

		};
		thread2.start();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		LogUtil.d(TAG, "thread_Main mThreadLocal" + mThreadLocal.get());
	}

	@Override
	public void initUI() {

	}

	public static void actionStart(Context context) {
		Intent intent = new Intent(context, HandlerMechanismActivity.class);
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(R.anim.enter_anim,
				R.anim.exit_anim);

	}

	class DowloadFileTask extends AsyncTask<URL, Integer, Long> {

		@Override
		protected Long doInBackground(URL... urls) {
			int count = urls.length;
			long totalSize = 0;
			for (int i = 0; i < count; i++) {

			}
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Long result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

	}

	private class MyHandlerThread extends HandlerThread implements Callback {

		public MyHandlerThread(String name) {
			super(name);
		}

		@Override
		public boolean handleMessage(Message msg) {
			// 打印线程的名称
			LogUtil.e(TAG, " handleMessage CurrentThread = "
					+ Thread.currentThread().getName());
			return true;
		}

	}

}
