package com.example.testdemo.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.testdemo.R;
import com.example.testdemo.R.anim;
import com.example.testdemo.R.id;
import com.example.testdemo.R.layout;
import com.example.testdemo.anims.Rotate3dAnimation;
import com.example.testdemo.base.BaseActivity;
import com.example.testdemo.utils.MyUtils;

public class AnimationActivity extends BaseActivity implements OnClickListener {

	private ImageView img_animation1;
	private ImageView img_animation2;
	private ImageView img_animation3;
	private ListView list;

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
		list=(ListView) findViewById(R.id.list);
        ArrayList<String> datas = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            datas.add("name " + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.content_list_item, R.id.name, datas);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Toast.makeText(AnimationActivity.this, "click item",
                        Toast.LENGTH_SHORT).show();

            }
        });
        MyUtils.addAnimationToListView(AnimationActivity.this, list);




	}


	@Override
	public void onClick(View v) {
		AnimationDrawable drawable;
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
		case R.id.img_animation3:
			showToast("img_animation3");
			img_animation3.setBackgroundResource(R.anim.fram_ainmation);
			drawable=(AnimationDrawable) img_animation3.getBackground();
			drawable.start();
			break;

		default:
			break;
		}

	}

	public static void actionStart(Context context) {
		Intent intent = new Intent(context, AnimationActivity.class);
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim);
	
	}

}
