package com.example.testdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.testdemo.anims.Rotate3dAnimation;
import com.example.testdemo.base.BaseActivity;

public class AnimationActivity extends BaseActivity implements OnClickListener {

	private ImageView img_animation1;
	private ImageView img_animation2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animation);
		initUI();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void initUI() {
		img_animation1 = (ImageView) findViewById(R.id.img_animation1);
		img_animation1.setOnClickListener(this);
		img_animation2 = (ImageView) findViewById(R.id.img_animation2);
		img_animation2.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_animation1:
			Animation animation = AnimationUtils.loadAnimation(this,
					R.anim.anims);
			img_animation1.startAnimation(animation);
			break;
		case R.id.img_animation2:
			showToast("img_animation2");
			// Rotate3dAnimation animation2=new Rotate3dAnimation(0, 360, 0.5f,
			// 0.5f, 0.5f, true);
			Rotate3dAnimation animation2 = new Rotate3dAnimation(0, 360,
					img_animation2.getMeasuredWidth() / 2,
					img_animation2.getMeasuredHeight() / 2, img_animation2.getMeasuredWidth() / 2, true);
			animation2.setDuration(1000);
			animation2.setFillAfter(true);
			img_animation2.startAnimation(animation2);
			break;

		default:
			break;
		}

	}

	public static void actionStart(Context context) {
		Intent intent = new Intent(context, AnimationActivity.class);
		context.startActivity(intent);
	}

}
