package com.example.testdemo.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Toast;

public abstract class BaseActivity extends Activity   {

	private Toast toast;
	@Override
	public  void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		initUI();
	}
	@Override
	public void onDestroy(){
		super.onDestroy();
		
	}

	public abstract void initUI();
	/**
	 * 显示Toast信息
	 * 
	 * @param text
	 *            要显示的信息
	 */
	public void showToast(String text) {
		if(toast == null) {
			toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
//			toast.setGravity(Gravity.CENTER, 0, 0);
		} else {
			toast.setText(text);
		}
		toast.show();
	}
	public void showToast(int stringId) {
		showToast(getString(stringId));
	}
	

}
