package com.cyq.mvshow.widget;



import com.cyq.mvshow.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class TitleView extends RelativeLayout implements View.OnClickListener {

	private Button leftBtn;
	private Button rightBtn;
	private Button rightBtn2;
	private TextView title;
	private OnBtnClickListener rightBtnClickListener;
	private OnBtnClickListener rightBtn2ClickListener;
	private OnBtnClickListener leftBtnClickListener;
	private String titleStr;
	private ProgressBar progressBar;
	
	public TitleView(Context context) {
		this(context, null);
	}

	public TitleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TitleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater.from(context).inflate(R.layout.title_view, this, true);
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleView);
		titleStr = ta.getString(R.styleable.TitleView_title1);
		ta.recycle();
		initViews();
		initEvent();
	}

	private void initViews() {
		leftBtn = (Button) findViewById(R.id.head_left_btn);
		rightBtn = (Button) findViewById(R.id.head_right_btn);
		rightBtn2 = (Button) findViewById(R.id.head_right_btn2);
		title = (TextView) findViewById(R.id.head_title_tv);
		title.setText(titleStr);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
	}
	
	public Button getRightBtn(){
		return rightBtn;
	}

	private void initEvent() {
		leftBtn.setOnClickListener(this);
	}

	public void setLeftBtnVisibility(int visibility) {
		leftBtn.setVisibility(visibility);
	}

	public void setRightBtnVisibility(int visibility) {
		rightBtn.setVisibility(visibility);
	}

	public void setRightBtn2Visibility(int visibility) {
		rightBtn2.setVisibility(visibility);
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}

	public void setRightBtnClickListener(
			OnBtnClickListener rightBtnClickListener) {
		this.rightBtnClickListener = rightBtnClickListener;
		rightBtn.setOnClickListener(this);
	}

	public void setRightBtn2ClickListener(
			OnBtnClickListener rightBtn2ClickListener) {
		this.rightBtn2ClickListener = rightBtnClickListener;
		rightBtn2.setOnClickListener(this);
	}

	public void setLeftBtnClickListener(OnBtnClickListener leftBtnClickListener) {
		this.leftBtnClickListener = leftBtnClickListener;
		leftBtn.setOnClickListener(this);
	}

	public interface OnBtnClickListener {
		public void onClick(View v);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

			case R.id.head_left_btn:
				if(leftBtnClickListener != null) {
					leftBtnClickListener.onClick(leftBtn);
				} else {
					((Activity)getContext()).finish();
				}
				break;
			case R.id.head_right_btn:
				rightBtnClickListener.onClick(v);
				break;
			case R.id.head_right_btn2:
				rightBtn2ClickListener.onClick(v);
				break;

		}
	}

	public void setRightBtnBackgroundColor(int color) {
		rightBtn.setBackgroundColor(color);
	}

	public void setRightBtnText(String text) {
		rightBtn.setText(text);
	}

	public void hideLeftBtn() {
		leftBtn.setVisibility(View.GONE);
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}

	public void setLeftBtnBackground(int resId) {
		leftBtn.setBackgroundResource(resId);
	}

	public void setRightBtnBackground(int resId) {
		rightBtn.setBackgroundResource(resId);
	}

	public void setRightBtn2Background(int resId) {
		rightBtn2.setBackgroundResource(resId);
	}

}
