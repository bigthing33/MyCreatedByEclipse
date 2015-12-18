package com.example.testdemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.Scroller;

public class MyButton extends Button {
	private Context context;

	public MyButton(Context context) {
		super(context);
		this.context=context;
	}
	Scroller scroller=new Scroller(context);
	

	
}
