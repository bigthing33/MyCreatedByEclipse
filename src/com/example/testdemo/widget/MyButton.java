package com.example.testdemo.widget;

import com.example.testdemo.utils.LogUtil;
import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;

public class MyButton extends TextView {
	private static final String TAG = "MyButton";
	private int mScaledTouchSlop;
	// 分别记录上次滑动的坐标
	private int mLastX = 0;
	private int mLastY = 0;

	public MyButton(Context context) {
		this(context, null);
	}

	public MyButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MyButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		mScaledTouchSlop = ViewConfiguration.get(getContext())
				.getScaledTouchSlop();
		Log.d(TAG, "sts:" + mScaledTouchSlop);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//这是以屏幕的左上角做原点获得的坐标值，getX我猜测是以父容器的左上角做原点的坐标值，当然我自己没有验证，懒~~。
		int x = (int) event.getRawX();
		int y = (int) event.getRawY();
		LogUtil.d(TAG, x+"getRawX");
		LogUtil.d(TAG, y+"getRawY");
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			int deltaX = x - mLastX;//这次的坐标和上一次的坐标的相对距离
			int deltaY = y - mLastY;
			Log.d(TAG, "move, deltaX:" + deltaX + " deltaY:" + deltaY);
			int translationX = (int) ViewHelper.getTranslationX(this) + deltaX;//当前已经产生的位移，加上这次需要移动的位移
			int translationY = (int) ViewHelper.getTranslationY(this) + deltaY;//当前已经产生的位移，加上这次需要移动的位移
			//经过下面两个函数后，该控件的关于屏幕原点的坐标已经发生了变化，即getRawX()和getRawY()的值就不一样了
			ViewHelper.setTranslationX(this, translationX);
			ViewHelper.setTranslationY(this, translationY);
			break;
		}
		case MotionEvent.ACTION_UP: {
			break;
		}
		default:
			break;
		}

		mLastX = x;
		mLastY = y;
		return true;
	}

}
