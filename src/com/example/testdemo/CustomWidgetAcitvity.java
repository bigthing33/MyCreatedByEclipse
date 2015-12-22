package com.example.testdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.testdemo.base.BaseActivity;
import com.example.testdemo.widget.CascadeLayout;
import com.example.testdemo.widget.CircleView;

public class CustomWidgetAcitvity extends BaseActivity {
	public static final String TAG=CustomWidgetAcitvity.class.getName();
	private CascadeLayout myCascadeLayout;
	private CircleView circleView;
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customwidget);
		initUI();
		
	}
	@Override
	public void initUI() {
		myCascadeLayout=(CascadeLayout) findViewById(R.id.myCascadeLayout);
		circleView=(CircleView) findViewById(R.id.circleView);
	}
	public static void actionStart(Context context) {
		Intent intent = new Intent(context, CustomWidgetAcitvity.class);
		context.startActivity(intent);
	}

}
