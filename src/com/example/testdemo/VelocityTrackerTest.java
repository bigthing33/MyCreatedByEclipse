package com.example.testdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.TextView;

public class VelocityTrackerTest extends Activity {
	private TextView xVelocity;
	private TextView yVelocity;
	private VelocityTracker vTracker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_velocitytrackertest);
		xVelocity = (TextView) findViewById(R.id.xVelocity);
		yVelocity = (TextView) findViewById(R.id.yVelocity);
	}

	public static void actionStart(Context context) {
		Intent intent = new Intent(context, VelocityTrackerTest.class);
		context.startActivity(intent);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			if (vTracker == null) {
				vTracker = VelocityTracker.obtain();
			} else {
				vTracker.clear();
			}
			vTracker.addMovement(event);
			break;
		case MotionEvent.ACTION_MOVE:
			vTracker.addMovement(event);
			vTracker.computeCurrentVelocity(1000);
			xVelocity.setText("the x velocity is " + vTracker.getXVelocity());
			yVelocity.setText("the y velocity is " + vTracker.getYVelocity());
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			vTracker.clear();
			vTracker.recycle();
			break;
		}
		return true;
	}

}
