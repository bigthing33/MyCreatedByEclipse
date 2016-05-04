package com.cyq.mvshow.activity;




import com.cyq.mvshow.widget.TitleView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

public class BaseActivity  extends FragmentActivity{
	protected TitleView titleView;
	@Override
	protected void onCreate(@Nullable Bundle arg0) {
		super.onCreate(arg0);

	}
}
