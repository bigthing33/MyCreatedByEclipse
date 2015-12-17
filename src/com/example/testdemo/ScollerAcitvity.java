package com.example.testdemo;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;
import android.widget.TextView;

public class ScollerAcitvity extends Activity {
	private Scroller mScroller;
	private TextView scrollerTest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scollertest);
		scrollerTest=(TextView) findViewById(R.id.scrollerTest);
	}

	public static void actionStart(Context context) {
		Intent intent = new Intent(context, ScollerAcitvity.class);
		context.startActivity(intent);
	}

	private void smoothScrollTo(int destX,int destY){
		int scrollX=scrollerTest.getScrollX();
		int delta=destX-scrollX;
		mScroller.startScroll(scrollX, 0, delta, 0,1000);
		scrollerTest.invalidate();
		scrollerTest.computeScroll();
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

}
