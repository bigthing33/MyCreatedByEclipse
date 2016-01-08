package com.example.testdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.testdemo.R;
import com.example.testdemo.R.id;
import com.example.testdemo.R.layout;
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
		ViewGroup viewGroup=(ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
		View rootView = viewGroup.getChildAt(0);
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
