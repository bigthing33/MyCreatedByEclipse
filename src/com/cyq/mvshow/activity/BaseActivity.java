package com.cyq.mvshow.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.cyq.mvshow.widget.TitleView;

public class BaseActivity  extends FragmentActivity{
	protected TitleView titleView;
	@Override
	protected void onCreate( Bundle arg0) {
		super.onCreate(arg0);
	}
}
