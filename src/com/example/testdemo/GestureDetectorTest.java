package com.example.testdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.TextView;

public class GestureDetectorTest extends Activity {
	private TextView gesture;
	private GestureDetector mGestureDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gesturedetectortest);
		gesture = (TextView) findViewById(R.id.gesture);
		gesture.setText("the x velocity is ");
		OnGestureListener listener=new OnGestureListener() {
			
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				gesture.append("onSingleTapUp");
				return false;
			}
			
			@Override
			public void onShowPress(MotionEvent e) {
				gesture.append("onShowPress");
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
					float distanceY) {
				// TODO Auto-generated method stub
				gesture.append("onScroll");
				return false;
			}
			
			@Override
			public void onLongPress(MotionEvent e) {
				gesture.append("onLongPress");
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
					float velocityY) {
				gesture.append("onFling");
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean onDown(MotionEvent e) {
				gesture.append("onDown");
				// TODO Auto-generated method stub
				return false;
			}
		};
		mGestureDetector=new GestureDetector(listener);
		//解决长按后无法拖动的现象
		mGestureDetector.setIsLongpressEnabled(false);
	}

	public static void actionStart(Context context) {
		Intent intent = new Intent(context, GestureDetectorTest.class);
		context.startActivity(intent);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean consume=mGestureDetector.onTouchEvent(event);
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
		return consume;
	}

}
