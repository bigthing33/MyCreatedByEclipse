package com.example.testdemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testdemo.base.BaseActivity;
import com.example.testdemo.utils.LogUtil;
import com.example.testdemo.widget.CustomDrawable;

public class DrawableDemoActivity extends BaseActivity {

	private static final String TAG = DrawableDemoActivity.class.getName();
	private TextView test_size;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		LogUtil.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawable);
		initUI();
		
	}
	

	@Override
	protected void onResume() {
		LogUtil.d(TAG, "onResume");
		super.onResume();
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void initUI() {
		test_size = (TextView) findViewById(R.id.test_size);
		Drawable background = test_size.getBackground();
		LogUtil.d(TAG,"getIntrinsicHeight:"+ background.getIntrinsicHeight()+" getIntrinsicWidth:"+background.getIntrinsicWidth());
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		//当activity获得和失去焦点时会调用此方法，
		super.onWindowFocusChanged(hasFocus);
		LogUtil.d(TAG, "onWindowFocusChanged");
		if (hasFocus) {
			// test transition
			View v = findViewById(R.id.test_transition);
			TransitionDrawable drawable = (TransitionDrawable) v.getBackground();
			drawable.startTransition(1000);
            // test scale
            View testScale = findViewById(R.id.test_scale);
            ScaleDrawable testScaleDrawable = (ScaleDrawable) testScale.getBackground();
            testScaleDrawable.setLevel(10);
            // test clip
            ImageView testClip = (ImageView) findViewById(R.id.test_clip);
            ClipDrawable testClipDrawable = (ClipDrawable) testClip.getDrawable();
            testClipDrawable.setLevel(4000);//等级越高减的越少
            // test custom drawable
            View testCustomDrawable = findViewById(R.id.test_custom_drawable);
            CustomDrawable customDrawable = new CustomDrawable(Color.parseColor("#0ac39e"));
            customDrawable.setAlpha(200);//数字越大，透明度越低
            testCustomDrawable.setBackgroundDrawable(customDrawable);
		}
	}

	public static void actionStart(Context context) {
		Intent intent = new Intent(context, DrawableDemoActivity.class);
		context.startActivity(intent);
	}

}
