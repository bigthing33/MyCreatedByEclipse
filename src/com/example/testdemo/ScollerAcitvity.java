package com.example.testdemo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.Toast;

import com.example.testdemo.base.BaseActivity;
import com.example.testdemo.utils.LogUtil;

public class ScollerAcitvity extends BaseActivity implements OnClickListener, OnLongClickListener {
	private static final String TAG = ScollerAcitvity.class.getName();

	private static final int MESSAGE_SCROLL_TO = 1;
	private static final int FRAME_COUNT = 30;
	private static final int DELAYED_TIME = 33;
	private int mCount = 0;
	private Button scrollerBtn1;
	private Button scrollerBtn2;
	private Button scrollerBtn3;
	private Button scrollerBtn4;
	private View scrollerBtn5;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_SCROLL_TO: {
				mCount++;
				if (mCount <= FRAME_COUNT) {
					float fraction = mCount / (float) FRAME_COUNT;
					int scrollX = (int) (fraction * 100);
					scrollerBtn4.scrollTo(scrollX, 0);
					mHandler.sendEmptyMessageDelayed(MESSAGE_SCROLL_TO,
							DELAYED_TIME);
				}
				break;
			}

			default:
				break;
			}
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scollertest);
		initUI();

	}

	public static void actionStart(Context context) {
		Intent intent = new Intent(context, ScollerAcitvity.class);
		context.startActivity(intent);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			break;
		}
		return true;
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.scrollerBtn1:
			showToast("scrollerBtn1");
			Log.d(TAG, "button1.left=" + scrollerBtn1.getLeft());
			Log.d(TAG, "button1.getTop=" + scrollerBtn1.getTop());
			Log.d(TAG, "button1.getRight=" + scrollerBtn1.getRight());
			Log.d(TAG, "button1.getBottom=" + scrollerBtn1.getBottom());
			Log.d(TAG, "button1.x=" + scrollerBtn1.getX());
			Log.d(TAG, "button1.y=" + scrollerBtn1.getY());
			/**
			 * 动画滑动
			 */
			ObjectAnimator.ofFloat(scrollerBtn1, "translationX", 0, 100)
					.setDuration(1000).start();

			break;
		case R.id.scrollerBtn2:
			/**
			 * setLayoutParams滑动，改变布局参数
			 */
			MarginLayoutParams params = (MarginLayoutParams) scrollerBtn2
					.getLayoutParams();
			// params.width += 100;
			params.leftMargin += 100;
			scrollerBtn2.requestLayout();
			scrollerBtn2.setLayoutParams(params);
			break;
		case R.id.scrollerBtn3:
			/**
			 * 使用动画将view的内容向左滑动100。
			 */
			final int startX = 0;
			final int deltaX = 100;
			// ValueAnimator 在0和1之间计算动画的值，持续时间是1000ms.
			ValueAnimator animator = ValueAnimator.ofInt(0, 1)
					.setDuration(1000);
			animator.addUpdateListener(new AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator animator) {
					// 我估计这个fraction就是0-1在1秒中持续变动的值
					float fraction = animator.getAnimatedFraction();
					LogUtil.d("fraction", fraction + "");
					// 将内容根据参数数值移动了
					scrollerBtn3
							.scrollTo(startX + (int) (deltaX * fraction), 0);
				}
			});
			animator.start();
			break;
		case R.id.scrollerBtn4:
			/**
			 * 使用延时策略将view的内容向左滑动100。
			 */
			mHandler.sendEmptyMessageDelayed(MESSAGE_SCROLL_TO, DELAYED_TIME);
			break;

		default:
			break;
		}

	}

	@Override
	public void initUI() {
		scrollerBtn1 = (Button) findViewById(R.id.scrollerBtn1);
		scrollerBtn2 = (Button) findViewById(R.id.scrollerBtn2);
		scrollerBtn3 = (Button) findViewById(R.id.scrollerBtn3);
		scrollerBtn4 = (Button) findViewById(R.id.scrollerBtn4);
		scrollerBtn5 =  findViewById(R.id.scrollerBtn5);
		scrollerBtn1.setOnClickListener(this);
		scrollerBtn4.setOnLongClickListener(this);
		scrollerBtn2.setOnClickListener(this);
		scrollerBtn3.setOnClickListener(this);
		scrollerBtn4.setOnClickListener(this);

	}

	@Override
	public boolean onLongClick(View arg0) {
		 Toast.makeText(this, "long click", Toast.LENGTH_SHORT).show();
		return true;
	}

}
