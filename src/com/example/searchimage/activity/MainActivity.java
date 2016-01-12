package com.example.searchimage.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;

import com.example.searchimage.R;
import com.example.searchimage.base.BaseActivity;

public class MainActivity extends BaseActivity {
	private final static String TAG = MainActivity.class.getSimpleName();
	private Context mContext=MainActivity.this;
	private ViewPager mViewPager;
	private FrameLayout subFragmentContainer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mViewPager = new ViewPager(mContext);
		mViewPager.setId(R.id.viewPager_explore);
		mViewPager.setOffscreenPageLimit(2);//设置缓存个数
		setContentView(R.layout.activity_main);
	}

	@SuppressLint("NewApi")
	@Override
	public void initUI() {
		subFragmentContainer=(FrameLayout) findViewById(R.id.subFragmentContainer);
		subFragmentContainer.addView(mViewPager);
		final android.app.FragmentManager fm = getFragmentManager();

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				
			}
		}, 100);
		
	}
}
