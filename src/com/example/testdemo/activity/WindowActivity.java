package com.example.testdemo.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testdemo.R;
import com.example.testdemo.R.anim;
import com.example.testdemo.R.id;
import com.example.testdemo.R.layout;
import com.example.testdemo.base.BaseActivity;

public class WindowActivity extends BaseActivity implements OnClickListener,
		OnTouchListener {
	private Button viewWindow;
	private Button dialogWindow1;
	private Button toastWindow;
	/**
	 * 一般只有三个方法，add。update。remove。
	 */
	private WindowManager mWindowManager;
	private WindowManager.LayoutParams mLayoutParams;

	private Button mFloatingButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_windowdemo);
		initUI();
	}

	@Override
	public void onDestroy() {
		try {
			mWindowManager.removeView(mFloatingButton);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		super.onDestroy();
	}

	@Override
	public void initUI() {
		viewWindow = (Button) findViewById(R.id.ViewWindow);
		dialogWindow1 = (Button) findViewById(R.id.DialogWindow);
		toastWindow = (Button) findViewById(R.id.toastWindow);
		toastWindow.setOnClickListener(this);
		viewWindow.setOnClickListener(this);
		dialogWindow1.setOnClickListener(this);
		mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

	}

	public static void actionStart(Context context) {
		Intent intent = new Intent(context, WindowActivity.class);
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(R.anim.enter_anim,
				R.anim.exit_anim);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ViewWindow:
			showToast("button1 be clocked");
			mFloatingButton = new Button(this);
			mFloatingButton.setText("click me");
			// mLayoutParams=new LayoutParams(w, h, _type, _flags, _format)
			mLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT, 0, 0, PixelFormat.TRANSPARENT);
			/**
			 * FLAG_NOT_FOCUSABLE表示不需要获得焦点，也不需要接收各种输入事件，
			 * 此标记会同时启用FLAG_NOT_TOUCH_MODAL,最终事件会直接传递给下层的的具有焦点的window
			 * FLAG_NOT_TOUCH_MODAL表示此window区域内以外的事传给底层处理
			 * ，区域内的事则自己处理，一般都需要设置这个值，不然别的window就接收不到事件了
			 * FLAG_SHOW_WHEN_LOCKED表示可以显示在锁频界面上
			 */
			mLayoutParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
					| LayoutParams.FLAG_NOT_FOCUSABLE
					| LayoutParams.FLAG_SHOW_WHEN_LOCKED;
			/*
			 * 应用Window，子Window和系统Window三种类型，层级大的会覆盖在层级小的上面。
			 */
			mLayoutParams.type = LayoutParams.TYPE_SYSTEM_ERROR;
			mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
			mLayoutParams.x = 100;
			mLayoutParams.y = 300;
			mFloatingButton.setOnTouchListener(this);
			mWindowManager.addView(mFloatingButton, mLayoutParams);

			break;
		case R.id.DialogWindow:
			Dialog dialog = new Dialog(this.getApplicationContext());
			TextView textView = new TextView(this);
			textView.setText("this is toast!");
			dialog.setContentView(textView);
			dialog.setTitle("dialog");
			dialog.getWindow().setType(LayoutParams.TYPE_SYSTEM_ERROR);
			dialog.show();

			break;
		case R.id.toastWindow:
			
			break;
		default:
			break;
		}

	}
	
	public void show(){
		Toast.makeText(WindowActivity.this, "toast", Toast.LENGTH_LONG).show();
	}
	

	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
		int rawX = (int) event.getRawX();
		int rawY = (int) event.getRawY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			int x = (int) event.getX();
			int y = (int) event.getY();
			mLayoutParams.x = rawX;
			mLayoutParams.y = rawY;
			mWindowManager.updateViewLayout(mFloatingButton, mLayoutParams);
			break;
		}
		case MotionEvent.ACTION_UP: {
			break;
		}
		default:
			break;
		}

		return false;
	}

}
