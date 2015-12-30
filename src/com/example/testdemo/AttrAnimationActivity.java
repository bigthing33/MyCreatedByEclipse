package com.example.testdemo;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.testdemo.anims.Rotate3dAnimation;
import com.example.testdemo.base.BaseActivity;

@SuppressLint("NewApi")
public class AttrAnimationActivity extends BaseActivity implements
		OnClickListener {

	private ImageView img_animation1;
	private ImageView img_animation2;
	private ImageView img_animation3;
	private ImageView img_animation4;
	private ListView list;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attranimation);
		initUI();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim);
	}

	@Override
	public void initUI() {
		img_animation1 = (ImageView) findViewById(R.id.img_animation1);
		img_animation1.setOnClickListener(this);
		img_animation2 = (ImageView) findViewById(R.id.img_animation2);
		img_animation2.setOnClickListener(this);
		img_animation3 = (ImageView) findViewById(R.id.img_animation3);
		img_animation3.setOnClickListener(this);
		img_animation4 = (ImageView) findViewById(R.id.img_animation4);
		img_animation4.setOnClickListener(this);
		// list=(ListView) findViewById(R.id.list);
		// ArrayList<String> datas = new ArrayList<String>();
		// for (int i = 0; i < 50; i++) {
		// datas.add("name " + i);
		// }
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		// R.layout.content_list_item, R.id.name, datas);
		// list.setAdapter(adapter);
		// list.setOnItemClickListener(new OnItemClickListener() {
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// Toast.makeText(AttrAnimationActivity.this, "click item",
		// Toast.LENGTH_SHORT).show();
		//
		// }
		// });
		// MyUtils.addAnimationToListView(AttrAnimationActivity.this, list);

	}

	@Override
	public void onClick(View v) {
		AnimationDrawable drawable;
		switch (v.getId()) {
		case R.id.img_animation1:
			showToast("img_animation1");
			ObjectAnimator
					.ofFloat(img_animation1, "translationX", 0F,
							-img_animation1.getHeight()).setDuration(1000)
					.start();
			// ObjectAnimator.ofFloat(img_animation1, "translationX",
			// -img_animation1.getHeight()).setDuration(1000).start();
			// ObjectAnimator.ofFloat(img_animation1, "translationX",
			// 0F,-360F).setDuration(1000).start();
			// ObjectAnimator.ofFloat(img_animation1, "translationX", 0F,
			// 360F).setDuration(1000).start();

			break;
		case R.id.img_animation2:
			showToast("img_animation2");
			ValueAnimator colorAnimator = ObjectAnimator.ofInt(img_animation2,
					"backgroundColor", /* red */0xFFFF8080,/* Blue */
					0xFF8080FF);
			colorAnimator.setDuration(3000);
			colorAnimator.setEvaluator(new ArgbEvaluator());
			colorAnimator.setRepeatCount(ValueAnimator.INFINITE);
			colorAnimator.setRepeatMode(ValueAnimator.REVERSE);
			colorAnimator.start();

			break;
		case R.id.img_animation3:
			/* 动画集合 */
			showToast("img_animation3");
			AnimatorSet set = new AnimatorSet();

			set.playTogether(
					ObjectAnimator.ofFloat(img_animation3,"translationX", 0F, img_animation1.getHeight()),
					ObjectAnimator.ofFloat(img_animation3, "translationY", 0F,img_animation1.getHeight()),
					ObjectAnimator.ofFloat(img_animation3,"rotationX", 0F, 360F),
					ObjectAnimator.ofFloat(img_animation3,"scaleX", 1F, 1.5F),
					ObjectAnimator.ofFloat(img_animation3,"scaleY", 1F, 0.5F),
					ObjectAnimator.ofFloat(img_animation3,"alpha", 1F, 0.25F,1),
					ObjectAnimator.ofFloat(img_animation3,"rotationY", 0F, 180F)
					);
			set.setDuration(5*1000).start();
			break;
		case R.id.img_animation4:
			
			showToast("img_animation4");
			AnimatorSet set2=(AnimatorSet) AnimatorInflater.loadAnimator(AttrAnimationActivity.this, R.animator.property_animator);
			set2.setTarget(img_animation4);
			set2.start();
			
			break;

		default:
			break;
		}

	}

	public static void actionStart(Context context) {
		Intent intent = new Intent(context, AttrAnimationActivity.class);
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(R.anim.enter_anim,
				R.anim.exit_anim);

	}

}
